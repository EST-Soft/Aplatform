package jobplatform.fo.enterprise.service;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

import jobplatform.fo.enterprise.domain.dto.EnterLoginDTO;
import jobplatform.fo.enterprise.domain.dto.EnterRegisterDTO;

public interface EnterMemberService {

    public void insert(EnterRegisterDTO enterMemberDTO) throws IOException;

    
    public Long login(EnterLoginDTO enterLoginDTO);

    public boolean selectId(String id);

        String findByIdEml(EnterRegisterDTO enterRegisterDTO);

    String findByPswrd(EnterRegisterDTO enterRegisterDTO);

    int pswrdReset(EnterRegisterDTO enterRegisterDTO);

    }
