import React, {Component} from 'react';
import axios from "axios";
import ListItemsOfApprovedIdeasForAdmin
    from "../../components/ListItemsOfApprovedIdeasForAdmin/ListItemsOfApprovedIdeasForAdmin";
import ListItemsOfUnApprovedIdeasForAdmin
    from "../../components/ListItemsOfUnApprovedIdeasForAdmin/ListItemsOfUnApprovedIdeasForAdmin";


class IdeasForAdmin extends Component {

    state = {
        allApprovedIdeas: [],
        allUnApprovedIdeas: [],
        user: ''
    };

    componentDidMount() {
        document.title = 'Ideas (forAdmin)';

        const user = JSON.parse(window.localStorage.getItem("user"));
        this.setState({user: user});

        if (localStorage.user && JSON.parse(window.localStorage.getItem("user")).role === "ROLE_ADMIN") {
            this.getAllApprovedIdeas();
            this.getAllUnApprovedIdeas();
        }
    };

    getAllApprovedIdeas = () => {
        axios.get("/api/ideas/allApprovedIdeasForAdmin")
            .then(response => {
                //console.log(response);
                this.setState({
                    allApprovedIdeas: response.data
                })
            })
            .catch(error => {
                //console.warn(error.response);
            });
    };

    getAllUnApprovedIdeas = () => {
        axios.get("/api/ideas/allUnApprovedIdeasForAdmin")
            .then(response => {
                //console.log(response);
                this.setState({
                    allUnApprovedIdeas: response.data
                })
            })
            .catch(error => {
                //console.warn(error.response);
            });
    };

    approveHandler = (id) => {
        axios.post("/api/ideas/" + id + "/approve")
            .then(response => {
                //console.log(response);
                this.getAllApprovedIdeas();
                this.getAllUnApprovedIdeas();
            })
            .catch(error => {
                //console.warn(error.response);
            });
    };

    deleteHandler = (id) => {
        axios.delete("/api/ideas/" + id)
            .then(response => {
                //console.log(response);
                this.getAllApprovedIdeas();
                this.getAllUnApprovedIdeas();
            })
            .catch(error => {
                //console.warn(error.response);
            });
    };

    render() {
        let listItemsOfAllApprovedIdeas = null;
        let listItemsOfAllUnApprovedIdeas = null;

        if (localStorage.user && JSON.parse(window.localStorage.getItem("user")).role === "ROLE_ADMIN") {

            listItemsOfAllApprovedIdeas =
                this.state.allApprovedIdeas.map((idea, index) => {
                    return (
                        <ListItemsOfApprovedIdeasForAdmin
                            key={index}
                            idea={idea}
                        />
                    )
                });

            listItemsOfAllUnApprovedIdeas =
                this.state.allUnApprovedIdeas.map((idea, index) => {
                    return (
                        <ListItemsOfUnApprovedIdeasForAdmin
                            key={index}
                            idea={idea}
                            approveHandler={this.approveHandler}
                            deleteHandler={this.deleteHandler}
                        />
                    )
                });
        }

        return (
            <div className="container">
                <h6>
                    Logged as : {localStorage.user ? this.state.user["userName"] : "anonymus"} "admin".
                </h6>
                <br/>

                <h2>
                    Approved Ideas
                </h2>
                <br/>
                <table className="table table-bordered">
                    <thead>
                    <tr className="table-success">
                        <th scope="col">TimeStamp</th>
                        <th scope="col">Name</th>
                        <th scope="col">Description</th>
                        <th scope="col">Full Description</th>
                        <th scope="col">Vote</th>
                    </tr>
                    </thead>
                    <tbody>
                    {listItemsOfAllApprovedIdeas}
                    </tbody>
                </table>
                <br/>

                <h2>
                    UnApproved Ideas
                </h2>
                <br/>
                <table className="table table-bordered">
                    <thead>
                    <tr className="table-danger">
                        <th scope="col">TimeStamp</th>
                        <th scope="col">Name</th>
                        <th scope="col">Description</th>
                        <th scope="col">Full Description</th>
                        <th scope="col">Vote</th>
                        <th scope="col">Approve</th>
                        <th scope="col">Reject</th>
                    </tr>
                    </thead>
                    <tbody>
                    {listItemsOfAllUnApprovedIdeas}
                    </tbody>
                </table>
                <br/>

            </div>
        )
    }
}

export default IdeasForAdmin;