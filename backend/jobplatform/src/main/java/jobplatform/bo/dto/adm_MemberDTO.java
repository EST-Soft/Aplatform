package jobplatform.bo.dto;

import java.time.LocalDateTime;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class adm_MemberDTO {

    // 회원_순번
    private int mbrSq;
    // 성별_유형_코드
    private String gndrTypCd;
    // 회원_아이디
    private String mbrId;
    // 회원_이름
    private String mbrName;
    // 회원_비밀번호
    private String mbrPswrd;
    // 회원_휴대폰
    private String mbrMp;
    // 회원_생년월일
    private String mbrBd;
    // 회원_주소
    private String mbrAdrs;
    // 회원_이메일_주소
    private String mbrEmlAdrs;

    // 회원_이메일_수신_체크
    private String mbrEmlRcvYn;

    // 회원_개인정보_약관_체크
    private String mbrPrvcyTrmsYn;

    // 포지션_제안_수락_여부
    private String pstnPrpslAcptYn;

    // 회원_이미지_원본_파일명
    private String mbrImgOrgnlFn;

    // 회원_이미지_파일_url
    private String mbrImgFileUrl;

    // 등록_회원_순번
    private int insrtMbrSq;

    // 등록_일시
    private LocalDateTime insrtDtm;

    // 수정_회원_순번
    private Integer updtMbrSq;

    // 수정_일시
    private LocalDateTime updtDtm;
 
    // 삭제_여부 
    private String dltYn;

    // 사용_여부
    private String useYn;
}