import axios from "axios";

const api = axios.create({
    baseURL: "http://3.37.242.106:8081",
});

// // 프로젝트 등록
// function prInsert(projectDomain) {return api.post("/project", projectDomain)}

function prSelect(no) {
    return api.get(`/project/${no}`)}

export { prSelect };