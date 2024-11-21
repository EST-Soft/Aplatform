package jobplatform.fo.report.service;

import jobplatform.fo.report.entity.IssueTrackEntity;
import jobplatform.fo.report.mapper.IssueTrackMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class IssueTrackServiceImpl implements IssueTrackService {

    private final IssueTrackMapper issueTrackMapper;

    @Override
    public void saveIssue(IssueTrackEntity issue) {
        issueTrackMapper.insertIssue(issue);
    }
}
