package jobplatform.fo.enterprise.domain.entity;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

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
@Table(name = "p6_tbl_enterprise_member")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EnterpriseMemberEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "entprs_mbr_sq", nullable = false)
    private Long entprsMbrSq;

    @Column(name = "entprs_sq", nullable = false)
    private Long entprsSq;

    @Column(name = "mbr_sq", nullable = false)
    private Long mbrSq;

    @Column(name = "hired_at", nullable = false)
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate hiredAt;

    @Column(name = "exit_at", nullable = true)
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate exitAt;


}
