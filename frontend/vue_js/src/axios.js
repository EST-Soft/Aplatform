import axios from "axios";

const baseUrl = "http://3.37.242.106:8081";
//const baseUrl = "http://localhost:80";

// axios 인스턴스 8081
const apiInstance = axios.create({
  baseURL: baseUrl,
});

// 공통 HTTP 메서드 정의
const api = {
  async $get(url, params) {
    try {
      const response = await apiInstance.get(url, params);
      return response.data;
    } catch (err) {
      console.error(err);
      throw err; // 에러를 다시 던져서 호출한 곳에서 처리할 수 있게 함
    }
  },
  async $post(url, data) {
    try {
      const response = await apiInstance.post(url, data);
      return response.data;
    } catch (err) {
      console.error(err);
      throw err;
    }
  },
  async $put(url, data) {
    try {
      const response = await apiInstance.put(url, data);
      return response.data;
    } catch (err) {
      console.error(err);
      throw err;
    }
  },
  async $patch(url, data) {
    try {
      const response = await apiInstance.patch(url, data);
      return response.data;
    } catch (err) {
      console.error(err);
      throw err;
    }
  },
  async $delete(url) {
    try {
      const response = await apiInstance.delete(url);
      return response.data;
    } catch (err) {
      console.error(err);
      throw err;
    }
  },
};

export { api, baseUrl };
