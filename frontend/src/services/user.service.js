import axios from "axios";
import authHeader from "./auth-header";

const API_URL = "http://localhost:8080/api/v/0/"

class UserService {

    createPost(content) {
        const param = authHeader();
        console.log(param)
        return axios.post(API_URL + "posts",
            {
                "content" : content
            },
            {
                headers : {
                    "Authorization": authHeader(),
                    "Content-Type": "application/json"
                }
            });
    }
}

export default new UserService();