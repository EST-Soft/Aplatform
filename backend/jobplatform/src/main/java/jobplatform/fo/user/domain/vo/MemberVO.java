package jobplatform.fo.user.domain.vo;

import lombok.Data;

@Data
public class MemberVO {
    private String mbrId;
    private String currentPassword;
    private String newPassword;
}
