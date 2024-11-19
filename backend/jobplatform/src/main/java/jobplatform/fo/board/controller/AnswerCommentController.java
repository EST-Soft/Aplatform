package jobplatform.fo.board.controller;

import jobplatform.fo.board.entity.AnswerCommentEntity;
import jobplatform.fo.board.service.AnswerCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/comments")
public class AnswerCommentController {

    @Autowired
    private AnswerCommentService answerCommentService;

    @GetMapping("/answer/{answrSq}")
    public List<AnswerCommentEntity> getCommentsByAnswerId(@PathVariable long answrSq) {
        System.out.println("Controller answrSq: " + answrSq);
        List<AnswerCommentEntity> comments = answerCommentService.getCommentsByAnswerId(answrSq);
        System.out.println("Controller 반환 댓글 리스트: " + comments); // 댓글 데이터 출력
        return comments;
    }


    @PostMapping("/answer/{answrSq}")
    public void addComment(@PathVariable long answrSq, @RequestBody AnswerCommentEntity comment) {
        comment.setAnswrSq(answrSq);
        answerCommentService.addComment(comment);
    }

    @PutMapping("/{cmntSq}")
    public void updateComment(@PathVariable long cmntSq, @RequestBody String newContent) {
        try {
            // JSON 문자열을 Map으로 변환
            ObjectMapper objectMapper = new ObjectMapper();
            Map<String, String> map = objectMapper.readValue(newContent, Map.class);

            // "cmntCntnt" 키의 값만 추출
            String content = map.get("cmntCntnt");
            System.out.println("Extracted Content: " + content);

            // 서비스에 전달
            answerCommentService.updateComment(cmntSq, content);
        } catch (Exception e) {
            throw new RuntimeException("Failed to parse JSON content: " + e.getMessage());
        }
    }

    @DeleteMapping("/{cmntSq}")
    public void deleteComment(@PathVariable long cmntSq) {
        answerCommentService.deleteComment(cmntSq);
    }
}
