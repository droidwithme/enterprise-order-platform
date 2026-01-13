import axios from "axios";

const api = axios.create({
  baseURL: "http://user-service:8090", // confirm this is your user-service baseURL http://user-service:8090"
});

api.interceptors.request.use((config) => {
  const token = localStorage.getItem("token");
  if (token) {
    config.headers.Authorization = `Bearer ${token}`;
  }
  return config;
});

export default api;
