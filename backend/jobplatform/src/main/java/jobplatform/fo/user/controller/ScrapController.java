package jobplatform.fo.user.controller;

import jobplatform.fo.user.domain.service.ScrapService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/scrap")
@RequiredArgsConstructor
public class ScrapController {

    private final ScrapService scrapService;

    @PostMapping
    public ResponseEntity<String> addScrap(
            @RequestParam Long mbrSq,
            @RequestParam Long scrapTargetSq,
            @RequestParam String scrapTargetType) {
        // 필수 파라미터 유효성 확인
        if (mbrSq == null || scrapTargetSq == null || scrapTargetType == null) {
            return ResponseEntity.badRequest().body("필수 파라미터가 누락되었습니다.");
        }

        // scrapTargetType 검증
        if (!"POST".equals(scrapTargetType) && !"ANSWER".equals(scrapTargetType)) {
            return ResponseEntity.badRequest().body("유효하지 않은 스크랩 대상 유형입니다.");
        }

        scrapService.addScrap(mbrSq, null, scrapTargetSq, scrapTargetType);
        return ResponseEntity.ok("스크랩이 성공적으로 추가되었습니다.");
    }



    @PatchMapping("/{scrapTargetSq}")
    public ResponseEntity<String> deleteScrap(@PathVariable Long scrapTargetSq) {
        scrapService.deleteScrap(scrapTargetSq);
        return ResponseEntity.ok("스크랩이 성공적으로 삭제되었습니다.");
    }

    @GetMapping
    public ResponseEntity<List<Object>> getScrapListByMember(@RequestParam Long mbrSq) {
        List<Object> scrapList = scrapService.getScrapListByMember(mbrSq);
        return ResponseEntity.ok(scrapList);
    }

    @GetMapping("/exists")
    public ResponseEntity<Boolean> checkScrapExists(
            @RequestParam Long mbrSq,
            @RequestParam Long scrapTargetSq,
            @RequestParam String scrapTargetType) {
        boolean exists = scrapService.checkScrapExists(mbrSq, scrapTargetSq, scrapTargetType);
        System.out.println("exists123123 : " + exists);
        return ResponseEntity.ok(exists);
    }
}
