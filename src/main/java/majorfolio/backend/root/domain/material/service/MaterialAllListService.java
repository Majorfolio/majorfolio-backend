package majorfolio.backend.root.domain.material.service;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import majorfolio.backend.root.domain.material.dto.response.*;
import majorfolio.backend.root.domain.material.entity.Material;
import majorfolio.backend.root.domain.material.repository.MaterialRepository;
import majorfolio.backend.root.domain.member.entity.KakaoSocialLogin;
import majorfolio.backend.root.domain.member.entity.Member;
import majorfolio.backend.root.domain.member.repository.KakaoSocialLoginRepository;
import majorfolio.backend.root.domain.member.repository.MemberRepository;
import majorfolio.backend.root.global.exception.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static majorfolio.backend.root.global.response.status.BaseExceptionStatus.*;

/**
 * 모두보기 버튼에 따른 기능을 작성한 Service
 * @author 김태혁
 * @version 0.0.1
 */
@Service
@RequiredArgsConstructor
public class MaterialAllListService {
    private final MaterialRepository materialRepository;
    private final KakaoSocialLoginRepository kakaoSocialLoginRepository;
    private final MemberRepository memberRepository;


    /**
     * 모든학교애서 최근업로드순의 모두보기 수행
     * @author 김태혁
     * @version 0.0.1
     */
    public SingleMaterialListResponse getAllUnivUploadList(int page, int pageSize) {
        Pageable pageable = PageRequest.of(page - 1, pageSize);
        Page<Material> materialPage = materialRepository.findByStatusAndMemberStatusOrderByCreatedAtDescIdAsc("active","active", pageable);

        List<MaterialResponse> materialResponses = convertToMaterialResponseList(materialPage.getContent());

        if (materialResponses == null || materialResponses.isEmpty()) {
            // 더 이상 자료가 없습니다. 예외 발생 또는 메시지 전달 등의 처리
            throw new NotFoundException(NOT_FOUND_MATERIAL);
        }

        int totalPages = materialPage.getTotalPages();
        boolean isLastPage = page >= totalPages;

        return SingleMaterialListResponse.of(page, materialResponses, isLastPage);
    }

    /**
     * 모든학교애서 좋아요 많은 순의 모두보기 수행
     * @author 김태혁
     * @version 0.0.1
     */
    public SingleMaterialListResponse getAllUnivLike(int page, int pageSize) {
        Pageable pageable = PageRequest.of(page - 1, pageSize);
        Page<Material> materialPage = materialRepository.findByStatusAndMemberStatusOrderByTotalRecommendDescIdAsc("active","active", pageable);

        List<MaterialResponse> materialResponses = convertToMaterialResponseList(materialPage.getContent());
        if (materialResponses == null || materialResponses.isEmpty()) {
            // 더 이상 자료가 없습니다. 예외 발생 또는 메시지 전달 등의 처리
            throw new NotFoundException(NOT_FOUND_MATERIAL);
        }

        int totalPages = materialPage.getTotalPages();
        boolean isLastPage = page >= totalPages;

        return SingleMaterialListResponse.of(page, materialResponses, isLastPage);
    }

    /**
     * 내 학교애서 최근업로드순의 모두보기 수행
     * @author 김태혁
     * @version 0.0.1
     */
    public SingleMaterialListResponse getMyUnivUploadList(int page, int pageSize, HttpServletRequest request) {
        Pageable pageable = PageRequest.of(page - 1, pageSize);
        Object kakaoIdAttribute = request.getAttribute("kakaoId");

        if (kakaoIdAttribute == null) {
            // 카카오 아이디가 없을 때의 예외 처리 또는 메시지 전달 등의 처리
            throw new NotFoundException(NOT_FOUND_KAKAOID);
        }

        Long kakaoId = Long.parseLong(kakaoIdAttribute.toString());

        // 카카오 아이디에 해당하는 값 조회
        KakaoSocialLogin kakaoSocialLogin = kakaoSocialLoginRepository.findById(kakaoId).orElse(null);
        if (kakaoSocialLogin == null || kakaoSocialLogin.getMember() == null || kakaoSocialLogin.getMember().getMajor1() == null) {
            // 카카오 아이디에 해당하는 값이 없을 때의 예외 처리 또는 메시지 전달 등의 처리
            throw new NotFoundException(NOT_FOUND_INFO_FROM_KAKAOID);
        }


        String univName = kakaoSocialLogin.getMember().getUniversityName();
        Page<Material> materialPage = materialRepository.findByStatusAndMemberUniversityNameAndMemberStatusOrderByCreatedAtDescIdAsc("active",univName, "active", pageable);

        List<MaterialResponse> materialResponses = convertToMaterialResponseList(materialPage.getContent());
        if (materialResponses == null || materialResponses.isEmpty()) {
            // 더 이상 자료가 없습니다. 예외 발생 또는 메시지 전달 등의 처리
            throw new NotFoundException(NOT_FOUND_MATERIAL);
        }

        int totalPages = materialPage.getTotalPages();
        boolean isLastPage = page >= totalPages;

        return SingleMaterialListResponse.of(page, materialResponses, isLastPage);
    }

