package jobplatform.fo.board.controller;

import jobplatform.fo.board.entity.AnswerCommentEntity;
import jobplatform.fo.board.service.AnswerCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comments")
public class AnswerCommentController {

    @Autowired
    private AnswerCommentService answerCommentService;

    @GetMapping("/answer/{answrSq}")
    public List<AnswerCommentEntity> getCommentsByAnswerId(@PathVariable long answrSq) {
        return answerCommentService.getCommentsByAnswerId(answrSq);
    }

    @PostMapping("/answer/{answrSq}")
    public void addComment(@PathVariable long answrSq, @RequestBody AnswerCommentEntity comment) {
        comment.setAnswrSq(answrSq);
        answerCommentService.addComment(comment);
    }

    @PutMapping("/{cmntSq}")
    public void updateComment(@PathVariable long cmntSq, @RequestBody String newContent) {
        answerCommentService.updateComment(cmntSq, newContent);
    }

    @DeleteMapping("/{cmntSq}")
    public void deleteComment(@PathVariable long cmntSq) {
        answerCommentService.deleteComment(cmntSq);
    }
}
