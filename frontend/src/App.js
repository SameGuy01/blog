import './App.css';

import React from 'react';

import NavigationBar from './components/NavigationBar'

class App extends React.Component{

    state = {
        data: []
    }

    render() {
        return(
            <div>
                <NavigationBar/>
            </div>
        )
    }
}

export default App;
