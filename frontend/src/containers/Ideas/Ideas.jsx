import React, {Component} from 'react';
import axios from "axios";
import IdeaForm from "../IdeaForm/IdeaForm";
import ListItemsOfApprovedIdeas from "../../components/ListItemsOfApprovedIdeas/ListItemsOfApprovedIdeas";

class Ideas extends Component {

    state = {
        approvedIdeas: [],
        user: ''
    };

    componentDidMount() {
        document.title = 'Ideas';

        const user = JSON.parse(window.localStorage.getItem("user"));
        this.setState({user: user});

        if (localStorage.user && JSON.parse(window.localStorage.getItem("user")).role === "ROLE_USER") {
            this.getApprovedIdeas();
        }
    };

    getApprovedIdeas = () => {
        axios.get("/api/ideas/allApprovedIdeas")
            .then(response => {
                //console.log(response);
                this.setState({
                    approvedIdeas: response.data
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
        let listItemsOfApprovedIdeas = null;
        let ideaFormItem = null;

        if (localStorage.user && JSON.parse(window.localStorage.getItem("user")).role === "ROLE_USER") {

            listItemsOfApprovedIdeas =
                this.state.approvedIdeas.map((idea, index) => {
                    return (
                        <ListItemsOfApprovedIdeas
                            key={index}
                            idea={idea}
                            voteHandler={this.voteHandler}
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
                    Approved Ideas
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
                        {listItemsOfApprovedIdeas}
                        </tbody>
                    </table>
                    <br/>

                {ideaFormItem}
            </div>
        )
    }
}

export default Ideas;