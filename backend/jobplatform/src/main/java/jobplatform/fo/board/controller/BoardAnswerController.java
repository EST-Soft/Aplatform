package jobplatform.fo.board.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import jobplatform.fo.board.entity.BoardAnswerEntity;
import jobplatform.fo.board.service.BoardAnswerService;
import jobplatform.fo.sample.util.Header;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/answer")
public class BoardAnswerController {

    private final BoardAnswerService boardAnswerService;

    // 의존성 주입을 통해 서비스 초기화
    public BoardAnswerController(BoardAnswerService boardAnswerService) {
        this.boardAnswerService = boardAnswerService;
    }

    

    /*
     * 답변 등록
     */
    @PostMapping("")
    public ResponseEntity<?> insertOne(@RequestBody BoardAnswerEntity answer) {
        try {
            System.out.println("컨트롤러 answer 값 : " + answer);
            int result = boardAnswerService.insertOne(answer);
            System.out.println("result :" + result);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error inserting answer: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /*
     * 답변 수, 답변 채택여부 확인
     */
    @GetMapping("/{brdSq}")
    public ResponseEntity<?> findAnswer(@PathVariable int brdSq) {
        try {
            System.out.println("brdSq 값 : " + brdSq);
            Map<String, Integer> result = new HashMap<>();
            int checkAnswer = boardAnswerService.checkAnswer(brdSq);
            int countAnswer = boardAnswerService.countAnswer(brdSq);
            result.put("checkAnswer", checkAnswer);
            result.put("countAnswer", countAnswer);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error checking answer: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 답변 리스트 불러오기
     */
    @GetMapping("/qna/{brdSq}")
    public ResponseEntity<?> answerList(@PathVariable int brdSq,
                                        @RequestParam(defaultValue = "0") int page,
                                        @RequestParam(defaultValue = "3") int size) {
        try {
            System.out.println("Fetching answer list for brdSq: " + brdSq);
            Header<List<BoardAnswerEntity>> answerList = boardAnswerService.List(page, size, brdSq);
            System.out.println("Fetched answer list: " + answerList);
            
            return ResponseEntity.ok(answerList);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("답글 목록을 불러오는 중 오류가 발생했습니다.");
        }
    }

    /*
     * 답글 상세내용
     */
    @GetMapping("/detail/{answrSq}")
    public ResponseEntity<?> getAnswerDetail(@PathVariable int answrSq) {
        try {
            BoardAnswerEntity answer = boardAnswerService.answer(answrSq);
            System.out.println("controller에서 answer는 무슨값?" + answer);
            return ResponseEntity.ok(answer);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("답글 상세내용을 불러오는 중 오류가 발생했습니다.");
        }
    }

    /*
     * 조회수 증가
     */
    @PostMapping("/updateHits/{answrSq}")
    public ResponseEntity<?> updateHits(@PathVariable int answrSq) {
        try {
            int result = boardAnswerService.updateHits(answrSq);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("조회수 업데이트 중 오류 발생");
        }
    }

    /*
     * 추천 여부 확인
     */
    @PostMapping("/recommendationCheck")
    public ResponseEntity<?> check(@RequestParam int answrSq, @RequestParam int mbrSq) {
        try {
            int result = boardAnswerService.checkRecommendation(answrSq, mbrSq);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("추천 조회 오류 발생");
        }
    }

    /*
     * 비추천 여부 확인
     */
    @PostMapping("/notRecommendationCheck")
    public ResponseEntity<?> notCheck(@RequestParam int answrSq, @RequestParam int mbrSq) {
        try {
            int result = boardAnswerService.checkNotRecommendation(answrSq, mbrSq);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("비추천 조회 오류 발생");
        }
    }

    /*
     * 추천 업데이트
     */
    @PostMapping("/recommendation/{answrSq}")
    public ResponseEntity<?> updateRecommendation(@PathVariable int answrSq, @RequestParam int mbrSq, @RequestParam int value) {
        try {
            int result = boardAnswerService.updateRecommendation(answrSq, mbrSq, value);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("추천하기 중 오류가 발생했습니다");
        }
    }

    /*
     * 비추천 업데이트
     */
    @PostMapping("/notRecommendation/{answrSq}")
    public ResponseEntity<?> updateNotRecommendation(@PathVariable int answrSq, @RequestParam int mbrSq, @RequestParam int value) {
        try {
            int result = boardAnswerService.updateNotRecommendation(answrSq, mbrSq, value);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("비추천하기 중 오류가 발생했습니다");
        }
    }

    /*
     * 답변 채택
     */
    @PatchMapping("/selection/{answrSq}/{brdSq}")
    public ResponseEntity<?> selectRecommendation(@PathVariable int answrSq, @PathVariable int brdSq) {
        try {
            int result = boardAnswerService.selectRecommendation(answrSq, brdSq);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("채택하기 중 오류 발생");
        }
    }

    /*
     * 답글 삭제
     */
    @DeleteMapping("/delete/{answrSq}")
    public ResponseEntity<?> deleteAnswer(@PathVariable int answrSq) {
        try {
            int result = boardAnswerService.deleteAnswer(answrSq);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("답글 삭제 중 오류 발생");
        }
    }

    /*
     * 답변 수정
     */
    @PatchMapping("")
    public ResponseEntity<?> editAnswer(@RequestBody BoardAnswerEntity boardAnswerEntity) {
        try {
            System.out.println(boardAnswerEntity); // 수정된 답변 정보 확인
            boardAnswerService.editAnswer(boardAnswerEntity); // 서비스에서 답변 수정
            return ResponseEntity.ok(1); // 성공 응답
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("답변 수정 중 오류 발생");
        }
    }
}
