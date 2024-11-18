package jobplatform.fo.user.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpSession;
import jobplatform.fo.user.domain.mapper.MypageMapper;
import jobplatform.fo.user.domain.vo.ApplyInfoDTO;

@Service
public class MypageServieImpl implements MypageService{
   
   @Autowired
   private MypageMapper mypagemapper;
   
//   public Map<String, Object> getJoinCondition(int id) {
//      return mypagemapper.getJoinCondition(id);
//   }

   @Override
   public Integer getId(String id) {
      // TODO Auto-generated method stub
      return mypagemapper.getid(id);
   }

   @Override
   public List<ApplyInfoDTO> getApplyInfo(int mbrSq) {
      // TODO Auto-generated method stub
      return mypagemapper.getApplyInfo(mbrSq);
   }

   @Override
   public Integer getTotalApplyCount(Integer mbrSq) {
      // TODO Auto-generated method stub
      return mypagemapper.getTotalApplyCount(mbrSq);
   }

   @Override //지원취소
   public void delAppy(Integer apySq) {
      // TODO Auto-generated method stub
      mypagemapper.delAppy(apySq);
   }

   @Override //지원취소Cnt
   public Integer cancelApplyCnt(Integer mbrSq) {
      // TODO Auto-generated method stub
      return mypagemapper.cancelApplyCnt(mbrSq);
   }

   @Override // 이력서열람Cnt
   public Integer resumeReadCnt(Integer mbrSq) {
      // TODO Auto-generated method stub
      return mypagemapper.resumeReadCnt(mbrSq);
   }

   @Override // 이력서미열람Cnt
   public Integer resumeNotReadCnt(Integer mbrSq) {
      // TODO Auto-generated method stub
      return mypagemapper.resumeNotReadCnt(mbrSq);
   }

   @Override // 검색시 지원 내역
   public List<ApplyInfoDTO> getSearchList(HashMap<String, Object> searchParam) {
      // TODO Auto-generated method stub
      return mypagemapper.getSearchList(searchParam);
   }

   @Override
   public void saveJobView(Long jbpSq, HttpSession session) {
      // 최근 본 공고를 세션에서 가져옵니다
        List<Long> recentJobViews = (List<Long>) session.getAttribute("recentJobViews");

        if (recentJobViews == null) {
            recentJobViews = new ArrayList<>();
        }

        // 이미 세션에 해당 공고가 없으면 추가
        if (!recentJobViews.contains(jbpSq)) {
            recentJobViews.add(0, jbpSq);  // 최신 공고를 맨 앞에 추가
        }

        // 세션에 최근 본 공고 목록 저장
        session.setAttribute("recentJobViews", recentJobViews);
    }
   }

