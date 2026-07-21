import axios, { AxiosError } from "axios";

export class ApiError extends Error {
  constructor(
    message: string,
    public readonly status?: number,
  ) {
    super(message);
    this.name = "ApiError";
  }
}

const http = axios.create({
  baseURL: import.meta.env.VITE_API_BASE_URL || "/api",
  timeout: 5_000,
  headers: {
    Accept: "application/json",
  },
});

http.interceptors.response.use(
  (response) => response,
  (error: AxiosError) => {
    if (!error.response) {
      return Promise.reject(new ApiError("无法连接后端服务"));
    }

    const status = error.response.status;
    const message = status >= 500 ? "服务暂时不可用" : `请求失败（HTTP ${status}）`;
    return Promise.reject(new ApiError(message, status));
  },
);

export default http;

