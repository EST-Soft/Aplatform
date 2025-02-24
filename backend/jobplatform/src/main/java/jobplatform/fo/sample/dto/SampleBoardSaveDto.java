package jobplatform.fo.sample.dto;

import java.sql.Timestamp;

import jobplatform.fo.sample.db.SampleBoardEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SampleBoardSaveDto {
    private Long idx;
    private String title;
    private String contents;
    private String createdBy;

    public SampleBoardEntity toEntity() {
        return SampleBoardEntity.builder()
                .idx(idx)
                .title(title)
                .contents(contents)
                .createdBy(createdBy)
                .build();
    }
}