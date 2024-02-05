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
        Page<Material> materialPage = materialRepository.findByOrderByCreatedAtDescIdAsc(pageable);

        List<MaterialResponse> materialResponses = convertToMaterialResponseList(materialPage.getContent());

        if (materialResponses == null || materialResponses.isEmpty()) {
            // 더 이상 자료가 없습니다. 예외 발생 또는 메시지 전달 등의 처리
            throw new NotFoundException("자료가 없습니다.");
        }

        return SingleMaterialListResponse.of(page, materialResponses);
    }

    /**
     * 모든학교애서 좋아요 많은 순의 모두보기 수행
     * @author 김태혁
     * @version 0.0.1
     */
    public SingleMaterialListResponse getAllUnivLike(int page, int pageSize) {
        Pageable pageable = PageRequest.of(page - 1, pageSize);
        Page<Material> materialPage = materialRepository.findByOrderByTotalRecommendDescIdAsc(pageable);

        List<MaterialResponse> materialResponses = convertToMaterialResponseList(materialPage.getContent());
        if (materialResponses == null || materialResponses.isEmpty()) {
            // 더 이상 자료가 없습니다. 예외 발생 또는 메시지 전달 등의 처리
            throw new NotFoundException("자료가 없습니다.");
        }

        return SingleMaterialListResponse.of(page, materialResponses);
    }

    /**
     * 내 학교애서 최근업로드순의 모두보기 수행
     * @author 김태혁
     * @version 0.0.1
     */
    public SingleMaterialListResponse getMyUnivUploadList(int page, int pageSize, HttpServletRequest request) {
        Pageable pageable = PageRequest.of(page - 1, pageSize);

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
        Page<Material> materialPage = materialRepository.findByMemberUniversityNameOrderByCreatedAtDescIdAsc(univName, pageable);

        List<MaterialResponse> materialResponses = convertToMaterialResponseList(materialPage.getContent());
        if (materialResponses == null || materialResponses.isEmpty()) {
            // 더 이상 자료가 없습니다. 예외 발생 또는 메시지 전달 등의 처리
            throw new NotFoundException("자료가 없습니다.");
        }

        return SingleMaterialListResponse.of(page, materialResponses);
    }

    /**
     * 내 학교애서 좋아요 많은 순의 모두보기 수행
     * @author 김태혁
     * @version 0.0.1
     */
    public SingleMaterialListResponse getMyUnivLike(int page, int pageSize, HttpServletRequest request) {
        Pageable pageable = PageRequest.of(page - 1, pageSize);

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
        Page<Material> materialPage = materialRepository.findByMemberUniversityNameOrderByTotalRecommendDescIdAsc(univName, pageable);

        List<MaterialResponse> materialResponses = convertToMaterialResponseList(materialPage.getContent());
        if (materialResponses == null || materialResponses.isEmpty()) {
            // 더 이상 자료가 없습니다. 예외 발생 또는 메시지 전달 등의 처리
            throw new NotFoundException("더 이상 자료가 없습니다.");
        }

        return SingleMaterialListResponse.of(page, materialResponses);
    }

    /**
     * 내 학과애서 최근업로드순의 모두보기 수행
     * @author 김태혁
     * @version 0.0.1
     */
    public SingleMaterialListResponse getMyMajorUploadList(int page, int pageSize, HttpServletRequest request) {
        Pageable pageable = PageRequest.of(page - 1, pageSize);

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
        Page<Material> materialPage = materialRepository.findByMajorOrderByCreatedAtDescIdAsc(major, pageable);

        List<MaterialResponse> materialResponses = convertToMaterialResponseList(materialPage.getContent());
        if (materialResponses == null || materialResponses.isEmpty()) {
            // 더 이상 자료가 없습니다. 예외 발생 또는 메시지 전달 등의 처리
            throw new NotFoundException("더 이상 자료가 없습니다.");
        }

        return SingleMaterialListResponse.of(page, materialResponses);
    }

    /**
     * 내 학과애서 좋아요 많은 순의 모두보기 수행
     * @author 김태혁
     * @version 0.0.1
     */
    public SingleMaterialListResponse getMyMajorLike(int page, int pageSize, HttpServletRequest request) {
        Pageable pageable = PageRequest.of(page - 1, pageSize);
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
        Page<Material> materialPage = materialRepository.findByMajorOrderByTotalRecommendDescIdAsc(major, pageable);

        List<MaterialResponse> materialResponses = convertToMaterialResponseList(materialPage.getContent());
        if (materialResponses == null || materialResponses.isEmpty()) {
            // 더 이상 자료가 없습니다. 예외 발생 또는 메시지 전달 등의 처리
            throw new NotFoundException("더 이상 자료가 없습니다.");
        }

        return SingleMaterialListResponse.of(page, materialResponses);
    }

    /**
     * 이 수업은 다른자료 모두기보기를 수행
     * @author 김태혁
     * @version 0.0.1
     */
    public NameMaterialListResponse getAllSubjectNew(int page, int pageSize, Long materialID) {
        Pageable pageable = PageRequest.of(page - 1, pageSize);
//        Material material = materialRepository.findById(materialID).orElse(null);
        Material material = materialRepository.findById(materialID).orElseThrow(() -> new NotFoundException("해당 ID에 대한 자료를 찾을 수 없습니다."));

        Page<Material> materialPage = materialRepository.findByClassNameAndProfessorAndMember_UniversityNameAndMajor(material.getClassName(), material.getProfessor(), material.getMember().getUniversityName(), material.getMajor(), pageable);

        List<NameMaterialResponse> nameMaterialResponses = convertToMaterialNamedResponseList(materialPage.getContent());
        if (nameMaterialResponses == null || nameMaterialResponses.isEmpty()) {
            // 더 이상 자료가 없습니다. 예외 발생 또는 메시지 전달 등의 처리
            throw new NotFoundException("더 이상 자료가 없습니다.");
        }

        return NameMaterialListResponse.of(page, nameMaterialResponses);
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
            throw new NotFoundException("해당 닉네임에 대한 멤버를 찾을 수 없습니다.");
        }

        Pageable pageable = PageRequest.of(page - 1, pageSize);
        Page<Material> materialPage = materialRepository.findByMember(member, pageable);
        List<SellerMaterialResponse> nameMaterialResponses = convertToMaterialSellerResponseList(materialPage.getContent());

        if (nameMaterialResponses == null || nameMaterialResponses.isEmpty()) {
            // 더 이상 자료가 없습니다. 예외 발생 또는 메시지 전달 등의 처리
            throw new NotFoundException("더 이상 자료가 없습니다.");
        }

        return SellerMaterialListResponse.of(page, nameMaterialResponses);
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
