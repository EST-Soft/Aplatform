package jobplatform.fo.enterprise.domain.repository;

import java.util.List;

import org.apache.ibatis.annotations.Select;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import jobplatform.fo.enterprise.domain.entity.ApplyEntity;
import jobplatform.fo.enterprise.domain.vo.ApplyListVO;

public interface ApplyRepository extends JpaRepository<ApplyEntity, Long>{
    boolean existsByResumeRsmSqAndJobPostingJbpSq(Long rsmSq, Long jbpSq);

}


