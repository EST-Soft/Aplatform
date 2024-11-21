package jobplatform.fo.user.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jakarta.servlet.http.HttpSession;
import jobplatform.fo.user.domain.vo.ApplyInfoDTO;

public interface MypageService {
//   Map<String, Object> getJoinCondition(int id);
   
   Integer getId(String id); // 회원순번
   
   List<ApplyInfoDTO> getApplyInfo(int mbrSq); // 지원정보
   
   List<ApplyInfoDTO> getSearchList(HashMap<String, Object> searchParam); // 검색시 지원젇보 리스트
   
   Integer getTotalApplyCount (Integer mbrSq); // 지원완료
   
   void delAppy(Integer apySq); // 지원취소

   Integer cancelApplyCnt(Integer mbrSq); //지원취소cnt
   
   Integer resumeReadCnt(Integer mbrSq); // 이력서 열람Cnt
   
   Integer resumeNotReadCnt(Integer mbrSq); // 이력서 미열람Cnt

  void saveJobView(Long jbpSq, HttpSession session);

}
