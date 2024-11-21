package jobplatform.fo.report.mapper;

import jobplatform.fo.report.entity.IssueTrackEntity;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface IssueTrackMapper {
    void insertIssue(IssueTrackEntity issue); // 신고 등록
}
