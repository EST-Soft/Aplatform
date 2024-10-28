package jobplatform.fo.enterprise.domain.dto;

import java.util.List;

import jobplatform.fo.user.domain.vo.EducationDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResumeDataResponseDto {

    // 이력서 인적사항 및 이미지
    private ResumeDataDTO resumeDataDTO;
    // 학력 정보 리스트
    private List<EducationDto> educationDtoList;
    // 경력 정보 리스트
    private List<CareerDto> careerDtoList;
    // 스킬 정보 리스트
    private List<SkillCodeSDto> skilsDataDtoList;
    // 자격/면허증 리스트
    private List<ResumeCertificateSDto> certificateDtoList;
    // 자기소개서 리스트
    private List<SelfIntroductionsDataDTO> selfIntroductionDtoList;
    // 첨부파일 리스트
    private List<AttachmentDto> attachmentDtoList;

} // ResumeDataResponseDto
