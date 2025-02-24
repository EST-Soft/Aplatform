import React, { useState } from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';

const FileUpload = () => {
    const [file, setFile] = useState(null);
    const [fileName, setFileName] = useState('');
    const [uploadedUrl, setUploadedUrl] = useState('');
    const navigate = useNavigate();

    const handleFileChange = (e) => {
        setFile(e.target.files[0]);
    };

    const handleFileNameChange = (e) => {
        setFileName(e.target.value);
    };

    const handleSubmit = async (e) => {
        e.preventDefault();

        if (!file || !fileName) {
            alert('선택된 파일이 없습니다.');
            return;
        }

        const formData = new FormData();
        formData.append('file', file);
        formData.append('fileName', fileName);

        try {
            const response = await axios.post('/s3fileUpload', {fileName, file}, {
                headers: {
                    'Content-Type': 'multipart/form-data'
                }
            });
            setUploadedUrl(response.data);
            alert('File uploaded successfully');
        } catch (error) {
            console.error('Error uploading file', error);
            alert('Error uploading file');
        }
    };

    const backToList = () => {
        navigate('/board');
    };

    return (
        <div>
            <h2>파일 업로드</h2>
            <form onSubmit={handleSubmit}>
                <div>
                    <label>File Name:</label>
                    <input
                        type="text"
                        value={fileName}
                        onChange={handleFileNameChange}
                    />
                </div>
                <div>
                    <label>Choose File:</label>
                    <input
                        type="file"
                        onChange={handleFileChange}
                    />
                </div>
                <button className='btn btn-primary' type="submit">업로드</button>
                <button className='btn btn-primary' onClick={backToList}>취소</button>
            </form>
            {uploadedUrl && (
                <div>
                    <h3>Uploaded File URL:</h3>
                    <a href={uploadedUrl} target="_blank" rel="noopener noreferrer">{uploadedUrl}</a>
                </div>
            )}

        </div>
    );
};

export default FileUpload;