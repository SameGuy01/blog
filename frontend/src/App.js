import './App.css';

import React from 'react';

import AuthService from "./services/auth.service"

import Login from "./components/Login";
import {Route, Switch} from "react-router";
import Profile from "./components/Profile";


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
                </Switch>
            </div>
        )
    }
}

export default App;
