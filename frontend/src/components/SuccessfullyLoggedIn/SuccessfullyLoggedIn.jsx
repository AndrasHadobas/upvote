import React, {Component} from 'react';
import './SuccessfullyLoggedIn.css';

class SuccessfullyLoggedIn extends Component {

    componentDidMount() {
        document.title = "UpVote Successfully Logged In Page"
    }

    render() {
        return (
            <div>
                <div className="success_title">
                    <p>You have successfully logged in!</p>
                </div>
                <div className="success_text">
                    <p>Now you can use the UpVote App!</p>
                    <p>Just click ideas on the NavBar. Enjoy your voting and have a nice day!</p>
                    <p>## RULES ##</p>
                    <p>#1  Don't forget, you can only vote once.</p>
                    <p>#2  You cannot vote on your own idea!</p>
                    <p>#3  If you share a new idea, you have to wait until the admin approves it.</p>
                </div>
            </div>
        )
    }
}

export default SuccessfullyLoggedIn;
