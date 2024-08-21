package jobplatform.fo.user.domain.mapper;

import java.util.HashMap;
import java.util.List;

import jobplatform.fo.user.domain.entity.MemberEntity;
import org.apache.ibatis.annotations.Mapper;

import jobplatform.fo.user.domain.vo.ApplyInfoDTO;

@Mapper
public interface MypageMapper {
   
//   Map<String, Object> getJoinCondition(int Id);
   
   Integer getid(String id);
   
   List<ApplyInfoDTO> getApplyInfo(int mbrSq); // 지원내역
   
   List<ApplyInfoDTO> getSearchList(HashMap<String, Object> searchParam); // 검색시 지원젇보 리스트

   Integer getTotalApplyCount(Integer mbrSq); // 지원완료
   
   void delAppy(Integer apySq); // 지원취소
   
   Integer cancelApplyCnt(Integer mbrSq); // 지원취소Cnt
   
   Integer resumeReadCnt(Integer mbrSq); // 이력서 열람Cnt
   
   Integer resumeNotReadCnt(Integer mbrSq); // 이력서 미열람Cnt
   MemberEntity findByMbrSq(int mbrSq);

   
}