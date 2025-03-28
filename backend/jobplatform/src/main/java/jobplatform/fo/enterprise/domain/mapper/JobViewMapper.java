package jobplatform.fo.enterprise.domain.mapper;

import java.time.LocalDateTime;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface JobViewMapper {
    // 본 적이 있는 공고인지 체크
    boolean existsJobViewByMbrSqAndJbpSq(@Param("mbrSq") Long mbrSq, @Param("jbpSq")Long jbpSq);
    // 공고 조회 등록
    void insertJobView(@Param("mbrSq")Long mbrSq, @Param("jbpSq")Long jbpSq, @Param("mbrId")String mbrId);
    // 봤던 공고 조회 시간 업데이트
    void updateJobViewDate(@Param("mbrSq")Long mbrSq, @Param("jbpSq")Long jbpSq, @Param("now")LocalDateTime now);
}
