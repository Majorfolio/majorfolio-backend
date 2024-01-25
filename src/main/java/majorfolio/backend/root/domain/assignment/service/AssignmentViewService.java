package majorfolio.backend.root.domain.assignment.service;

import lombok.RequiredArgsConstructor;
import majorfolio.backend.root.domain.assignment.dto.response.AssignmentResponse;
import majorfolio.backend.root.domain.assignment.entity.Assignment;
import majorfolio.backend.root.domain.assignment.entity.AssignmentStats;
import majorfolio.backend.root.domain.assignment.repository.AssignmentRepository;
import majorfolio.backend.root.domain.assignment.repository.AssignmentStatsRepository;
import majorfolio.backend.root.domain.member.entity.Member;
import majorfolio.backend.root.domain.member.repository.MemberRepository;
import majorfolio.backend.root.domain.university.entity.Subjects;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AssignmentViewService {

    private final AssignmentRepository assignmentRepository;
    private final MemberRepository memberRepository;
    private final AssignmentStatsRepository assignmentStatsRepository;


    public List<AssignmentResponse> getNewUploadAssignments() {
        List<Assignment> newUploadAssignments = assignmentRepository.findTop5ByOrderByCreatedTimeDesc(); // Assuming Assignment has a 'createdAt' field
        return mapToAssignmentResponseList(newUploadAssignments);
    }

    public List<AssignmentResponse> getBestAssignments() {
        List<AssignmentStats> bestAssignmentStats = assignmentStatsRepository.findTop5ByOrderByLikesDesc();
        List<Assignment> bestAssignments = bestAssignmentStats.stream()
                .map(AssignmentStats::getAssignment)
                .collect(Collectors.toList());
        return mapToAssignmentResponseList(bestAssignments);
    }

    public List<AssignmentResponse> getLatestAssignments() {
        List<Assignment> latestAssignments = assignmentRepository.findTop5ByOrderByViewTimestampDesc(); // Assuming Assignment has an 'updatedAt' field
        return mapToAssignmentResponseList(latestAssignments);
    }

    private List<AssignmentResponse> mapToAssignmentResponseList(List<Assignment> assignments) {
        return assignments.stream()
                .map(this::mapToAssignmentResponse)
                .collect(Collectors.toList());
    }

    private AssignmentResponse mapToAssignmentResponse(Assignment assignment){
        Member member = assignment.getMember();
        Subjects subjects = assignment.getSubjects();
        AssignmentStats assignmentStats = assignmentStatsRepository.findByAssignment(assignment);

        return AssignmentResponse.of(
                assignment.getId(),
                member.getNickname(),
                subjects.getName(),
                subjects.getUniversity().getName(),
                subjects.getMajor(),
                assignmentStats.getLikes());
    }


}
