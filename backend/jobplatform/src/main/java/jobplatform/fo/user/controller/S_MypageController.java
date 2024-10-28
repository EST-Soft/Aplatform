package jobplatform.fo.user.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jobplatform.fo.user.domain.vo.ApplyInfoDTO;
import jobplatform.fo.user.service.MypageService;

@RestController
@RequestMapping("/mypage")
public class S_MypageController {

   @Autowired
   MypageService mypageService;

   @SuppressWarnings("null")
   @PostMapping("/getApplyList")
   public ResponseEntity<?> getApplyList(@RequestBody Map<String, String> param)throws Exception {
      
      List<ApplyInfoDTO> applyInfo = null;
      Map<String, Object> applyInfoMap = new HashMap<>();
      
      int mbrSq = Integer.parseInt(param.get("mbrSq"));
      System.out.println(param.get("mbrSq"));
//
//      Integer mbrSq = mypageService.getId(param.get("id"));
//
//      if( mbrSq == null) {
//         return ResponseEntity.ok(List.of());
//      }else {
         
         applyInfo = mypageService.getApplyInfo(mbrSq);
         Integer totalApplyCount = mypageService.getTotalApplyCount(mbrSq);
         Integer cancelApplyCnt = mypageService.cancelApplyCnt(mbrSq);
         Integer resumeReadCnt = mypageService.resumeReadCnt(mbrSq);
         Integer resumeNotReadCnt = mypageService.resumeNotReadCnt(mbrSq);
         applyInfoMap.put("applyInfo", applyInfo);
         applyInfoMap.put("totalApplyCount", totalApplyCount);
         applyInfoMap.put("cancelApplyCnt", cancelApplyCnt);
         applyInfoMap.put("resumeReadCnt", resumeReadCnt);
         applyInfoMap.put("resumeNotReadCnt", resumeNotReadCnt);
         System.out.println(applyInfo);

//      }

      return ResponseEntity.ok(applyInfoMap);
   }
   
   
   @PostMapping("/delApply") // 지원취소
   public void delApply(@RequestBody Map<String, String> param) throws Exception{
      System.out.println(param.get("apySq"));
      Integer apySq = Integer.parseInt(param.get("apySq"));
      mypageService.delAppy(apySq);
      
   }
   
   @PostMapping("/getSearchList")
   public ResponseEntity<?> getSearchList(@RequestBody Map<String, String> param)throws Exception {
      // 검색 타입
      String searchType = param.get("serchType");
      // 검색할 내용
      String search = param.get("search");
      // 회원 순번
       Integer mbrSq = Integer.parseInt(param.get("mbrSq"));
      
      System.out.println(mbrSq);
      System.out.println(search);
      System.out.println(searchType);
      
      List<ApplyInfoDTO> applyInfo = null;
      Map<String, Object> applyInfoMap = new HashMap<>();
      
      // 로그인정보 입력안한 경우
          
          HashMap<String, Object> searchParam = new HashMap<>();
          
          searchParam.put("searchType", searchType);
          searchParam.put("search", search);
          searchParam.put("mbrSq", mbrSq);
          
           applyInfo = mypageService.getSearchList(searchParam);
           Integer totalApplyCount = mypageService.getTotalApplyCount(mbrSq);
           Integer cancelApplyCnt = mypageService.cancelApplyCnt(mbrSq);
           Integer resumeReadCnt = mypageService.resumeReadCnt(mbrSq);
           Integer resumeNotReadCnt = mypageService.resumeNotReadCnt(mbrSq);
           applyInfoMap.put("applyInfo", applyInfo);
           applyInfoMap.put("totalApplyCount", totalApplyCount);
           applyInfoMap.put("cancelApplyCnt", cancelApplyCnt);
           applyInfoMap.put("resumeReadCnt", resumeReadCnt);
           applyInfoMap.put("resumeNotReadCnt", resumeNotReadCnt);
           
           System.out.println(applyInfo);

       
      


      return ResponseEntity.ok(applyInfoMap);
   }
//   
//   
//   @GetMapping("/test")
//   public ResponseEntity<?> test(@RequestParam Map<String, Object> param)throws Exception {
//
//      System.out.println(param.get("mbrSq"));
//      return ResponseEntity.ok("통신");
//   }
   

}
