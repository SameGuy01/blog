import React from "react"

import AXIOS from "../../services/auth.service"

class Registration extends React.Component {

    constructor(props) {
        super(props);

        this.handleRegister = this.handleRegister.bind(this)
        this.onChangeUsername = this.onChangeUsername.bind(this)
        this.onChangePassword = this.onChangePassword.bind(this)
        this.onChangeEmail = this.onChangeEmail.bind(this)
        this.onChangeFirstname = this.onChangeFirstname.bind(this)
        this.onChangeLastname = this.onChangeLastname.bind(this)

        this.state = {
            username: "",
            email: "",
            password: "",
            successful: "",
            message: "",
            lastname: "",
            firstname: ""
        }
    }


    onChangeUsername(e) {
        this.setState({
            username: e.target.value
        });
    }

    onChangeEmail(e) {
        this.setState({
            email: e.target.value
        });
    }

    onChangePassword(e) {
        this.setState({
            password: e.target.value
        });
    }

    onChangeFirstname(e) {
        this.setState({
            firstname: e.target.value
        });
    }

    onChangeLastname(e) {
        this.setState({
            lastname: e.target.value
        });
    }

    handleRegister(e) {
        e.preventDefault()

        this.setState({
            message: "",
            successful: false
        });

        AXIOS.register(this.state.username,
            this.state.email,
            this.state.password,
            this.state.firstname,
            this.state.lastname
        )
            .then(response => {
                this.setState({
                    message: response.data,
                    successful: true
                });

            }, error => {
                const resMessage = error.response.data
                this.setState({
                   message: resMessage,
                   successful: false
                });
            });
    }


    render() {
        return (
         <div>
            <strong>Sign up</strong>
             <div>
                 <form
                     onSubmit={this.handleRegister}
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
                         <label htmlFor="email">Email</label>
                         <input
                             type="text"
                             name="email"
                             value={this.state.email}
                             onChange={this.onChangeEmail}
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
                         <label htmlFor="firstname">Firstname</label>
                         <input
                            type="text"
                            name="firstname"
                            value={this.state.firstname}
                            onChange={this.onChangeFirstname}
                         />
                     </div>

                     <div>
                         <label htmlFor="lastname">Lastname</label>
                         <input
                             type="text"
                             name="lastname"
                             value={this.state.lastname}
                             onChange={this.onChangeLastname}
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

                     {this.state.message && (
                         <div>
                             <div
                                 className={
                                     this.state.successful
                                         ? "success"
                                         : "error"
                                 }
                                 role="alert"
                             >
                                 <strong>
                                     {this.state.message.message}
                                 </strong>
                             </div>
                         </div>
                     )}
                 </form>
             </div>
         </div>
        )
    }

}
export default Registration;