package majorfolio.backend.root.domain.assignment.api;

import lombok.RequiredArgsConstructor;
import majorfolio.backend.root.domain.assignment.dto.response.AssignmentResponse;
import majorfolio.backend.root.domain.assignment.dto.response.ListAssignmentResponse;
import majorfolio.backend.root.domain.assignment.service.AssignmentViewService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/home")
public class HomeAssignmentController {

    private final AssignmentViewService assignmentViewService;

    @GetMapping("/all/univ")
    public ListAssignmentResponse allUniv() {
        List<AssignmentResponse> newUploadList = assignmentViewService.getNewUploadAssignments();
        List<AssignmentResponse> bestList = assignmentViewService.getBestAssignments();
        List<AssignmentResponse> latestList = assignmentViewService.getLatestAssignments();
        return ListAssignmentResponse.of(newUploadList, bestList, latestList);
    }
}