    /**
     * 내 학교애서 좋아요 많은 순의 모두보기 수행
     * @author 김태혁
     * @version 0.0.1
     */
    public SingleMaterialListResponse getMyUnivLike(int page, int pageSize, HttpServletRequest request) {
        Pageable pageable = PageRequest.of(page - 1, pageSize);

        Object kakaoIdAttribute = request.getAttribute("kakaoId");

        if (kakaoIdAttribute == null) {
            // 카카오 아이디가 없을 때의 예외 처리 또는 메시지 전달 등의 처리
            throw new NotFoundException(NOT_FOUND_KAKAOID);
        }

        Long kakaoId = Long.parseLong(kakaoIdAttribute.toString());

        // 카카오 아이디에 해당하는 값 조회
        KakaoSocialLogin kakaoSocialLogin = kakaoSocialLoginRepository.findById(kakaoId).orElse(null);
        if (kakaoSocialLogin == null || kakaoSocialLogin.getMember() == null || kakaoSocialLogin.getMember().getMajor1() == null) {
            // 카카오 아이디에 해당하는 값이 없을 때의 예외 처리 또는 메시지 전달 등의 처리
            throw new NotFoundException(NOT_FOUND_INFO_FROM_KAKAOID);
        }


        String univName = kakaoSocialLogin.getMember().getUniversityName();
        Page<Material> materialPage = materialRepository.findByStatusAndMemberUniversityNameAndMemberStatusOrderByTotalRecommendDescIdAsc("active",univName, "active", pageable);

        List<MaterialResponse> materialResponses = convertToMaterialResponseList(materialPage.getContent());
        if (materialResponses == null || materialResponses.isEmpty()) {
            // 더 이상 자료가 없습니다. 예외 발생 또는 메시지 전달 등의 처리
            throw new NotFoundException(NOT_FOUND_MATERIAL);
        }

        int totalPages = materialPage.getTotalPages();
        boolean isLastPage = page >= totalPages;

        return SingleMaterialListResponse.of(page, materialResponses, isLastPage);
    }

    /**
     * 내 학과애서 최근업로드순의 모두보기 수행
     * @author 김태혁
     * @version 0.0.1
     */
    public SingleMaterialListResponse getMyMajorUploadList(int page, int pageSize, HttpServletRequest request) {
        Pageable pageable = PageRequest.of(page - 1, pageSize);

        Object kakaoIdAttribute = request.getAttribute("kakaoId");

        if (kakaoIdAttribute == null) {
            // 카카오 아이디가 없을 때의 예외 처리 또는 메시지 전달 등의 처리
            throw new NotFoundException(NOT_FOUND_KAKAOID);
        }

        Long kakaoId = Long.parseLong(kakaoIdAttribute.toString());

        // 카카오 아이디에 해당하는 값 조회
        KakaoSocialLogin kakaoSocialLogin = kakaoSocialLoginRepository.findById(kakaoId).orElse(null);
        if (kakaoSocialLogin == null || kakaoSocialLogin.getMember() == null || kakaoSocialLogin.getMember().getMajor1() == null) {
            // 카카오 아이디에 해당하는 값이 없을 때의 예외 처리 또는 메시지 전달 등의 처리
            throw new NotFoundException(NOT_FOUND_INFO_FROM_KAKAOID);
        }

        String major = kakaoSocialLogin.getMember().getMajor1();
        Page<Material> materialPage = materialRepository.findByStatusAndMajorAndMemberStatusOrderByCreatedAtDescIdAsc("active",major, "active", pageable);

        List<MaterialResponse> materialResponses = convertToMaterialResponseList(materialPage.getContent());
        if (materialResponses == null || materialResponses.isEmpty()) {
            // 더 이상 자료가 없습니다. 예외 발생 또는 메시지 전달 등의 처리
            throw new NotFoundException(NOT_FOUND_MATERIAL);
        }

        int totalPages = materialPage.getTotalPages();
        boolean isLastPage = page >= totalPages;

        return SingleMaterialListResponse.of(page, materialResponses, isLastPage);
    }

