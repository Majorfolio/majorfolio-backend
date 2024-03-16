/**
 * MyService
 *
 * 2024.02.05
 *
 * 0.0.1
 *
 * Majorfolio
 */
package majorfolio.backend.root.domain.material.service;

import com.amazonaws.services.s3.AmazonS3Client;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import majorfolio.backend.root.domain.admin.entity.Event;
import majorfolio.backend.root.domain.admin.entity.Notice;
import majorfolio.backend.root.domain.admin.repository.EventRepository;
import majorfolio.backend.root.domain.admin.repository.NoticeRepository;
import majorfolio.backend.root.domain.material.dto.response.*;
import majorfolio.backend.root.domain.material.entity.Material;
import majorfolio.backend.root.domain.material.repository.MaterialRepository;
import majorfolio.backend.root.domain.member.dto.request.MyInfoRequest;
import majorfolio.backend.root.domain.member.dto.request.ProfileImageRequest;
import majorfolio.backend.root.domain.member.entity.Bookmark;
import majorfolio.backend.root.domain.member.entity.KakaoSocialLogin;
import majorfolio.backend.root.domain.member.entity.Likes;
import majorfolio.backend.root.domain.member.entity.Member;
import majorfolio.backend.root.domain.member.repository.BookmarkRepository;
import majorfolio.backend.root.domain.member.repository.KakaoSocialLoginRepository;
import majorfolio.backend.root.domain.member.repository.LikeRepository;
import majorfolio.backend.root.domain.member.repository.MemberRepository;
import majorfolio.backend.root.domain.member.service.MemberGlobalService;
import majorfolio.backend.root.global.exception.NotFoundException;
import majorfolio.backend.root.global.util.S3Util;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import static majorfolio.backend.root.global.response.status.BaseExceptionStatus.NOT_FOUND_INFO_FROM_KAKAOID;
import static majorfolio.backend.root.global.response.status.BaseExceptionStatus.NOT_FOUND_MATERIAL;
import static majorfolio.backend.root.global.status.S3DirectoryEnum.EVENTS3;
import static majorfolio.backend.root.global.status.S3DirectoryEnum.NOTICES3;

/**
 * /My 요청에 관한 서비스 로직 정의
 *
 * @author 김영록
 * @version 0.0.1
 */
@Service
@RequiredArgsConstructor
public class MyService {
    private final KakaoSocialLoginRepository kakaoSocialLoginRepository;
    private final MaterialRepository materialRepository;
    private final LikeRepository likeRepository;
    private final BookmarkRepository bookmarkRepository;
    private final MemberGlobalService memberGlobalService;
    private final MemberRepository memberRepository;
    private final NoticeRepository noticeRepository;
    private final EventRepository eventRepository;

    @Value("${cloud.aws.s3.bucket}")
    private String s3Bucket;

    @Value("${cloud.aws.cloudFront.distributionDomain}")
    private String distributionDomain;

    @Value("${cloud.aws.path}")
    private String privateKeyFilePath;

    @Value("${cloud.aws.cloudFront.keyPairId}")
    private String keyPairId;

    private final AmazonS3Client amazonS3;

    /**
     * 좋아요 기능 구현
     * @param materialId
     * @param kakaoId
     * @return
     */
    public String like(Long materialId, Long kakaoId){
        Member member = kakaoSocialLoginRepository.findById(kakaoId).get().getMember();
        Material material = materialRepository.findById(materialId).get();
        Likes likes = likeRepository.findByMemberAndMaterial(member, material);
        int totalRecommend = material.getTotalRecommend();
        if(likes == null){
            likes = Likes.of(material, member, true);
            likes.setIsCheck(true);
            likeRepository.save(likes);
            totalRecommend++;
            material.setTotalRecommend(totalRecommend);
            materialRepository.save(material);
            return "좋아요 +1";
        }

        if(likes.getIsCheck()){
            likes.setIsCheck(false);
            likeRepository.save(likes);
            totalRecommend--;
            material.setTotalRecommend(totalRecommend);
            materialRepository.save(material);
            return "좋아요 -1";
        }
        else{
            likes.setIsCheck(true);
            likeRepository.save(likes);
            totalRecommend++;
            material.setTotalRecommend(totalRecommend);
            materialRepository.save(material);
            return "좋아요 +1";
        }

    }

