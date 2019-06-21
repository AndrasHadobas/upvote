import React, {Component} from 'react';
import {Link} from 'react-router-dom';
import './NavBar.css'

class NavBar extends Component {

    render() {

        let logInLogOutMenuItem = (
            <div>
                <ul className="navbar-nav ml-auto">
                    <li className="nav-item">
                        <Link className="nav-link menu" to="/registration">Registration</Link>
                    </li>
                    <li className="nav-item">
                        <Link className="nav-link menu" to="/login">Login</Link>
                    </li>
                </ul>
            </div>
        );
        if (localStorage.user) {
            logInLogOutMenuItem = (
                <div>
                    <ul className="navbar-nav ml-auto">
                        <li className="nav-item">
                            <Link className="nav-link menu" to="/logout">Logout</Link>
                        </li>
                    </ul>
                </div>
            )
        }

        let ideasForAdminItem = null;
        if (localStorage.user && JSON.parse(localStorage.getItem("user")).role === "ROLE_ADMIN") {
            ideasForAdminItem = (
                <div>
                    <ul className="navbar-nav ml-auto">
                        <li className="nav-item">
                            <Link className="nav-link menu" to="/ideasForAdmin">Ideas (site:Admin)</Link>
                        </li>
                    </ul>
                </div>
            )
        }

        let ideasForVoterItem = null;
        if (localStorage.user && JSON.parse(localStorage.getItem("user")).role === "ROLE_USER") {
            ideasForVoterItem = (
                <div>
                    <ul className="navbar-nav ml-auto">
                        <li className="nav-item">
                            <Link className="nav-link menu" to="/ideas">Ideas</Link>
                        </li>
                    </ul>
                </div>
            )
        }

        return (
            <div>
                <nav className="navbar navbar-expand-lg navbar-dark bg-primary">
                    <div className="container">
                        <a className="navbar-brand upVote" href="/">UpVote App</a>
                        <button type="button"
                                className="navbar-toggler"
                                data-toggle="collapse"
                                data-target="#myNavbar"
                                aria-expanded="false"
                                aria-controls="myNavbar"
                                aria-label="Toggle navigation">
                            <span className="navbar-toggler-icon"/>
                        </button>
                        <div id="myNavbar" className="navbar-collapse collapse">
                            <ul className="navbar-nav mr-auto">
                                {ideasForAdminItem}
                                {ideasForVoterItem}
                                {logInLogOutMenuItem}
                            </ul>
                        </div>
                    </div>
                </nav>
            </div>
        )
    };
}

export default NavBar;