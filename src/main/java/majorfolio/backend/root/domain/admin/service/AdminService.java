package majorfolio.backend.root.domain.admin.service;

import lombok.RequiredArgsConstructor;
import majorfolio.backend.root.domain.admin.dto.request.PostNoticeRequest;
import majorfolio.backend.root.domain.admin.dto.response.PostNoticeResponse;
import majorfolio.backend.root.domain.admin.repository.AdminRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class AdminService {
    private final AdminRepository adminRepository;

    /**
     * 공지사항 글쓰기 서비스 구현
     * @param postNoticeRequest
     * @return
     */
    public PostNoticeResponse postNotice(PostNoticeRequest postNoticeRequest) {
        MultipartFile multipartFile = postNoticeRequest.getMultipartFile();
        String fileName = multipartFile.getName();

    }
}
