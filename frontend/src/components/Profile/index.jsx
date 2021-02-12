import React from "react"

import AuthService from "../../services/auth.service"

class Profile extends React.Component {

    constructor(props) {
        super(props);

        this.state = {
            redirect: null,
            userReady: false,
            currentUser: { username: "" }
        };
    }

    componentDidMount() {
        const currentUser = AuthService.getCurrentUser();
        this.setState({ currentUser: currentUser, userReady: true })
        console.log(currentUser)
    }

    logout() {
        AuthService.logout()
    }

    render() {

        const {currentUser} = this.state;

        return (
            <div>
                {(this.state.userReady) ?
                    <div>
                        <a href="/login" onClick={this.logout}>
                        LogOut
                        </a>

                        <header>
                            <h3>
                                <strong>{currentUser.username}</strong>
                            </h3>
                        </header>
                        <p>
                            <strong>Token:</strong>{" "}
                            {currentUser.token.substring(0, 20)} ...{" "}
                            {currentUser.token.substr(currentUser.token.length - 20)}
                        </p>
                        <p>
                            <strong>Id:</strong>{" "}
                            {currentUser.id}
                        </p>
                        <p>
                            <strong>Email:</strong>{" "}
                            {currentUser.email}
                        </p>
                        <strong>Authorities:</strong>
                        <ul>
                            {currentUser.roles &&
                            currentUser.roles.map((role, index) => <li key={index}>{role}</li>)}
                        </ul>
                    </div>: null}
            </div>
        )
    }

}

export default Profile;