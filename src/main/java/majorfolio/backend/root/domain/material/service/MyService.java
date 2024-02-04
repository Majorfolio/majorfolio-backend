package majorfolio.backend.root.domain.material.service;

import lombok.RequiredArgsConstructor;
import majorfolio.backend.root.domain.material.entity.Material;
import majorfolio.backend.root.domain.material.repository.MaterialRepository;
import majorfolio.backend.root.domain.member.entity.Likes;
import majorfolio.backend.root.domain.member.entity.Member;
import majorfolio.backend.root.domain.member.repository.KakaoSocialLoginRepository;
import majorfolio.backend.root.domain.member.repository.LikeRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MyService {
    private final KakaoSocialLoginRepository kakaoSocialLoginRepository;
    private final MaterialRepository materialRepository;
    private final LikeRepository likeRepository;
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
}
