import React, {Component} from 'react';
import axios from 'axios';

class LoginForm extends Component {

    state = {
        loginForm: {
            userName: {
                value: '',
                isValid: true,
                message: ''
            },
            password: {
                value: '',
                isValid: true,
                message: ''
            }
        }
    };

    componentDidMount() {
        document.title = 'UpVote LogIn Page';
    };

    inputChangedHandler = (event) => {
        const target = event.target;
        const updatedLoginForm = {...this.state.loginForm};
        const updatedFormElement = {...updatedLoginForm[target.name]};

        updatedFormElement.value = target.value;
        updatedFormElement.isValid = true;
        updatedFormElement.message = '';

        updatedLoginForm[target.name] = updatedFormElement;
        this.setState({loginForm: updatedLoginForm});
    };

    validationHandler = (error) => {
        const updatedLoginForm = {...this.state.loginForm};

        for (let fieldError of error.fieldErrors) {
            updatedLoginForm[fieldError.field] = {
                value: this.state.loginForm[fieldError.field].value,
                isValid: false,
                message: fieldError.message
            }
        }

        this.setState({loginForm: updatedLoginForm});
    };

    formSubmitHandler = (event) => {
        event.preventDefault();

        axios.get('/api/accounts/login', {
            auth: {
                username: this.state.loginForm.userName.value,
                password: this.state.loginForm.password.value
            }
        })
            .then(response => {
                //console.log(response);
                localStorage.setItem('user', JSON.stringify(response.data));
                //console.log(localStorage.getItem('user'));
                this.props.history.push('/successLogin');
            })

            .catch(error => {
                //console.log(error.response);
                const errors = {
                    fieldErrors: [
                        {
                            field: 'userName',
                            message: 'Invalid username or password! Please type again!',
                        }
                    ]
                };
                this.validationHandler(errors);
            });
    };

    render() {
        return (
            <div className="container">

                <h2>Login</h2>
                <br/>

                <form onSubmit={this.formSubmitHandler}>

                    <div className="form-group has danger">
                        <label className="form-control-label" htmlFor="userName">UserName:</label>
                        <input
                            id="userName"
                            type="text"
                            name="userName"
                            onChange={this.inputChangedHandler}
                            value={this.state.loginForm.userName.value}
                            placeholder="{mandatory} Please, type your userName here!"
                            className={this.state.loginForm.userName.isValid ? "form-control" : "form-control is-invalid"}
                        />
                        <span className="text-danger">{this.state.loginForm.userName.message}</span>
                    </div>

                    <div className="form-group">
                        <label className="col-form-label" htmlFor="password">Password:</label>
                        <input
                            id="password"
                            type="password"
                            name="password"
                            onChange={this.inputChangedHandler}
                            value={this.state.loginForm.password.value}
                            placeholder="{mandatory} Please, type your password here!"
                            className="form-control"
                        />
                    </div>
                    <br/>

                    <div>
                        <button type="submit" className="btn btn-primary">Login</button>
                    </div>
                    <br/>

                </form>
            </div>
        )
    }
}

export default LoginForm;