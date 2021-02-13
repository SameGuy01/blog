import React from "react"

import AuthService from "../../services/auth.service"

class Login extends React.Component{
    constructor(props) {
        super(props);
        this.state = {
            username: "",
            password: "",
            loading: false,
            message: ""
        };

        this.handleLogin = this.handleLogin.bind(this);
        this.onChangeUsername = this.onChangeUsername.bind(this);
        this.onChangePassword = this.onChangePassword.bind(this);
    }

    onChangeUsername(e) {
        this.setState({
            username: e.target.value
        });
    }

    onChangePassword(e) {
        this.setState({
            password: e.target.value
        });
    }

    handleLogin(e) {
        e.preventDefault();

        this.setState({
            message: "",
            loading: true
        });

        AuthService.login(this.state.username, this.state.password)
            .then( () => {
                this.props.history.push("/profile");
                window.location.reload();
                },
                error => {
                const resMessage =
                    (error.response &&
                        error.response.data &&
                        error.response.data.message) ||
                    error.message ||
                    error.toString();
                 this.setState({
                     loading: false,
                     message: resMessage
                 });
            });

    }

    render() {
        return (
            <div>
                <form
                    onSubmit={this.handleLogin}
                    ref = {c => {
                        this.form = c;
                    }}
                >
                    <div>
                        <label htmlFor="username">Username</label>
                        <input
                            type="text"
                            name="username"
                            value={this.state.username}
                            onChange={this.onChangeUsername}
                        />
                    </div>

                    <div>
                        <label htmlFor="password">Password</label>
                        <input
                            type="password"
                            name="password"
                            value={this.state.password}
                            onChange={this.onChangePassword}
                        />
                    </div>

                    <div>
                        <button
                            disabled={this.state.loading}>
                            {this.state.loading && (
                                <span>Loading</span>
                            )}
                            <span>Log in</span>
                        </button>
                    </div>
                </form>
            </div>
        )
    }
}

export default Login;