package jobplatform.fo.sample.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import jobplatform.fo.sample.db.SampleBoardEntity;
import jobplatform.fo.sample.db.SampleBoardMapper;
import jobplatform.fo.sample.dto.SampleBoardSaveDto;
import jobplatform.fo.sample.util.Header;
import jobplatform.fo.sample.util.Pagination;
import jobplatform.fo.sample.util.Search;

import java.util.HashMap;
import java.util.List;

@RequiredArgsConstructor
@Service
public class SampleBoardService {
    private final SampleBoardMapper boardMapper;

    Header<List<SampleBoardEntity>> getBoardList(int page, int size, Search search) {
        HashMap<String, Object> paramMap = new HashMap<>();

        if (page <= 1) {    //페이지가 1 이하로 입력되면 0으로 고정,
            paramMap.put("page", 0);
        } else {            //페이지가 2 이상
            paramMap.put("page", (page - 1) * size);
        }
        paramMap.put("size", size);
        paramMap.put("sk", search.getSk());
        paramMap.put("sv", search.getSv());

        List<SampleBoardEntity> boardList = boardMapper.getBoardList(paramMap);
        Pagination pagination = new Pagination(
                boardMapper.getBoardTotalCount(paramMap),
                page,
                size,
                10
        );

        return Header.OK(boardList, pagination);
    }

    Header<SampleBoardEntity> getBoardOne(Long idx) {
        return Header.OK(boardMapper.getBoardOne(idx));
    }

    Header<SampleBoardEntity> insertBoard(SampleBoardSaveDto boardSaveDto) {
        SampleBoardEntity entity = boardSaveDto.toEntity();
        if (boardMapper.insertBoard(entity) > 0) {
            return Header.OK(entity);
        } else {
            return Header.ERROR("ERROR");
        }
    }

    Header<SampleBoardEntity> updateBoard(SampleBoardSaveDto boardSaveDto) {
        SampleBoardEntity entity = boardSaveDto.toEntity();
        if (boardMapper.updateBoard(entity) > 0) {
            return Header.OK(entity);
        } else {
            return Header.ERROR("ERROR");
        }
    }
    
    public void updateHits(Long idx){
        boardMapper.hitsBoard(idx);
    }

    Header<String> deleteBoard(Long idx) {
        if (boardMapper.deleteBoard(idx) > 0) {
            return Header.OK();
        } else {
            return Header.ERROR("ERROR");
        }
    }
}