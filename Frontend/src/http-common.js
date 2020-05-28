import axios from "axios";

export default axios.create({
//  baseURL : "http://k02b1051.p.ssafy.io/api/" // AWS
//  baseURL: "http://localhost:8080/api/" // 로컬
 baseURL: "http://172.30.1.39:8080/api/" // 로컬

});