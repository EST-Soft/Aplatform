package jobplatform.fo.enterprise.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import jobplatform.fo.enterprise.domain.entity.AttachmentEntity;

public interface AttachmentRepository extends JpaRepository<AttachmentEntity, Long> {
    
} // AttachmentRepository
