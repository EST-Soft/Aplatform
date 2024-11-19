package jobplatform.fo.report.controller;

import jobplatform.fo.report.entity.IssueTrackEntity;
import jobplatform.fo.report.service.IssueTrackService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/issues")
@RequiredArgsConstructor
public class IssueTrackController {

    private final IssueTrackService issueTrackService;

    @PostMapping
    public ResponseEntity<String> createIssue(@RequestBody IssueTrackEntity issue) {
        // 서비스 호출을 통해 데이터 저장
        issueTrackService.saveIssue(issue);
        return ResponseEntity.ok("신고가 접수되었습니다.");
    }
}
