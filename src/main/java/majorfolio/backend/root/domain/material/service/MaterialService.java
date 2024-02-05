package majorfolio.backend.root.domain.material.service;


import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import majorfolio.backend.root.domain.material.dto.response.MaterialListResponse;
import majorfolio.backend.root.domain.material.dto.response.MaterialResponse;
import majorfolio.backend.root.domain.material.entity.Material;
import majorfolio.backend.root.domain.material.repository.MaterialRepository;
import majorfolio.backend.root.domain.member.entity.KakaoSocialLogin;
import majorfolio.backend.root.domain.member.repository.KakaoSocialLoginRepository;
import majorfolio.backend.root.global.exception.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
/**
 * 과제를 조회하기 위해 필요한 기능을 정의한 서비스
 * @author 김태혁
 * @version 0.0.1
 */
@Service
@RequiredArgsConstructor
public class MaterialService {
    private final MaterialRepository materialRepository;
    private final KakaoSocialLoginRepository kakaoSocialLoginRepository;
    /**
     * 모든학교의 최신순 과제를 List<MaterialResponse> 형태로 반환
     * @author 김태혁
     * @version 0.0.1
     */
    public List<MaterialResponse> getNewUploadList() {
        List<Material> newUploadMaterials = materialRepository.findTop5ByOrderByCreatedAtDescIdAsc();
        if (newUploadMaterials == null || newUploadMaterials.isEmpty()) {
            // 자료가 1개도 없을 때의 예외 처리 또는 메시지 전달 등의 처리
            throw new NotFoundException("자료 없습니다.");
        }
        return convertToMaterialResponseListByMaterial(newUploadMaterials);
    }

    /**
     * 모든학교의 좋아요 많은 순 과제를 List<MaterialResponse> 형태로 반환
     * @author 김태혁
     * @version 0.0.1
     */
    public List<MaterialResponse> getBestList() {
        List<Material> bestMaterials = materialRepository.findTop5ByOrderByTotalRecommendDescIdAsc();
        return convertToMaterialResponseListByMaterial(bestMaterials);
    }

    /**
     * 모든학교에서 최근 본 순 과제를 List<MaterialResponse> 형태로 반환
     * @author 김태혁
     * @version 0.0.1
     */
    private List<MaterialResponse> convertToMaterialResponseListByMaterial(List<Material> materials) {
        return materials.stream()
                .map(MaterialResponse::of)
                .collect(Collectors.toList());
    }

    /**
     * 쿠키의 값에서 아이디를 읽어와 List<MaterialResponse>으로 반환
     * @author 김태혁
     * @version 0.0.1
     */
    public List<MaterialResponse> getRecentlyViewedMaterials(String cookieValue) {
        List<Long> recentlyViewedMaterialIds = parseRecentlyViewedMaterials(cookieValue);
        //길이를 5개로 제한
        List<Long> limitedIds = recentlyViewedMaterialIds.size() > 5
                ? recentlyViewedMaterialIds.subList(0, 5)
                : recentlyViewedMaterialIds;
        return convertToMaterialResponseList(limitedIds);
    }
    /**
     * "1,3,4.." 의 형태로 들어오는 string에서 id를 파싱
     * @author 김태혁
     * @version 0.0.1
     */
    private List<Long> parseRecentlyViewedMaterials(String cookieValue) {
        return List.of(cookieValue.split(",")).stream()
                .map(Long::parseLong)
                .collect(Collectors.toList());
    }

    /**
     * 과제를 아이디로 찾아 반환
     * @author 김태혁
     * @version 0.0.1
     */
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

    /**
     * 모든 학교에 대한 과제 조회 리스트들을 반환
     * @author 김태혁
     * @version 0.0.1
     */
    public MaterialListResponse getAllList(String cookieValue) {
        List<MaterialResponse> recentList;
        System.out.println(cookieValue);
        if(cookieValue == null || cookieValue.isEmpty())
            recentList = null;
        else
            recentList = getRecentlyViewedMaterials(cookieValue);
        return MaterialListResponse.of(getNewUploadList(), getBestList(), recentList);
    }

    /**
     * 내 대학에 대한 과제 조회 리스트들을 반환
     * @author 김태혁
     * @version 0.0.1
     */
    public MaterialListResponse getUnivList(HttpServletRequest request, String cookieValue) {
        Object kakaoIdAttribute = request.getAttribute("kakao_id");

        if (kakaoIdAttribute == null) {
            // 카카오 아이디가 없을 때의 예외 처리 또는 메시지 전달 등의 처리
            throw new NotFoundException("카카오 아이디를 찾을 수 없습니다.");
        }

        Long kakaoId = Long.parseLong(kakaoIdAttribute.toString());

        // 카카오 아이디에 해당하는 값 조회
        KakaoSocialLogin kakaoSocialLogin = kakaoSocialLoginRepository.findByKakaoNumber(kakaoId);
        if (kakaoSocialLogin == null || kakaoSocialLogin.getMember() == null || kakaoSocialLogin.getMember().getUniversityName() == null) {
            // 카카오 아이디에 해당하는 값이 없을 때의 예외 처리 또는 메시지 전달 등의 처리
            throw new NotFoundException("카카오 아이디에 해당하는 정보를 찾을 수 없습니다.");
        }
        //Long kakaoId = 2L;
        String univName = kakaoSocialLoginRepository.findByKakaoNumber(kakaoId).getMember().getUniversityName();

        List<MaterialResponse> recentList;
        if(cookieValue == null || cookieValue.isEmpty())
            recentList = null;
        else
            recentList = getRecentlyViewedMaterialsWithUniv(cookieValue, univName);
        return MaterialListResponse.of(getNewUploadListUniv(univName), getBestListUniv(univName), recentList);
    }

