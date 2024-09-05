package jobplatform.fo.board.controller;

import jobplatform.fo.board.entity.BoardEntity;
import jobplatform.fo.board.service.BoardService;
import jobplatform.fo.sample.util.Header;
import jobplatform.fo.sample.util.Search;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/board")
@RestController
public class BoardController {
    private final BoardService boardService;

    public BoardController(BoardService boardService) {
        this.boardService = boardService;
    }

    @PostMapping("") // 등록
    public ResponseEntity<?> BoardInsert(@RequestBody BoardEntity boardEntity) {
        try {
            System.out.println(boardEntity);
            int result = boardService.register(boardEntity);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            System.out.println("Error during board insertion: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("게시글 등록 중 오류가 발생했습니다.");
        }
    }

    @GetMapping("") // 리스트
    public ResponseEntity<?> boardList(@RequestParam(defaultValue = "general") String brdTypCode,
                                       @RequestParam(defaultValue = "0") int page,
                                       @RequestParam(defaultValue = "10") int size,
                                       Search search) {
        try {
            System.out.println(brdTypCode);
            Header<List<BoardEntity>> boardList = boardService.List(page, size, search, brdTypCode);
            System.out.println("게시판에서는 어떻게 출력? " + boardList);
            return ResponseEntity.ok(boardList);
        } catch (Exception e) {
            System.out.println("Error during fetching board list: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("게시글 목록을 불러오는 중 오류가 발생했습니다.");
        }
    }

    @GetMapping("/{brdSq}") // 상세보기
    public ResponseEntity<?> boardDetail(@PathVariable int brdSq) {
        try {
            System.out.println(brdSq);
            BoardEntity board = boardService.board(brdSq);
            if (board == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("해당 게시글을 찾을 수 없습니다.");
            }
            return ResponseEntity.ok(board);
        } catch (Exception e) {
            System.out.println("Error during fetching board details: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("게시글 상세 조회 중 오류가 발생했습니다.");
        }
    }

    @PatchMapping("") // 수정
    public ResponseEntity<?> boardUpdate(@RequestBody BoardEntity boardEntity) {
        try {
            System.out.println(boardEntity);
            boardService.modify(boardEntity);
            return ResponseEntity.ok(1);
        } catch (Exception e) {
            System.out.println("Error during board update: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("게시글 수정 중 오류가 발생했습니다.");
        }
    }

    @DeleteMapping("/{brdSq}") // 삭제
    public ResponseEntity<?> boardDelete(@PathVariable int brdSq) {
        try {
            boardService.destroy(brdSq);
            return ResponseEntity.ok(1);
        } catch (Exception e) {
            System.out.println("Error during board deletion: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("게시글 삭제 중 오류가 발생했습니다.");
        }
    }
}
