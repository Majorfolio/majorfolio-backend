package majorfolio.backend.root.domain.content.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import majorfolio.backend.root.domain.content.dto.response.ContentsListResponse;
import majorfolio.backend.root.domain.content.dto.response.ContentsResponse;
import majorfolio.backend.root.domain.content.repository.EventBannerRepository;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ContentService {

    private final EventBannerRepository eventBannerRepository;

    public ContentsListResponse banner_contents() {
        List<ContentsResponse> contentsList = eventBannerRepository.findAll()
                .stream()
                .map(ContentsResponse::of)
                .collect(Collectors.toList());

        return ContentsListResponse.of(contentsList);
    }



}
