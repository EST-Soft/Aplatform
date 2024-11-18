package jobplatform.fo.enterprise.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import jobplatform.fo.enterprise.domain.dto.JobRecommendationDTO;
@Service 
public class JobRecommendationImpl implements JobRecommendationService {
  @Autowired
private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

public List<JobRecommendationDTO> recommendJobs(long rsmSq, List<String> selectedSkills) {
    String sql = "SELECT j.jbp_sq, j.jbp_ttl, COUNT(r.skl_code_sq) AS match_count "
               + "FROM P2_TBL_JOB_POSTING_M j "
               + "JOIN P2_TBL_SKILL_CODE_RESUME_R r ON j.skl_code_sq = r.skl_code_sq "
               + "WHERE r.rsm_sq = :rsm_sq "
               + "AND r.skl_code_sq IN (:selected_skill_codes) "
               + "GROUP BY j.jbp_sq, j.jbp_ttl "
               + "HAVING COUNT(r.skl_code_sq) > 0 "
               + "ORDER BY match_count DESC "
               + "LIMIT 3";

    // MapSqlParameterSource로 파라미터 바인딩
    MapSqlParameterSource params = new MapSqlParameterSource();
    params.addValue("rsm_sq", rsmSq);  // 이력서 순번
    params.addValue("selected_skill_codes", selectedSkills);  // 선택된 기술 코드 리스트

    // 쿼리 실행 및 결과 매핑
    List<JobRecommendationDTO> recommendations = namedParameterJdbcTemplate.query(sql, params, new RowMapper<JobRecommendationDTO>() {
        @Override
        public JobRecommendationDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
            JobRecommendationDTO jobRecommendation = new JobRecommendationDTO();
            jobRecommendation.setJbpSq(rs.getLong("jbp_sq"));
            jobRecommendation.setJbpTtl(rs.getString("posting_title"));
            jobRecommendation.setMatchSkills(rs.getInt("match_count"));
            return jobRecommendation;
        }
    });

    return recommendations;
}
}
