import './App.css';

import React from 'react';
import {Route, Switch} from "react-router";

import AuthService from "./services/auth.service"

import Login from "./components/Login";
import Profile from "./components/Profile";
import Signup from "./components/Signup"

class App extends React.Component{

    constructor(props) {
        super(props);
        this.logOut = this.logOut.bind(this);

        this.state = {
            currentUser: undefined
        };
    }

    componentDidMount() {
        const user = AuthService.getCurrentUser();

        if(user) {
            this.setState({
                currentUser: user
            })
        }
    }

    logOut(){
        AuthService.logout();
    }

    render() {

        return(
            <div>
                <Switch>
                    <Route exact path="/login" component={Login} />
                    <Route exact path="/profile" component={Profile} />
                    <Route exact path="/signup" component={Signup} />
                </Switch>
            </div>
        )
    }
}

export default App;