    /**
     * 내 학과애서 좋아요 많은 순의 모두보기 수행
     * @author 김태혁
     * @version 0.0.1
     */
    public SingleMaterialListResponse getMyMajorLike(int page, int pageSize, HttpServletRequest request) {
        Pageable pageable = PageRequest.of(page - 1, pageSize);
        Object kakaoIdAttribute = request.getAttribute("kakaoId");

        if (kakaoIdAttribute == null) {
            // 카카오 아이디가 없을 때의 예외 처리 또는 메시지 전달 등의 처리
            throw new NotFoundException(NOT_FOUND_KAKAOID);
        }

        Long kakaoId = Long.parseLong(kakaoIdAttribute.toString());

        // 카카오 아이디에 해당하는 값 조회
        KakaoSocialLogin kakaoSocialLogin = kakaoSocialLoginRepository.findById(kakaoId).orElse(null);
        if (kakaoSocialLogin == null || kakaoSocialLogin.getMember() == null || kakaoSocialLogin.getMember().getMajor1() == null) {
            // 카카오 아이디에 해당하는 값이 없을 때의 예외 처리 또는 메시지 전달 등의 처리
            throw new NotFoundException(NOT_FOUND_INFO_FROM_KAKAOID);
        }

        String major = kakaoSocialLogin.getMember().getMajor1();
        Page<Material> materialPage = materialRepository.findByStatusAndMajorAndMemberStatusOrderByTotalRecommendDescIdAsc("active",major, "active", pageable);

        List<MaterialResponse> materialResponses = convertToMaterialResponseList(materialPage.getContent());
        if (materialResponses == null || materialResponses.isEmpty()) {
            // 더 이상 자료가 없습니다. 예외 발생 또는 메시지 전달 등의 처리
            throw new NotFoundException(NOT_FOUND_MATERIAL);
        }

        int totalPages = materialPage.getTotalPages();
        boolean isLastPage = page >= totalPages;

        return SingleMaterialListResponse.of(page, materialResponses, isLastPage);
    }

    /**
     * 이 수업은 다른자료 모두기보기를 수행
     * @author 김태혁
     * @version 0.0.1
     */
    public NameMaterialListResponse getAllSubjectNew(int page, int pageSize, Long materialID) {
        Pageable pageable = PageRequest.of(page - 1, pageSize);
//        Material material = materialRepository.findById(materialID).orElse(null);
        Material material = materialRepository.findById(materialID).orElseThrow(() -> new NotFoundException(NOT_FOUND_MATERIAL_FROM_MEMBER_ID));

        Page<Material> materialPage = materialRepository.findByStatusAndClassNameAndProfessorAndMember_UniversityNameAndMajorAndMemberStatus("active",material.getClassName(), material.getProfessor(), material.getMember().getUniversityName(), material.getMajor(), "active", pageable);

        List<NameMaterialResponse> nameMaterialResponses = convertToMaterialNamedResponseList(materialPage.getContent());
        if (nameMaterialResponses == null || nameMaterialResponses.isEmpty()) {
            // 더 이상 자료가 없습니다. 예외 발생 또는 메시지 전달 등의 처리
            throw new NotFoundException(NOT_FOUND_MATERIAL);
        }

        int totalPages = materialPage.getTotalPages();
        boolean isLastPage = page >= totalPages;

        return NameMaterialListResponse.of(page, nameMaterialResponses, isLastPage);
    }

    /**
     * 이 판매자의 다른 자료 모두보기를 수행
     * @author 김태혁
     * @version 0.0.1
     */
    public SellerMaterialListResponse getSellerList(int page, int pageSize, String nickName) {
        Member member = memberRepository.findByNickName(nickName);
        if (member == null) {
            // 해당 닉네임에 대한 멤버를 찾을 수 없을 때의 예외 처리 또는 메시지 전달 등의 처리
            throw new NotFoundException(NOT_FOUND_MEMBER_FROM_NICKNAME);
        }

        Pageable pageable = PageRequest.of(page - 1, pageSize);
        Page<Material> materialPage = materialRepository.findByStatusAndMemberAndMemberStatus("active",member, "active", pageable);
        List<SellerMaterialResponse> nameMaterialResponses = convertToMaterialSellerResponseList(materialPage.getContent());

        if (nameMaterialResponses == null || nameMaterialResponses.isEmpty()) {
            // 더 이상 자료가 없습니다. 예외 발생 또는 메시지 전달 등의 처리
            throw new NotFoundException(NOT_FOUND_MATERIAL);
        }

        int totalPages = materialPage.getTotalPages();
        boolean isLastPage = page >= totalPages;

        return SellerMaterialListResponse.of(page, nameMaterialResponses, isLastPage);
    }

    /**
     * MaterialResponse로 반환
     * @author 김태혁
     * @version 0.0.1
     */
    private List<MaterialResponse> convertToMaterialResponseList(List<Material> materials) {

        return materials.stream()
                .map(MaterialResponse::of)
                .collect(Collectors.toList());
    }

    /**
     * NameMaterialResponse 반환
     * @author 김태혁
     * @version 0.0.1
     */
    private List<NameMaterialResponse> convertToMaterialNamedResponseList(List<Material> materials) {

        return materials.stream()
                .map(NameMaterialResponse::of)
                .collect(Collectors.toList());
    }

    /**
     * SellerMaterialResponse 반환
     * @author 김태혁
     * @version 0.0.1
     */
    private List<SellerMaterialResponse> convertToMaterialSellerResponseList(List<Material> materials) {

        return materials.stream()
                .map(SellerMaterialResponse::of)
                .collect(Collectors.toList());
    }



}
