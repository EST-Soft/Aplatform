package jobplatform.fo.board.controller;

import java.util.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import jobplatform.fo.board.entity.BoardAnswerEntity;
import jobplatform.fo.board.service.BoardAnswerService;
import jobplatform.fo.sample.util.Header;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.amazonaws.Response;



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
         try{
            System.out.println("컨트롤러 answer 값 : " +answer);
            int result = boardAnswerService.insertOne(answer);
            System.out.println("result :" + result);
            return new ResponseEntity<>(result, HttpStatus.OK);
         }catch (Exception e){
            return new ResponseEntity<>("Error inserting answer: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
         }
     }

     /*
      * 답변 수, 답변 채택여부 확인
      *
      */

     @GetMapping("/{brdSq}")
     public ResponseEntity<?> findAnswer(@PathVariable int brdSq) {
        try{
            // System.out.println("brdSq 값 : " + brdSq);
            Map<String, Integer> result = new HashMap<>();
            int checkAnswer = boardAnswerService.checkAnswer(brdSq);
            int countAnswer = boardAnswerService.countAnswer(brdSq);
            result.put("checkAnswer", checkAnswer);
            result.put("countAnswer", countAnswer);
            return new ResponseEntity<>(result, HttpStatus.OK);

        }catch(Exception e){
            return new ResponseEntity<>("Error checking answer: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
     }


     /*
      * 답변 List 불러오기
      */

     @GetMapping("/qna/{brdSq}")
     public ResponseEntity<?> answerList(@PathVariable int brdSq,
                                         @RequestParam(defaultValue = "0") int page,
                                         @RequestParam(defaultValue = "5") int size) {

        try{
            Header<List<BoardAnswerEntity>> answerList = boardAnswerService.List(page, size, brdSq);
            return ResponseEntity.ok(answerList);
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("답글 목록을 불러오는 중 오류가 발생했습니다.");
        }
        
     }


     /*
      * 답글 상세내용
      *
      */

      @GetMapping("/detail/{answrSq}")
      public ResponseEntity<?> getAnswerDetail(@PathVariable int answrSq) {
        try{
            BoardAnswerEntity answer = boardAnswerService.answer(answrSq);
            // System.out.println("여기서 answer는 무슨값?" + answer);
            return ResponseEntity.ok(answer);
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("답글 상세내용을 불러오는 중 오류가 발생했습니다.");
        }
         
      }

      @PostMapping("/updateHits/{answrSq}")
      public ResponseEntity<?> updateHits(@PathVariable int answrSq) {
          
        try{
            int result = boardAnswerService.updateHits(answrSq);
            return ResponseEntity.ok(result);
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("조회수 업데이트중 오류 발생");
        }
          
          
      }
      

      @PostMapping("/recommendationCheck")
      public ResponseEntity<?> check(@RequestParam int answrSq, @RequestParam int mbrSq) {
          
        try{
            int result = boardAnswerService.checkRecommendation(answrSq, mbrSq);
            return ResponseEntity.ok(result);
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("추천 조회 오류 발생");
        } 
      }


      @PostMapping("/recommendation/{answrSq}")
      public ResponseEntity<?> updateRecommendation(@PathVariable int answrSq, @RequestParam int mbrSq, @RequestParam int value) {
        // System.out.println("answrSq : " + answrSq + "mbrSq : " + mbrSq );
        
          try{
            int result = boardAnswerService.updateRecommendation(answrSq, mbrSq, value);
            return ResponseEntity.ok(result);
          }catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("추천하기 중 오류가 발생했습니다");
          }
      }


      @PatchMapping("/selection/{answrSq}")
      public ResponseEntity<?> selectRecommendation(@PathVariable int answrSq){
        try{
            int result = boardAnswerService.selectRecommendation(answrSq);
            return ResponseEntity.ok(result);
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("채택하기 중 오류 발생");
        }
      }

      @DeleteMapping("/delete/{answrSq}")
      public ResponseEntity<?> deleteAnswer(@PathVariable int answrSq){
        
        try{
            int result = boardAnswerService.deleteAnswer(answrSq);
            return ResponseEntity.ok(result);
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("답글 삭제 중 오류 발생");
        }

      }


      /* 답변 수정 */
      @PatchMapping("")
      public ResponseEntity<?> editAnswer(@RequestBody BoardAnswerEntity boardAnswerEntity){
        try{ 
            System.out.println(boardAnswerEntity);
            boardAnswerService.editAnswer(boardAnswerEntity);            
            return ResponseEntity.ok(1);

        }catch(Exception e){
            e.printStackTrace();
        }
        
        return null;
      }
      
      
      
     
     
     

















}