    /**
     * 내학교에서 좋아요 많은 순 과제를 List<MaterialResponse> 형태로 반환
     * @author 김태혁
     * @version 0.0.1
     */
    private List<MaterialResponse> getBestListUniv(String univName) {
        List<Material> bestMaterials = materialRepository.findTop5ByMemberUniversityNameOrderByTotalRecommendDescIdAsc(univName);
        return convertToMaterialResponseListByMaterial(bestMaterials);
    }

    /**
     * 내학교에서 최근 업로드 순 과제를 List<MaterialResponse> 형태로 반환
     * @author 김태혁
     * @version 0.0.1
     */
    private List<MaterialResponse> getNewUploadListUniv(String univName) {
        List<Material> newUploadMaterials = materialRepository.findTop5ByMemberUniversityNameOrderByCreatedAtDescIdAsc(univName);
        return convertToMaterialResponseListByMaterial(newUploadMaterials);
    }

    /**
     * 내학교에서 최근 본 순 과제를 List<MaterialResponse> 형태로 반환
     * @author 김태혁
     * @version 0.0.1
     */
    private List<MaterialResponse> getRecentlyViewedMaterialsWithUniv(String cookieValue, String univName) {
        List<Long> recentlyViewedMaterialIds = parseRecentlyViewedMaterials(cookieValue);
        //길이를 5개로 제한
        List<Long> limitedIds = recentlyViewedMaterialIds.size() > 5
                ? recentlyViewedMaterialIds.subList(0, 5)
                : recentlyViewedMaterialIds;
        return convertToMaterialResponseListWithUniv(limitedIds, univName);
    }

    /**
     * 아이디와 대학이름에 맞는 과제 반환
     * @author 김태혁
     * @version 0.0.1
     */
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


    /**
     * 내 학과에 대한 과제 조회 리스트들을 반환
     * @author 김태혁
     * @version 0.0.1
     */
    public MaterialListResponse getMajorList(HttpServletRequest request, String cookieValue) {
        Object kakaoIdAttribute = request.getAttribute("kakao_id");

        if (kakaoIdAttribute == null) {
            // 카카오 아이디가 없을 때의 예외 처리 또는 메시지 전달 등의 처리
            throw new NotFoundException("카카오 아이디를 찾을 수 없습니다.");
        }

        Long kakaoId = Long.parseLong(kakaoIdAttribute.toString());

        // 카카오 아이디에 해당하는 값 조회
        KakaoSocialLogin kakaoSocialLogin = kakaoSocialLoginRepository.findByKakaoNumber(kakaoId);
        if (kakaoSocialLogin == null || kakaoSocialLogin.getMember() == null || kakaoSocialLogin.getMember().getMajor1() == null) {
            // 카카오 아이디에 해당하는 값이 없을 때의 예외 처리 또는 메시지 전달 등의 처리
            throw new NotFoundException("카카오 아이디에 해당하는 정보를 찾을 수 없습니다.");
        }
        //Long kakaoId = 2L;
        String major = kakaoSocialLoginRepository.findByKakaoNumber(kakaoId).getMember().getMajor1();

        List<MaterialResponse> recentList;
        if(cookieValue == null || cookieValue.isEmpty())
            recentList = null;
        else
            recentList = getRecentlyViewedMaterialsWithMajor(cookieValue, major);
        return MaterialListResponse.of(getNewUploadListMajor(major), getBestListMajor(major), recentList);
    }

    /**
     * 내학과에서 좋아요 많은 순 과제를 List<MaterialResponse> 형태로 반환
     * @author 김태혁
     * @version 0.0.1
     */
    private List<MaterialResponse> getBestListMajor(String major) {
        List<Material> bestMaterials = materialRepository.findTop5ByMajorOrderByTotalRecommendDescIdAsc(major);
        return convertToMaterialResponseListByMaterial(bestMaterials);
    }

    /**
     * 내학과에서 업로드 순 과제를 List<MaterialResponse> 형태로 반환
     * @author 김태혁
     * @version 0.0.1
     */
    private List<MaterialResponse> getNewUploadListMajor(String major) {
        List<Material> newUploadMaterials = materialRepository.findTop5ByMajorOrderByCreatedAtDescIdAsc(major);
        return convertToMaterialResponseListByMaterial(newUploadMaterials);
    }

    /**
     * 내학과에서 최근에 본 순 과제를 List<MaterialResponse> 형태로 반환
     * @author 김태혁
     * @version 0.0.1
     */
    private List<MaterialResponse> getRecentlyViewedMaterialsWithMajor(String cookieValue, String major) {
        List<Long> recentlyViewedMaterialIds = parseRecentlyViewedMaterials(cookieValue);
        //길이를 5개로 제한
        List<Long> limitedIds = recentlyViewedMaterialIds.size() > 5
                ? recentlyViewedMaterialIds.subList(0, 5)
                : recentlyViewedMaterialIds;
        return convertToMaterialResponseListWithMajor(limitedIds, major);
    }

    /**
     * 아이디와 학과이름에 맞는 과제 반환
     * @author 김태혁
     * @version 0.0.1
     */
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
