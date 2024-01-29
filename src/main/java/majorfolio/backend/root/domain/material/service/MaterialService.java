package majorfolio.backend.root.domain.material.service;


import lombok.RequiredArgsConstructor;
import majorfolio.backend.root.domain.material.dto.response.MaterialListResponse;
import majorfolio.backend.root.domain.material.dto.response.MaterialResponse;
import majorfolio.backend.root.domain.material.entity.Material;
import majorfolio.backend.root.domain.material.repository.MaterialRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MaterialService {
    private final MaterialRepository materialRepository;

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
}
