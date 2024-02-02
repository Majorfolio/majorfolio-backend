package majorfolio.backend.root.domain.material.service;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import majorfolio.backend.root.domain.material.dto.response.MaterialResponse;
import majorfolio.backend.root.domain.material.dto.response.SingleMaterialListResponse;
import majorfolio.backend.root.domain.material.entity.Material;
import majorfolio.backend.root.domain.material.repository.MaterialRepository;
import majorfolio.backend.root.domain.member.repository.KakaoSocialLoginRepository;
import majorfolio.backend.root.global.exception.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MaterialAllListService {
    private final MaterialRepository materialRepository;
    private final KakaoSocialLoginRepository kakaoSocialLoginRepository;


    public SingleMaterialListResponse getAllUnivUploadList(int page, int pageSize) {
        Pageable pageable = PageRequest.of(page - 1, pageSize);
        Page<Material> materialPage = materialRepository.findByOrderByCreatedAtDescIdAsc(pageable);

        List<MaterialResponse> materialResponses = convertToMaterialResponseList(materialPage.getContent());

        if (materialResponses == null || materialResponses.isEmpty()) {
            // 더 이상 자료가 없습니다. 예외 발생 또는 메시지 전달 등의 처리
            throw new NotFoundException("더 이상 자료가 없습니다.");
        }

        return SingleMaterialListResponse.of(materialResponses);
    }

    public SingleMaterialListResponse getAllUnivLike(int page, int pageSize) {
        Pageable pageable = PageRequest.of(page - 1, pageSize);
        Page<Material> materialPage = materialRepository.findByOrderByTotalRecommendDescIdAsc(pageable);

        List<MaterialResponse> materialResponses = convertToMaterialResponseList(materialPage.getContent());

        return SingleMaterialListResponse.of(materialResponses);
    }

    public SingleMaterialListResponse getMyUnivUploadList(int page, int pageSize, HttpServletRequest request) {
        Pageable pageable = PageRequest.of(page - 1, pageSize);
        //Long kakaoId = Long.parseLong(request.getAttribute("kakao_id").toString());
        Long kakaoId = 2L;
        String univName = kakaoSocialLoginRepository.findByKakaoNumber(kakaoId).getMember().getUniversityName();
        Page<Material> materialPage = materialRepository.findByMemberUniversityNameOrderByCreatedAtDescIdAsc(univName, pageable);

        List<MaterialResponse> materialResponses = convertToMaterialResponseList(materialPage.getContent());

        return SingleMaterialListResponse.of(materialResponses);
    }

    public SingleMaterialListResponse getMyUnivLike(int page, int pageSize, HttpServletRequest request) {
        Pageable pageable = PageRequest.of(page - 1, pageSize);
        //Long kakaoId = Long.parseLong(request.getAttribute("kakao_id").toString());
        Long kakaoId = 2L;
        String univName = kakaoSocialLoginRepository.findByKakaoNumber(kakaoId).getMember().getUniversityName();
        Page<Material> materialPage = materialRepository.findByMemberUniversityNameOrderByTotalRecommendDescIdAsc(univName, pageable);

        List<MaterialResponse> materialResponses = convertToMaterialResponseList(materialPage.getContent());

        return SingleMaterialListResponse.of(materialResponses);
    }

    public SingleMaterialListResponse getMyMajorUploadList(int page, int pageSize, HttpServletRequest request) {
        Pageable pageable = PageRequest.of(page - 1, pageSize);
        //Long kakaoId = Long.parseLong(request.getAttribute("kakao_id").toString());
        Long kakaoId = 2L;
        String major = kakaoSocialLoginRepository.findByKakaoNumber(kakaoId).getMember().getMajor1();
        Page<Material> materialPage = materialRepository.findByMajorOrderByCreatedAtDescIdAsc(major, pageable);

        List<MaterialResponse> materialResponses = convertToMaterialResponseList(materialPage.getContent());

        return SingleMaterialListResponse.of(materialResponses);
    }

    public SingleMaterialListResponse getMyMajorLike(int page, int pageSize, HttpServletRequest request) {
        Pageable pageable = PageRequest.of(page - 1, pageSize);
        //Long kakaoId = Long.parseLong(request.getAttribute("kakao_id").toString());
        Long kakaoId = 2L;
        String major = kakaoSocialLoginRepository.findByKakaoNumber(kakaoId).getMember().getMajor1();
        Page<Material> materialPage = materialRepository.findByMajorOrderByTotalRecommendDescIdAsc(major, pageable);

        List<MaterialResponse> materialResponses = convertToMaterialResponseList(materialPage.getContent());

        return SingleMaterialListResponse.of(materialResponses);
    }

    private List<MaterialResponse> convertToMaterialResponseList(List<Material> materials) {

        return materials.stream()
                .map(MaterialResponse::of)
                .collect(Collectors.toList());
    }



}
