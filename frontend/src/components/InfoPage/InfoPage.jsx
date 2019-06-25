import React, {Component} from 'react';
import './InfoPage.css';

class InfoPage extends Component {

    componentDidMount() {
        document.title = "UpVote Info Page"
    };

    render() {
        return (
            <div>
                <div className="info_title">
                    <p>Info Page!</p>
                </div>
                <div className="info_text">
                    <p>How to use UpVote App?</p>
                    <p>First you have to register and after you can log in.</p>
                    <p>## RULES ##</p>
                    <p>#1  Don't forget, you can only vote once.</p>
                    <p>#2  You cannot vote on your own idea!</p>
                    <p>#3  If you share a new idea, you have to wait until the admin approves it.</p>
                </div>
            </div>
        )
    }
}

export default InfoPage;