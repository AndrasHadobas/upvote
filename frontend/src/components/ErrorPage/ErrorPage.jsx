import React, {Component} from 'react';
import './ErrorPage.css';

class ErrorPage extends Component {

    componentDidMount() {
        document.title = "UpVote Error Page"
    }

    render() {
        return (
            <div>
                <div className="errorPage_number">
                    404
                </div>
                <div className="errorPage_title">
                    <p>Ooops, something went wrong!</p>
                </div>
                <div className="errorPage_text">
                    <p>You can go back by using the NavBar.</p>
                </div>
            </div>
        )
    }
}

export default ErrorPage;
