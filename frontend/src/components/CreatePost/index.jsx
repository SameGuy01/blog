import React from "react"

import UserService from "../../services/user.service"

class CreatePost extends React.Component{
    constructor(props) {
        super(props);
        this.state = {
            content: "",
            successful: false,
            message: ""
        }
        this.handleCreatePost = this.handleCreatePost.bind(this);
        this.onChangeContent = this.onChangeContent.bind(this);
    }

    onChangeContent(e) {
        this.setState({
            content: e.target.value
        });
    }

    handleCreatePost(e) {
        e.preventDefault();

        this.setState({
            successful: false,
            message: ""
        });

        UserService.createPost(this.state.content)
            .then(response => {
                console.log(response)
                this.setState({
                    message: response.data,
                    successful: true
                })
            }, error => {
                console.log(error.response)
                this.setState({
                    message: error,
                    successful: false
                });
            });
    }

    render() {
        return(
            <div>
                <strong>Create post</strong>
                <form
                    onSubmit={this.handleCreatePost}
                    ref = {c => {
                        this.form = c;
                    }}
                >
                    <div>
                        <label htmlFor="content">Content</label>
                        <input
                            type="text"
                            name="content"
                            value={this.state.content}
                            onChange={this.onChangeContent}
                        />
                    </div>

                    <button>
                        Create post.
                    </button>

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
                                    {this.state.message}
                                </strong>
                            </div>
                        </div>
                    )}

                </form>
            </div>
        )
    }
}

export default CreatePost;