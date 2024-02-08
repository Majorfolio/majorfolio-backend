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

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import majorfolio.backend.root.domain.material.dto.response.BookmarkListResponse;
import majorfolio.backend.root.domain.material.dto.response.MyBookmark;
import majorfolio.backend.root.domain.material.entity.Material;
import majorfolio.backend.root.domain.material.repository.MaterialRepository;
import majorfolio.backend.root.domain.member.entity.Bookmark;
import majorfolio.backend.root.domain.member.entity.Likes;
import majorfolio.backend.root.domain.member.entity.Member;
import majorfolio.backend.root.domain.member.repository.BookmarkRepository;
import majorfolio.backend.root.domain.member.repository.KakaoSocialLoginRepository;
import majorfolio.backend.root.domain.member.repository.LikeRepository;
import majorfolio.backend.root.global.exception.NotFoundException;
import majorfolio.backend.root.global.exception.UserException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

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
    public BookmarkListResponse showBookmarkList(int page, int pageSize, Long kakaoId){
        Pageable pageable = PageRequest.of(page - 1, pageSize);
        Member member;
        try {
            member = kakaoSocialLoginRepository.findById(kakaoId).get().getMember();
        } catch (NoSuchElementException e){
            throw new NotFoundException("해당 유저를 찾을 수 없습니다.");
        }

        Page<Bookmark> bookmarkPage = bookmarkRepository.findByMemberAndIsCheckOrderByDateDescIdAsc(member, true, pageable);

        List<MyBookmark> myBookmarkList = convertBookmarkListResponse(bookmarkPage.getContent());

        return BookmarkListResponse.of(page, myBookmarkList);
    }

    /**
     * 내가 북마크한거 리스트 반환
     * @param bookmarkList
     * @return
     */
    public List<MyBookmark> convertBookmarkListResponse(List<Bookmark> bookmarkList){
        List<MyBookmark> bookmarks = new ArrayList<>();
        for(Bookmark bookmark : bookmarkList){
            Material material = bookmark.getMaterial();
            Member owner = material.getMember();

            //과제 오너의 닉네임, 프로필 이미지, 대학명 가져오기
            String nickName = owner.getNickName();
            String profileUrl = owner.getProfileImage();
            String university = owner.getUniversityName();

            //과제 이름, 그 과제의 학과, 좋아요 수, 타입 가져오기
            String className = material.getClassName();
            String major = material.getMajor();
            int totalRecommend = material.getTotalRecommend();
            String type = material.getType();

            MyBookmark myBookmark = MyBookmark.of(nickName, profileUrl, className,
                    university, major, type, totalRecommend);

            bookmarks.add(myBookmark);
        }

        return bookmarks;
    }
}
