//package jobplatform.fo.sample.controller;
//
//import lombok.RequiredArgsConstructor;
//import lombok.extern.log4j.Log4j2;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.*;
//
//import jobplatform.fo.sample.db.SampleBoardEntity;
//import jobplatform.fo.sample.db.SampleBoardMapper;
//import jobplatform.fo.sample.dto.SampleBoardSaveDto;
//import jobplatform.fo.sample.util.Header;
//import jobplatform.fo.sample.util.Search;
//
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//
//@RequiredArgsConstructor
//@RestController
//public class SampleBoardController {
//    private final SampleBoardService boardService;
//
//    //Http Get 방식으로 주소 가장 뒤 /board로 접근
//    @GetMapping("/board")
//    Header<List<SampleBoardEntity>> getBoardList(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size, Search search) {
//        return boardService.getBoardList(page, size, search);
//    }
//
//    //idx의 데이터 1개를 조회한다.
//    @GetMapping("/board/{idx}")
//    Header<SampleBoardEntity> getBoardOne(@PathVariable Long idx) {
//        boardService.updateHits(idx);
//        return boardService.getBoardOne(idx);
//    }
//
//    @PostMapping("/board")
//    Header<SampleBoardEntity> createBoard(@RequestBody SampleBoardSaveDto boardSaveDto) {
//        return boardService.insertBoard(boardSaveDto);
//    }
//
//    @PatchMapping("/board")
//    Header<SampleBoardEntity> updateBoard(@RequestBody SampleBoardSaveDto boardSaveDto) {
//        return boardService.updateBoard(boardSaveDto);
//    }
//
//    @DeleteMapping("/board/{idx}")
//    Header<String> deleteBoard(@PathVariable Long idx) {
//        return boardService.deleteBoard(idx);
//    }
//}
