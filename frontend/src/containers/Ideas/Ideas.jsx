import React, {Component} from 'react';
import axios from "axios";
import IdeaForm from "../IdeaForm/IdeaForm";
import ListItemsOfApprovedIdeasExceptTheUser
    from "../../components/ListItemsOfApprovedIdeasExceptTheUser/ListItemsOfApprovedIdeasExceptTheUser";
import ListItemsOfApprovedIdeasOfTheUser
    from "../../components/ListItemsOfApprovedIdeasOfTheUser/ListItemsOfApprovedIdeasOfTheUser";

class Ideas extends Component {

    state = {
        approvedIdeasExceptTheUser: [],
        approvedIdeasOfTheUser: [],
        user: ''
    };

    componentDidMount() {
        document.title = 'Ideas';

        const user = JSON.parse(window.localStorage.getItem("user"));
        this.setState({user: user});

        if (localStorage.user && JSON.parse(window.localStorage.getItem("user")).role === "ROLE_USER") {
            this.getApprovedIdeasExceptTheUser();
            this.getApprovedIdeasOfTheUser();
        }
    };

    getApprovedIdeasExceptTheUser = () => {
        axios.get("/api/ideas/allApprovedIdeasExceptAccount")
            .then(response => {
                //console.log(response);
                this.setState({
                    approvedIdeasExceptTheUser: response.data
                });
            })
            .catch(error => {
                //console.warn(error.response);
            });
    };

    getApprovedIdeasOfTheUser = () => {
        axios.get("/api/ideas/allApprovedIdeasByAccount")
            .then(response => {
                //console.log(response);
                this.setState({
                    approvedIdeasOfTheUser: response.data
                });
            })
            .catch(error => {
                //console.warn(error.response);
            });
    };

    voteHandler = (id) => {
        axios.post("/api/ideas/" + id + "/addVote")
            .then(response => {
                //console.log(response);
            })
            .catch(error => {
                //console.warn(error.response);
            });
    };

    render() {
        let listItemsOfApprovedIdeasExceptTheUser = null;
        let listItemsOfApprovedIdeasOfTheUser = null;
        let ideaFormItem = null;

        if (localStorage.user && JSON.parse(window.localStorage.getItem("user")).role === "ROLE_USER") {

            listItemsOfApprovedIdeasExceptTheUser =
                this.state.approvedIdeasExceptTheUser.map((idea, index) => {
                    return (
                        <ListItemsOfApprovedIdeasExceptTheUser
                            key={index}
                            idea={idea}
                            voteHandler={this.voteHandler}
                        />
                    )
                });


            listItemsOfApprovedIdeasOfTheUser =
                this.state.approvedIdeasOfTheUser.map((idea, index) => {
                    return (
                        <ListItemsOfApprovedIdeasOfTheUser
                            key={index}
                            idea={idea}
                        />
                    )
                });

            ideaFormItem =
                <div>
                    <IdeaForm/>
                </div>
        }


        return (
            <div className="container">
                <h6>
                    Logged as : {localStorage.user ? this.state.user["userName"] : "anonymus"} "user".
                </h6>
                <br/>

                <h2>
                    Approved Ideas (except yours)
                </h2>
                <br/>
                <table className="table table-bordered">
                    <thead>
                    <tr className="table-dark">
                        <th scope="col">TimeStamp</th>
                        <th scope="col">Name</th>
                        <th scope="col">Description</th>
                        <th scope="col">Full Description</th>
                        <th scope="col">Vote</th>
                    </tr>
                    </thead>
                    <tbody>
                    {listItemsOfApprovedIdeasExceptTheUser}
                    </tbody>
                </table>
                <br/>

                <h2>
                    Your Approved Ideas
                </h2>
                <br/>
                <table className="table table-bordered">
                    <thead>
                    <tr className="table-dark">
                        <th scope="col">TimeStamp</th>
                        <th scope="col">Name</th>
                        <th scope="col">Description</th>
                        <th scope="col">Full Description</th>
                    </tr>
                    </thead>
                    <tbody>
                    {listItemsOfApprovedIdeasOfTheUser}
                    </tbody>
                </table>
                <br/>

                {ideaFormItem}
            </div>
        )
    }
}

export default Ideas;