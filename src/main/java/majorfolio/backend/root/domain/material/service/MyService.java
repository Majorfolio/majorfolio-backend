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

import lombok.RequiredArgsConstructor;
import majorfolio.backend.root.domain.material.entity.Material;
import majorfolio.backend.root.domain.material.repository.MaterialRepository;
import majorfolio.backend.root.domain.member.entity.Bookmark;
import majorfolio.backend.root.domain.member.entity.Likes;
import majorfolio.backend.root.domain.member.entity.Member;
import majorfolio.backend.root.domain.member.repository.BookmarkRepository;
import majorfolio.backend.root.domain.member.repository.KakaoSocialLoginRepository;
import majorfolio.backend.root.domain.member.repository.LikeRepository;
import org.springframework.stereotype.Service;

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
}
