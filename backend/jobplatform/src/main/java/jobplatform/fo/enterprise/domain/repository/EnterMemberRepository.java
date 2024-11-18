package jobplatform.fo.enterprise.domain.repository;

import java.util.Optional;

import jakarta.transaction.Transactional;
import jobplatform.fo.enterprise.domain.dto.EnterRegisterDTO;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import jobplatform.fo.enterprise.domain.entity.EnterMemberEntity;

@Repository
public interface EnterMemberRepository extends JpaRepository<EnterMemberEntity,Long> {
	
	
	
	@Query(value ="SELECT m.entrprsPswrd FROM EnterMemberEntity m WHERE m.entrprsId = :entrprsId")
	String findByEntrprsId(String entrprsId);


	boolean existsByEntrprsPicEml(String entrprsPicEml);

	boolean existsByEntrprsId(String entrprsId);

//	@Query(value ="SELECT m.entrprsSq FROM EnterMemberEntity m WHERE m.entrprsId = :entrprsId")
//	String findByEntrprsSameID(String entrprsId);


	@Query(value ="SELECT m.entrprsSq FROM EnterMemberEntity m WHERE m.entrprsId = :entrprsId")
	Long findByEntrprsIdSq(String entrprsId);
	
	 // 기업이름 가져오기
   
	 Optional<EnterMemberEntity> findByEntrprsSq(Long entrprsSq);


	EnterMemberEntity findByEntrprsPicEml(String entrprsPicEml);

	@Query("SELECT e.entrprsSq FROM EnterMemberEntity e WHERE e.entrprsId = :#{#dto.entrprsId} AND e.entrprsPicName = :#{#dto.entrprsPicName} AND e.entrprsPicEml = :#{#dto.entrprsPicEml}")
	String findByEntrprsPw(EnterRegisterDTO dto);

	@Modifying
	@Transactional
	@Query("UPDATE EnterMemberEntity e SET e.entrprsPswrd = :#{#enterRegisterDTO.entrprsPswrd} WHERE e.entrprsSq = :#{#enterRegisterDTO.entrprsSq}")
	int pswrdReset(EnterRegisterDTO enterRegisterDTO);

//	@Modifying
//	@Query("UPDATE EnterMemberEntity e SET e.entrprsPswrd = :encodePswrd WHERE e.entrprsId = :entrprsId")
//	void updatePassword(@Param("encodePswrd") String encodePswrd, @Param("entrprsId") String entrprsId);

}