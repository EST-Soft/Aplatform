package jobplatform.fo.enterprise.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JobPostingSearchDataDTO {


        private long entrprsSq;
        private String sort;
        private int pageNo;


}
