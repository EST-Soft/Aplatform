package jobplatform.fo.enterprise.domain.repository;

import java.util.List;

import org.springframework.boot.autoconfigure.batch.BatchProperties.Job;
import org.springframework.data.jpa.repository.JpaRepository;

import jobplatform.fo.enterprise.domain.entity.JobEntity;

public interface JobRepository extends JpaRepository<JobEntity, Long> {

	
}
