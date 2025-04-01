package jobplatform.fo.enterprise.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import jobplatform.fo.enterprise.domain.entity.EnterpriseMemberEntity;



@Repository
public interface EnterpriseMemberRepository extends JpaRepository<EnterpriseMemberEntity,Long> {

    List<EnterpriseMemberEntity> findByEntprsSq(Long entprsSq);
}
