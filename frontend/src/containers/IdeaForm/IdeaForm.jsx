import React, {Component} from 'react';
import axios from 'axios';
import Popup from "reactjs-popup";

class IdeaForm extends Component {

    state = {
        ideaForm: {
            accountUserName: {
                value: '',
                isValid: true,
                message: ''
            },
            name: {
                value: '',
                isValid: true,
                message: ''
            },
            description: {
                value: '',
                isValid: true,
                message: ''
            }
        },
        user: ''
    };

    componentDidMount() {
        const user = JSON.parse(window.localStorage.getItem("user"));
        const newFormData = {...this.state.ideaForm};
        newFormData.accountUserName = {
            value: user["userName"],
            isValid: true,
            message: ''
        };
        this.setState({
            ideaForm: newFormData,
            user: user
        });
    }

    inputChangedHandler = (event) => {
        const target = event.target;
        const updatedIdeaForm = {...this.state.ideaForm};
        const updatedFormElement = {...updatedIdeaForm[target.name]};

        updatedFormElement.value = target.value;
        updatedFormElement.isValid = true;
        updatedFormElement.message = '';

        updatedIdeaForm[target.name] = updatedFormElement;
        this.setState({ideaForm: updatedIdeaForm});
    };

    validationHandler = (error) => {
        const updatedIdeaForm = {...this.state.ideaForm};

        for (let fieldError of error.fieldErrors) {
            updatedIdeaForm[fieldError.field] = {
                value: this.state.ideaForm[fieldError.field].value,
                isValid: false,
                message: fieldError.message
            }
        }

        this.setState({ideaForm: updatedIdeaForm});
    };

    formSubmitHandler = (event) => {
        event.preventDefault();

        const formData = {};
        for (let formElementIdentifier in this.state.ideaForm) {
            formData[formElementIdentifier] = this.state.ideaForm[formElementIdentifier].value;
        }

        axios.post('/api/ideas', formData)
            .then(() => {
                this.eraseForm();
                this.openPopup();
            })
            .catch(error => {
                //console.warn(error);
                this.validationHandler(error.response.data);
            });
    };

    openPopup = () => {
        this.setState({openPopup: true});
    };

    closePopup = () => {
        this.setState(
            {openPopup: false});
    };

    eraseForm = () => {
        const newForm = {
            accountUserName: {
                value: this.state.user["userName"],
                isValid: true,
                message: ''
            },
            name: {
                value: '',
                isValid: true,
                message: ''
            },
            description: {
                value: '',
                isValid: true,
                message: ''
            }
        };
        this.setState({
            ideaForm: newForm
        })
    };

    render() {

        return (

            <div className="container">

                <h3>Share your own new Idea</h3>

                <form onSubmit={this.formSubmitHandler}>

                    <div className="form-group has danger">
                        <label className="form-control-label" htmlFor="accountUserName">UserName:</label>
                        <input
                            id="accountUserName"
                            type="text"
                            name="accountUserName"
                            onChange={this.inputChangedHandler}
                            value={localStorage.user ? this.state.user["userName"] : ""}
                            placeholder={this.state.ideaForm.accountUserName.value}
                            disabled={true}
                            style={{background: 'white'}}
                            className={this.state.ideaForm.accountUserName.isValid ? "form-control" : "form-control is-invalid"}
                        />
                        <span className="text-danger">{this.state.ideaForm.accountUserName.message}</span>
                    </div>

                    <div className="form-group has danger">
                        <label className="form-control-label" htmlFor="name">Name of your Idea:</label>
                        <input
                            id="name"
                            type="text"
                            name="name"
                            onChange={this.inputChangedHandler}
                            value={this.state.ideaForm.name.value}
                            placeholder="{mandatory} Please, type the name of your idea here!"
                            className={this.state.ideaForm.name.isValid ? "form-control" : "form-control is-invalid"}
                        />
                        <span className="text-danger">{this.state.ideaForm.name.message}</span>
                    </div>

                    <div className="form-group has danger">
                        <label className="form-control-label" htmlFor="description">Description of your Idea:</label>
                        <input
                            id="description"
                            type="text"
                            name="description"
                            onChange={this.inputChangedHandler}
                            value={this.state.ideaForm.description.value}
                            placeholder="{mandatory} Please, type the description of your idea here!"
                            className={this.state.ideaForm.description.isValid ? "form-control" : "form-control is-invalid"}
                        />
                        <span className="text-danger">{this.state.ideaForm.description.message}</span>
                    </div>

                    <div>
                        <button type="submit" className="btn btn-success">Save</button>
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
                                Thank you for your idea!<br/><br/>
                                Your idea was sent to the admin.<br/><br/>
                                You can see your own idea, if the admin approves it.<br/><br/>
                                Just click OK, and you can share a new idea.
                            </h5>
                            <br/>
                            <button className="btn btn-info" onClick={this.closePopup}>OK</button>
                        </div>
                    </Popup>

                </form>
            </div>
        )
    }
}

export default IdeaForm;