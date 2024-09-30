import React ,{useState} from 'react';
import styles from './css/ChangePw.module.css';

function ChangePw() {

    // 해당 페이지는 비밀번호 변경을 위한 페이지. 240930, 화면만 구성되어있음
    const [newPassword, setNewPassword] = useState('');
    const [confirmPassword, setConfirmPassword] = useState('');
    //const navigate = useNavigate();

    const handleSubmit = (e) => {
        e.preventDefault();
        if (newPassword !== confirmPassword) {
            alert('비밀번호가 일치하지 않습니다.');
        return;
    }

        // 비밀번호 변경 API 요청보낼 위치
        // 성공적으로 비밀번호가 변경되면 다른 페이지로 이동.
        console.log('새 비밀번호:', newPassword);

        // 예시: 비밀번호 변경 성공 후 리다이렉션
        //navigate('/admin/AdminLogin'); // 원하는 경로로 변경

    };
    return(

    <div className={styles.ChangePwForm}>
        <div className={styles.formBox}>
            <h2 className={styles.h2Con}>새 비밀번호 설정</h2>

                <div className={styles.lineDiv} />
                    <br />
                    <br />
                <form onSubmit={handleSubmit}>
                    <div>
                        <input 
                            type="password" 
                            value={newPassword} 
                            onChange={(e) => setNewPassword(e.target.value)} 
                            placeholder="새 비밀번호 입력" 
                            required
                            className={styles.pwInput}
                        />
                    </div>
                
                        <br />
                
                    <div>
                
                    <input 
                        type="password" 
                        value={confirmPassword} 
                        onChange={(e) => setConfirmPassword(e.target.value)} 
                        placeholder="새 비밀번호 확인" 
                        required
                        className={styles.pwInputConfirm}
                    />
                        
                    </div>
                    
                        <br />
                    
                    <div className={styles.changeBtnDiv}>
                        <button type="submit"
                                className={styles.changeBtn}>
                        비밀번호 변경</button>
                    </div>
                </form>
        </div>
    </div>
    );
}

export default ChangePw;