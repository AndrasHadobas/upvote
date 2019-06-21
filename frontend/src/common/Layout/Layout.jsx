import React, {Component} from 'react';
import NavBar from '../NavBar/NavBar'
import ErrorPage from "../../components/ErrorPage/ErrorPage";
import LoginForm from "../../containers/Login/LoginForm";
import Logout from "../../containers/Logout/Logout";
import RegistrationForm from "../../containers/Registration/RegistrationForm";
import IdeasForAdmin from "../../containers/IdeasForAdmin/IdeasForAdmin";
import Ideas from "../../containers/Ideas/Ideas";
import {Route, Switch} from "react-router-dom";
import SuccessfullyLoggedIn from "../../components/SuccessfullyLoggedIn/SuccessfullyLoggedIn";
import InfoPage from "../../components/InfoPage/InfoPage";

class Layout extends Component {

    render() {
        return (
            <div>
                <Route component={NavBar}/>
                <div className="container jumbotron">
                    <Switch>
                        <Route path="/" exact component={InfoPage}/>
                        <Route path="/registration" exact component={RegistrationForm}/>
                        <Route path="/login" exact component={LoginForm}/>
                        <Route path="/successLogin" exact component={SuccessfullyLoggedIn}/>
                        <Route path="/logout" exact component={Logout}/>
                        <Route path="/ideasForAdmin" exact component={IdeasForAdmin}/>
                        <Route path="/ideas" exact component={Ideas}/>
                        <Route path="*" exact={true} component={ErrorPage}/>
                    </Switch>
                </div>
            </div>
        )
    }
}

export default Layout;