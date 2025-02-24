package jobplatform.fo.board.controller;

import jobplatform.fo.board.entity.BoardCommentEntity;
import jobplatform.fo.board.service.BoardCommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
/*댓글 컨트롤러*/
@Controller
@RequestMapping("/comment")
public class BoardCommentController {
    private final BoardCommentService boardCommentService;

    // 의존성 주입을 통해 서비스 초기화
    public BoardCommentController(BoardCommentService boardCommentService) {
        this.boardCommentService = boardCommentService;
    }

    /**
     * 댓글 등록 메소드
     * @param comment 등록할 댓글 정보
     * @return 등록 결과 상태와 함께 HTTP 응답
     */
    @PostMapping("")
    public ResponseEntity<?> insertOne(@RequestBody BoardCommentEntity comment) {
        try {
            // 서비스에서 댓글 등록
            long result = boardCommentService.findOne(comment);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            // 예외 발생 시 내부 서버 오류 반환
            return new ResponseEntity<>("Error inserting comment: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 댓글 수정 메소드
     * @param comment 수정할 댓글 정보
     * @return 수정 결과 상태와 함께 HTTP 응답
     */
    @PatchMapping("")
    public ResponseEntity<?> update(@RequestBody BoardCommentEntity comment) {
        try {
            // 수정 시간 업데이트
            comment.setUpdtDtm(LocalDateTime.now());

            // 서비스에서 댓글 수정
            int result = boardCommentService.update(comment);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            // 예외 발생 시 내부 서버 오류 반환
            return new ResponseEntity<>("Error updating comment: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 댓글 삭제 메소드
     * @param cmntSq 삭제할 댓글의 ID
     * @return 삭제 결과 상태와 함께 HTTP 응답
     */
    @DeleteMapping("/{cmntSq}")
    public ResponseEntity<?> delete(@PathVariable Long cmntSq) {
        try {
            // 서비스에서 댓글 삭제
            int result = boardCommentService.destroy(cmntSq);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            // 예외 발생 시 내부 서버 오류 반환
            return new ResponseEntity<>("Error deleting comment: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
