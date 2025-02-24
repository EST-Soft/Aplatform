package jobplatform.fo.enterprise.domain.dto;

import lombok.Data;

@Data
public class EnterInfoDTO {
	
	private Long entrprsSq; // 기업 순번
	private String entrprsId; // 기업 아이디
	private String entrprsName; // 기업명
	private String entrprsHp; // 기업 홈페이지
	private String entrprsAdrs; // 기업주소
	private String entrprsPicName; // 담당자 이름
	private String entrprsPicMp; // 담당자 전화번호
	private String entrprsPicEml; // 담당자 이메일 주소
	private String entrprsZipCode; // 기업 우편번호
}
