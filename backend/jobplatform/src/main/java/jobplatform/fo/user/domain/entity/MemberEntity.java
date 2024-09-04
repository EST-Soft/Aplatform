package jobplatform.fo.user.domain.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;
import org.springframework.data.annotation.LastModifiedDate;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "P3_TBL_MEMBER_M")
@Data
@DynamicInsert
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberEntity {

    // 회원_순번
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "mbr_sq", nullable = false)
    private int mbrSq;

    // 성별_유형_코드
    @Column(name = "gndr_typ_code", nullable = false, columnDefinition = "varchar(20)")
    private String gndrCtgryCd;

    // 회원_아이디
    @Column(name = "mbr_id", nullable = false, columnDefinition = "varchar(20)")
    private String mbrId;

    // 회원_이름
    @Column(name = "mbr_name", nullable = false, columnDefinition = "varchar(20)")
    private String mbrName;

    // 회원_비밀번호
    @Column(name = "mbr_pswrd", nullable = false, columnDefinition = "varchar(20)")
    private String mbrPswrd;

    // 회원_휴대폰
    @Column(name = "mbr_mp", nullable = false, columnDefinition = "varchar(20)")
    private String mbrMp;

    // 회원_생년월일
    @Column(name = "mbr_bd", nullable = false, columnDefinition = "varchar(20)")
    private String mbrBd;

    // 회원_이메일_주소
    @Column(name = "mbr_eml_adrs", nullable = false, columnDefinition = "varchar(50)")
    private String mbrEmlAdrs;

//    // 회원_이메일_수신_체크
//    @Column(name = "mbr_eml_rcv_chck", nullable = false, columnDefinition = "char(1)")
//    private String mbrEmlRcvChck;
    // 회원_이메일_수신_체크
    @Column(name = "mbr_eml_rcv_yn", nullable = false, columnDefinition = "char(1)")
    private String mbrEmlRcvChck;

//    // 회원_개인정보_약관_체크
//    @Column(name = "mbr_prvcy_trms_chck", nullable = false, columnDefinition = "char(1)")
//    private String mbrPrvcyTrmsChck;
    // 회원_개인정보_약관_체크
    @Column(name = "mbr_prvcy_trms_yn", nullable = false, columnDefinition = "char(1)")
    private String mbrPrvcyTrmsChck;

    // 등록_회원_순번
    @Column(name = "insrt_mbr_sq", nullable = false)
    private int insrtMbrSq;

    // 등록_일시
    @CreationTimestamp // 해당 필드가 DB에 삽입될 때 현재 시간으로 자동 설정됨
    @Column(name = "insrt_dtm", updatable = false) // 해당 필드는 저장된 값 이후에 변경, 수정되지 않도록 보장
    private LocalDateTime insrtDtm;

    // 수정_회원_순번 --널값허용하지 않아 Integer로 수정
    @Column(name = "updt_mbr_sq", nullable = true)
    private Integer updtMbrSq;

    // 수정_일시
    @LastModifiedDate // 해당 필드가 DB에 저장되거나 업데이트될 때마다 현재 시간으로 업데이트
    @Column(name = "updt_dtm", nullable = true)
    private LocalDateTime updtDtm;

//    // 삭제_회원_순번 -- DB에는 해당칼럼 존재X 주석처리
//    @Column(name = "dlt_mbr_sq", nullable = true)
//    private int dltMbrSq;

//    // 삭제_일시
//    @LastModifiedDate // 해당 필드가 DB에 저장되거나 업데이트될 때마다 현재 시간으로 업데이트
//    @Column(name = "dlt_dtm", nullable = true)
//    private LocalDateTime dltDtm;
    // 삭제_일시--DB랑 구조가 달라서 변경함./
//    @LastModifiedDate // 해당 필드가 DB에 저장되거나 업데이트될 때마다 현재 시간으로 업데이트
    // @Column(name = "dlt_dtm", nullable = true)
    // private LocalDateTime dltDtm;

    // 삭제_여부
    @Column(name = "dlt_yn", nullable = false, columnDefinition = "char(1)")
    private char dltYn = 'N';

    // 사용_여부
    @Column(name = "use_yn", nullable = false, columnDefinition = "char(1)")
    private char useYn = 'Y';

//    // 제안 수락 여부--없는데 추가했음.
    @Column(name = "pstn_prpsl_accept_yn", nullable = false, columnDefinition = "char(1)")
    private String pstnPrpslAcceptYn;

}