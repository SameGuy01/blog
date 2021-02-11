import axios from "axios";

const API_URL = "http://localhost:8080/api/v/0/auth/";

class AuthService {

    login (username, password) {
        return axios
            .post(API_URL + "login", {
                username,
                password
            })
            .then(response => {
                if (response.data.token) {
                    localStorage.setItem("user", JSON.stringify(response.data));
                    console.log(localStorage)
                }
                console.log(123)
                return response.data;
            });
    }

    logout() {
        localStorage.removeItem("user");
    }

    register (username, email, password, firstname, lastname) {
        return axios.post(API_URL + "signup", {
            username,
            email,
            password,
            firstname,
            lastname
        });
    }

    getCurrentUser() {
        return JSON.parse(localStorage.getItem('user'));
    }
}

export default new AuthService();