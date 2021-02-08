import './App.css';

import React from 'react'
import axios from "axios";

class App extends React.Component{

    state = {
        data: []
    }

    componentDidMount() {
        axios.get('http://localhost:8080/api/v/0/categories')
            .then(response =>{
                const data = response.data
                this.setState({data})
                console.log(data)
            })
    }

    render()
    {
        return (
            <div className="App">
                <h1>Categories</h1>
                <div>
                    {this.state.data.map(category => (
                        <div key={category.id}>
                            <p>{category.title}</p>
                            <div>
                                {category.postList.map( post => (
                                    <div key={post.id}>
                                        <p>Id: {post.id}</p>
                                        <p>Content : {post.content}</p>
                                        <p>Author : {post.user.username}</p>
                                    </div>
                                ))}
                            </div>
                        </div>
                    ))}
                </div>
            </div>
        );
    }
}

export default App;
