package com.community.fo.jpa.repository;

import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.community.fo.jpa.entity.MemberEntity;

public interface MemberRepository extends JpaRepository<MemberEntity, Integer> {

	MemberEntity findByMbrIdAndMbrPswrd(String mbrId, String mbrPswrd);

	@Query("SELECT m FROM MemberEntity m WHERE m.id = :userId")
	MemberEntity findByUserId(@Param("userId") String userId);
	
	MemberEntity findByMbrEmlAdrs(String mbrEmlAdrs);
}