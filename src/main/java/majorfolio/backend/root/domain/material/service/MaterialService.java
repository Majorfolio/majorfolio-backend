package majorfolio.backend.root.domain.material.service;


import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import majorfolio.backend.root.domain.material.dto.response.MaterialListResponse;
import majorfolio.backend.root.domain.material.dto.response.MaterialResponse;
import majorfolio.backend.root.domain.material.entity.Material;
import majorfolio.backend.root.domain.material.repository.MaterialRepository;
import majorfolio.backend.root.domain.member.repository.KakaoSocialLoginRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MaterialService {
    private final MaterialRepository materialRepository;
    private final KakaoSocialLoginRepository kakaoSocialLoginRepository;

    public List<MaterialResponse> getNewUploadList() {
        List<Material> newUploadMaterials = materialRepository.findTop5ByOrderByCreatedAtDesc();
        return convertToMaterialResponseListByMaterial(newUploadMaterials);
    }

    public List<MaterialResponse> getBestList() {
        List<Material> bestMaterials = materialRepository.findTop5ByOrderByTotalRecommendDesc();
        return convertToMaterialResponseListByMaterial(bestMaterials);
    }

    private List<MaterialResponse> convertToMaterialResponseListByMaterial(List<Material> materials) {
        return materials.stream()
                .map(MaterialResponse::of)
                .collect(Collectors.toList());
    }

    public List<MaterialResponse> getRecentlyViewedMaterials(String cookieValue) {
        List<Long> recentlyViewedMaterialIds = parseRecentlyViewedMaterials(cookieValue);
        return convertToMaterialResponseList(recentlyViewedMaterialIds);
    }
    private List<Long> parseRecentlyViewedMaterials(String cookieValue) {
        return List.of(cookieValue.split(",")).stream()
                .map(Long::parseLong)
                .collect(Collectors.toList());
    }

    private List<MaterialResponse> convertToMaterialResponseList(List<Long> materialIds) {
        List<MaterialResponse> materialResponses = new ArrayList<>();
        for (Long materialId : materialIds) {
            Material material = materialRepository.findById(materialId).orElse(null);
            if (material != null) {
                materialResponses.add(MaterialResponse.of(material));
            }
        }
        return materialResponses;
    }


    public MaterialListResponse getAllList(String cookieValue) {
        List<MaterialResponse> recentList;
        System.out.println(cookieValue);
        if(cookieValue == null || cookieValue.isEmpty())
            recentList = null;
        else
            recentList = getRecentlyViewedMaterials(cookieValue);
        return MaterialListResponse.of(getNewUploadList(), getBestList(), recentList);
    }

    public MaterialListResponse getUnivList(HttpServletRequest request, String cookieValue) {
        Long kakaoId = Long.parseLong(request.getAttribute("kakao_id").toString());
        //Long kakaoId = 2L;
        String univName = kakaoSocialLoginRepository.findByKakaoNumber(kakaoId).getMember().getUniversityName();

        List<MaterialResponse> recentList;
        if(cookieValue == null || cookieValue.isEmpty())
            recentList = null;
        else
            recentList = getRecentlyViewedMaterialsWithUniv(cookieValue, univName);
        return MaterialListResponse.of(getNewUploadListUniv(univName), getBestListUniv(univName), recentList);
    }

    private List<MaterialResponse> getBestListUniv(String univName) {
        List<Material> bestMaterials = materialRepository.findTop5ByMemberUniversityNameOrderByTotalRecommendDesc(univName);
        return convertToMaterialResponseListByMaterial(bestMaterials);
    }

    private List<MaterialResponse> getNewUploadListUniv(String univName) {
        List<Material> newUploadMaterials = materialRepository.findTop5ByMemberUniversityNameOrderByCreatedAtDesc(univName);
        return convertToMaterialResponseListByMaterial(newUploadMaterials);
    }

    private List<MaterialResponse> getRecentlyViewedMaterialsWithUniv(String cookieValue, String univName) {
        List<Long> recentlyViewedMaterialIds = parseRecentlyViewedMaterials(cookieValue);
        return convertToMaterialResponseListWithUniv(recentlyViewedMaterialIds, univName);
    }

    private List<MaterialResponse> convertToMaterialResponseListWithUniv(List<Long> materialIds, String univName) {
        List<MaterialResponse> materialResponses = new ArrayList<>();
        for (Long materialId : materialIds) {
            Material material = materialRepository.findByIdAndMemberUniversityName(materialId, univName);
            if (material != null) {
                materialResponses.add(MaterialResponse.of(material));
            }
        }
        return materialResponses;
    }


    public MaterialListResponse getMajorList(HttpServletRequest request, String cookieValue) {
        //Long kakaoId = Long.parseLong(request.getAttribute("kakao_id").toString());
        Long kakaoId = 2L;
        String major = kakaoSocialLoginRepository.findByKakaoNumber(kakaoId).getMember().getMajor1();

        List<MaterialResponse> recentList;
        if(cookieValue == null || cookieValue.isEmpty())
            recentList = null;
        else
            recentList = getRecentlyViewedMaterialsWithMajor(cookieValue, major);
        return MaterialListResponse.of(getNewUploadListMajor(major), getBestListMajor(major), recentList);
    }

    private List<MaterialResponse> getBestListMajor(String major) {
        List<Material> bestMaterials = materialRepository.findTop5ByMajorOrderByTotalRecommendDesc(major);
        return convertToMaterialResponseListByMaterial(bestMaterials);
    }

    private List<MaterialResponse> getNewUploadListMajor(String major) {
        List<Material> newUploadMaterials = materialRepository.findTop5ByMajorOrderByCreatedAtDesc(major);
        return convertToMaterialResponseListByMaterial(newUploadMaterials);
    }

    private List<MaterialResponse> getRecentlyViewedMaterialsWithMajor(String cookieValue, String major) {
        List<Long> recentlyViewedMaterialIds = parseRecentlyViewedMaterials(cookieValue);
        return convertToMaterialResponseListWithMajor(recentlyViewedMaterialIds, major);
    }

    private List<MaterialResponse> convertToMaterialResponseListWithMajor(List<Long> materialIds, String major) {
        List<MaterialResponse> materialResponses = new ArrayList<>();
        for (Long materialId : materialIds) {
            Material material = materialRepository.findByIdAndMajor(materialId, major);
            if (material != null) {
                materialResponses.add(MaterialResponse.of(material));
            }
        }
        return materialResponses;
    }
}
