import './App.css';

import React from 'react'

class Welcome extends React.Component{
  render(){
    return(
      <div>
          <h1>Hello, {this.props.someProps} !</h1>
      </div>
    )
  }
}

class App extends React.Component{
    render()
    {
        return (
            <div className="App">
                <Welcome someProps="Blog"/>
            </div>
        );
    }
}

export default App;
