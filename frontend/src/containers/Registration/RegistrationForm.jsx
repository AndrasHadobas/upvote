import React, {Component} from 'react';
import axios from 'axios';
import Popup from "reactjs-popup";

class RegistrationForm extends Component {

    state = {
        registrationForm: {
            firstName: {
                value: '',
                isValid: true,
                message: ''
            },
            lastName: {
                value: '',
                isValid: true,
                message: ''
            },
            userName: {
                value: '',
                isValid: true,
                message: ''
            },
            email: {
                value: '',
                isValid: true,
                message: ''
            },
            password: {
                value: '',
                isValid: true,
                message: ''
            },
            retypedPassword: {
                value: '',
                isValid: true,
                message: ''
            }
        },
        openPopup: false
    };

    componentDidMount() {
        document.title = 'UpVote Registration Page';
    };

    inputChangedHandler = (event) => {
        const target = event.target;
        const updatedRegistrationForm = {...this.state.registrationForm};
        const updatedFormElement = {...updatedRegistrationForm[target.name]};

        updatedFormElement.value = target.value;
        updatedFormElement.isValid = true;
        updatedFormElement.message = '';

        updatedRegistrationForm[target.name] = updatedFormElement;
        this.setState({registrationForm: updatedRegistrationForm});
    };


    validationHandler = (error) => {
        const updatedRegistrationForm = {...this.state.registrationForm};

        for (let fieldError of error.fieldErrors) {
            updatedRegistrationForm[fieldError.field] = {
                value: this.state.registrationForm[fieldError.field].value,
                isValid: false,
                message: fieldError.message
            }
        }

        this.setState({registrationForm: updatedRegistrationForm});
    };


    formSubmitHandler = (event) => {
        event.preventDefault();

        const formData = {};
        for (let formElementID in this.state.registrationForm) {
            if (this.state.registrationForm.hasOwnProperty(formElementID)) {
                formData[formElementID] = this.state.registrationForm[formElementID].value;
            }
        }

        axios.post('/api/accounts', formData)
            .then(response => {
                this.openPopup();
            })
            .catch(error => {
                this.validationHandler(error.response.data);
            });
    };

    openPopup = () => {
        this.setState({openPopup: true});
    };

    closePopup = () => {
        this.setState(
            {openPopup: false});
        this.props.history.push('/login');
    };

    render() {
        return (
            <div className="container">

                <h2>Registration</h2>
                <br/>

                <form onSubmit={this.formSubmitHandler}>

                    <div className="form-group has danger">
                        <label className="form-control-label" htmlFor="firstName">FirstName:</label>
                        <input
                            id="firstName"
                            type="text"
                            name="firstName"
                            onChange={this.inputChangedHandler}
                            value={this.state.registrationForm.firstName.value}
                            placeholder="{mandatory} Please, type your FirstName here!"
                            className={this.state.registrationForm.firstName.isValid ? "form-control" : "form-control is-invalid"}
                        />
                        <span className="text-danger">{this.state.registrationForm.firstName.message}</span>
                    </div>

                    <div className="form-group has danger">
                        <label className="form-control-label" htmlFor="lastName">LastName:</label>
                        <input
                            id="lastName"
                            type="text"
                            name="lastName"
                            onChange={this.inputChangedHandler}
                            value={this.state.registrationForm.lastName.value}
                            placeholder="{mandatory} Please, type your LastName here!"
                            className={this.state.registrationForm.lastName.isValid ? "form-control" : "form-control is-invalid"}
                        />
                        <span className="text-danger">{this.state.registrationForm.lastName.message}</span>
                    </div>

                    <div className="form-group has danger">
                        <label className="form-control-label" htmlFor="userName">UserName:</label>
                        <input
                            id="userName"
                            type="text"
                            name="userName"
                            onChange={this.inputChangedHandler}
                            value={this.state.registrationForm.userName.value}
                            placeholder="{mandatory} Please, type your userName here!"
                            className={this.state.registrationForm.userName.isValid ? "form-control" : "form-control is-invalid"}
                        />
                        <span className="text-danger">{this.state.registrationForm.userName.message}</span>
                    </div>

                    <div className="form-group has danger">
                        <label className="form-control-label" htmlFor="email">e-Mail:</label>
                        <input
                            id="email"
                            type="email"
                            name="email"
                            onChange={this.inputChangedHandler}
                            value={this.state.registrationForm.email.value}
                            placeholder="{mandatory} Please, type your email here!"
                            className={this.state.registrationForm.email.isValid ? "form-control" : "form-control is-invalid"}
                        />
                        <span className="text-danger">{this.state.registrationForm.email.message}</span>
                    </div>

                    <div className="form-group has danger">
                        <label className="form-control-label" htmlFor="password">Password:</label>
                        <input
                            id="password"
                            type="password"
                            name="password"
                            onChange={this.inputChangedHandler}
                            value={this.state.registrationForm.password.value}
                            placeholder="{mandatory} Please, type your new password here!"
                            className={this.state.registrationForm.password.isValid ? "form-control" : "form-control is-invalid"}
                        />
                        <span className="text-danger">{this.state.registrationForm.password.message}</span>
                    </div>

                    <div className="form-group">
                        <label className="form-control-label" htmlFor="retypedPassword">retypedPassword:</label>
                        <input
                            id="retypedPassword"
                            type="password"
                            name="retypedPassword"
                            onChange={this.inputChangedHandler}
                            value={this.state.registrationForm.retypedPassword.value}
                            placeholder="{mandatory} Please, retype your password!"
                            className="form-control"
                        />
                    </div>
                    <br/>

                    <div>
                        <button type="submit" id="registrationButton" className="btn btn-primary">Registration</button>
                    </div>
                    <br/>

                    <Popup
                        open={this.state.openPopup}
                        closeOnDocumentClick
                        onClose={this.closePopup}
                        modalposition="right center"
                        className="modal">
                        <div>
                            <h5 style={{color: 'black'}}>
                                Thank you for your registration!<br/><br/>
                                You have successfully registered.<br/><br/>
                                Just click OK, and you can login immediately.
                            </h5>
                            <br/>
                            <button id="registrationOk" className="btn btn-info" onClick={this.closePopup}>OK</button>
                        </div>
                    </Popup>

                    <div>
                        <small>
                            PASSWORD RULES:<br/>
                            # start-of-letter<br/>
                            # a digit must occur at least once<br/>
                            # a lower case letter must occur at least once<br/>
                            # an upper case letter must occur at least once<br/>
                            # a special character (@#$%^&+=) must occur at least once<br/>
                            # no whitespace allowed in the entire password<br/>
                            # anything, at least eight places though<br/>
                            # end-of-letter<br/>
                        </small>
                    </div>

                </form>
            </div>
        )
    }
}

export default RegistrationForm;