    /**
     * 북마크 기능 구현
     * @param materialId
     * @param kakaoId
     * @return
     */
    public String bookmark(Long materialId, Long kakaoId){
        Member member = kakaoSocialLoginRepository.findById(kakaoId).get().getMember();
        Material material = materialRepository.findById(materialId).get();
        Bookmark bookmark = bookmarkRepository.findByMemberAndMaterial(member, material);
        int totalBookmark = material.getTotalBookmark();
        if(bookmark == null){
            bookmark = Bookmark.of(material, member, true);
            bookmark.setIsCheck(true);
            bookmarkRepository.save(bookmark);
            totalBookmark++;
            material.setTotalBookmark(totalBookmark);
            materialRepository.save(material);
            return "북마크 +1";
        }

        if(bookmark.getIsCheck()){
            bookmark.setIsCheck(false);
            bookmarkRepository.save(bookmark);
            totalBookmark--;
            material.setTotalBookmark(totalBookmark);
            materialRepository.save(material);
            return "북마크 -1";
        }
        else{
            bookmark.setIsCheck(true);
            bookmarkRepository.save(bookmark);
            totalBookmark++;
            material.setTotalBookmark(totalBookmark);
            materialRepository.save(material);
            return "북마크 +1";
        }

    }

    /**
     * 마이페이지 북마크 한거 모아보기 api서비스 구현
     * @param page
     * @param pageSize
     * @param kakaoId
     * @return
     */
    public MyMaterialResponse showBookmarkList(int page, int pageSize, Long kakaoId){
        Pageable pageable = PageRequest.of(page - 1, pageSize);
        Member member;
        try {
            member = kakaoSocialLoginRepository.findById(kakaoId).get().getMember();
        } catch (NoSuchElementException e){
            throw new NotFoundException(NOT_FOUND_INFO_FROM_KAKAOID);
        }

        Page<Bookmark> bookmarkPage = bookmarkRepository.findByMemberAndIsCheckOrderByDateDescIdAsc(member, true, pageable);

        List<MyMaterial> myMaterialList = convertBookmarkListResponse(bookmarkPage.getContent());

        if (myMaterialList == null || myMaterialList.isEmpty()) {
            // 더 이상 자료가 없습니다. 예외 발생 또는 메시지 전달 등의 처리
            throw new NotFoundException(NOT_FOUND_MATERIAL);
        }

        return MyMaterialResponse.of(page, myMaterialList);
    }

    /**
     * 마이페이지 좋아요 한거 모아보기 api서비스 구현
     * @param page
     * @param pageSize
     * @param kakaoId
     * @return
     */
    public MyMaterialResponse showLikeList(int page, int pageSize, Long kakaoId){
        Pageable pageable = PageRequest.of(page - 1, pageSize);
        Member member;
        try {
            member = kakaoSocialLoginRepository.findById(kakaoId).get().getMember();
        } catch (NoSuchElementException e){
            throw new NotFoundException(NOT_FOUND_INFO_FROM_KAKAOID);
        }

        Page<Likes> likesPage = likeRepository.findByMemberAndIsCheckOrderByDateDescIdAsc(member, true, pageable);

        List<MyMaterial> myMaterialList = convertLikeListResponse(likesPage.getContent());

        if (myMaterialList == null || myMaterialList.isEmpty()) {
            // 더 이상 자료가 없습니다. 예외 발생 또는 메시지 전달 등의 처리
            throw new NotFoundException(NOT_FOUND_MATERIAL);
        }

        return MyMaterialResponse.of(page, myMaterialList);
    }

    /**
     * 내가 북마크한거 리스트 반환
     * @param bookmarkList
     * @return
     */
    public List<MyMaterial> convertBookmarkListResponse(List<Bookmark> bookmarkList){
        List<MyMaterial> bookmarks = new ArrayList<>();
        for(Bookmark bookmark : bookmarkList){
            Material material = bookmark.getMaterial();
            MyMaterial myMaterial = getMyMaterial(material);

            bookmarks.add(myMaterial);
        }

        return bookmarks;
    }

    public List<MyMaterial> convertLikeListResponse(List<Likes> likeList){
        List<MyMaterial> likes = new ArrayList<>();
        for(Likes like : likeList){
            Material material = like.getMaterial();
            MyMaterial myMaterial = getMyMaterial(material);

            likes.add(myMaterial);
        }

        return likes;
    }

