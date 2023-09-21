import axios from "axios";
import { BACKEND_URL } from "./constants";

const axiosInstance = axios.create({
    baseURL: BACKEND_URL,
    timeout: 30000,
    withCredentials: true
  });

export default axiosInstance;