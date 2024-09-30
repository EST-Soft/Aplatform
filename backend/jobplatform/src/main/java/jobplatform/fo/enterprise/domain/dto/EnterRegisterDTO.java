package jobplatform.fo.enterprise.domain.dto;


import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@NoArgsConstructor
@Data
public class EnterRegisterDTO {
    private int entrprsSq;

    private String entrprsId;

    private String entrprsPswrd;

    private String cmnRgtrrtnNmbr;// 사업자등록번호

    private String entrprsPicName; // 기업담당자_이름

    private String entrprsPicEml; // 기업담당자_이메일_주소

    private String entrprsName;

    private String entrprsPicMp; // 기업담당자_휴대폰

    private String entrprsEmlRcvYn; // 기업담당자_이메일_수신_여부

    private String entrprsPrvcyTrmsYn; // 회원 개인정보 약관 동의 여부

    private String gndrCtryCd;
}
