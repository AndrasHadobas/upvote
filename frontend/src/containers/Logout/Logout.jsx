import React, { Component } from 'react'
import axios from "axios";

class Logout extends Component {

    componentDidMount() {

        axios.post('/logout')
            .then(response => {
                //console.log(response);
                localStorage.removeItem('user');
                this.props.history.push('/login')
            })
            .catch(error => {
                //console.warn(error.response);
            });
    };

    render() {
        return (
            <div className="container">
                <h2>You have successfully logged out!</h2>
            </div>
        )
    };
}

export default Logout;