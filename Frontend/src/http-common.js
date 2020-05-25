import axios from "axios";

export default axios.create({
//  baseURL : "http://i02b206.p.ssafy.io/dc/" // AWS
 baseURL: "http://localhost:8080/api/" // 로컬
//  baseURL: "http://172.30.1.52:8080/api/" // 로컬
});