    /**
     * 중복 코드 없애기 위해 getMyMaterial메소드 생성
     * @param material
     * @return
     */
    public MyMaterial getMyMaterial(Material material){
        Member owner = material.getMember();

        //과제번호 가져오기
        Long materialId = material.getId();
        //멤버Id 가져오기
        Long memberId = material.getMember().getId();
        //과제 오너의 닉네임, 프로필 이미지, 대학명 가져오기
        String nickName = owner.getNickName();
        String profileUrl = owner.getProfileImage();
        String university = owner.getUniversityName();

        //과제 이름, 그 과제의 학과, 좋아요 수, 타입 가져오기
        String className = material.getClassName();
        String major = material.getMajor();
        int totalRecommend = material.getTotalRecommend();
        String type = material.getType();

        return MyMaterial.of(materialId, memberId, nickName, profileUrl, className,
                university, major, type, totalRecommend);
    }

    public String changeProfileImage(ProfileImageRequest profileImageRequest, HttpServletRequest request) {
        KakaoSocialLogin kakaoSocialLogin = memberGlobalService.getMemberByToken(request);
        int profileImageId = profileImageRequest.getProfileImage();
        System.out.println("profileImageId = " + profileImageId);

        Member member = kakaoSocialLogin.getMember();
        member.setProfileImage(Integer.toString(profileImageId));
        memberRepository.save(member);
        return "이미지가 변경되었습니다.";
    }

    /**
     * 공지사항 모아보기 서비스 구현
     * @return
     */
    public List<ShowNoticeListResponse> showNoticeList() {
        List<Notice> noticeList = noticeRepository.findAll();
        return convertNoticeListResponseList(noticeList);
    }

    // 공지사항 모아보기 응답 객체 변환 메소드
    private List<ShowNoticeListResponse> convertNoticeListResponseList(List<Notice> noticeList) {
        List<ShowNoticeListResponse> showNoticeListResponseList = new ArrayList<>();
        for(Notice n : noticeList){
            showNoticeListResponseList.add(ShowNoticeListResponse.of(n.getId(), n.getTitle()));
        }
        return showNoticeListResponseList;
    }

    /**
     * 이벤트 모아보기 서비스 구현
     * @return
     */
    public List<ShowEventListResponse> showEventList() {
        List<Event> eventList = eventRepository.findAll();
        return convertEventListResponseList(eventList);
    }

    // 이벤트 모아보기 응답 객체 변환 메소드
    private List<ShowEventListResponse> convertEventListResponseList(List<Event> eventList) {
        List<ShowEventListResponse> showEventListResponseList = new ArrayList<>();
        for(Event e : eventList){
            showEventListResponseList.add(ShowEventListResponse.of(e.getId(), e.getTitle()));
        }
        return showEventListResponseList;
    }

    /**
     * 공지사항 상세 보기 서비스 구현
     * @param noticeId
     * @return
     */
    public ShowNoticeDetailResponse showNoticeDetail(Long noticeId) throws InvalidKeySpecException, IOException {
        Notice notice = noticeRepository.findById(noticeId).get();
        String link = S3Util.makeSignedUrl(notice.getLink(), s3Bucket, 0L, 0L, NOTICES3.getS3DirectoryName(), privateKeyFilePath, distributionDomain, keyPairId, amazonS3);
        return ShowNoticeDetailResponse.of(notice.getTitle(), link);
    }

    /**
     * 이벤트 상세 보기 서비스 구현
     * @param eventId
     * @return
     */
    public ShowEventDetailResponse showEventDetail(Long eventId) throws InvalidKeySpecException, IOException {
        Event event = eventRepository.findById(eventId).get();
        String link = S3Util.makeSignedUrl(event.getLink(), s3Bucket, 0L, 0L, EVENTS3.getS3DirectoryName(), privateKeyFilePath, distributionDomain, keyPairId, amazonS3);
        return ShowEventDetailResponse.of(event.getTitle(), link);
    }

    public String changeMyInfo(MyInfoRequest myInfoRequest, HttpServletRequest request) {
        Member member = memberGlobalService.getMemberByToken(request).getMember();

        if (myInfoRequest.getNickName() != null) {
            member.setNickName(myInfoRequest.getNickName());
        }
        if (myInfoRequest.getMajor1() != null) {
            member.setMajor1(myInfoRequest.getMajor1());
        }
        if (myInfoRequest.getMajor2() != null) {
            member.setMajor2(myInfoRequest.getMajor2());
        }
        if (myInfoRequest.getStudentId() != 0) { // Check for non-zero studentId
            member.setStudentId(myInfoRequest.getStudentId());
        }
        if (myInfoRequest.getPhoneNumber() != null) {
            member.setPhoneNumber(myInfoRequest.getPhoneNumber());
        }

        memberRepository.save(member);
        return "정상적으로 유저의 정보가 변경되었습니다.";
    }
}
