import React from 'react';
import './ListItemsOfApprovedIdeasExceptTheUser.css'
import Popup from "reactjs-popup";

function ListItemsOfApprovedIdeasExceptTheUser(props) {

    let voteButtonItem = null;
    let popUpItem = null;

    if (localStorage.user && JSON.parse(window.localStorage.getItem("user")).role === "ROLE_USER") {

        voteButtonItem =
            <button
                type="button"
                className="btn btn-success"
                onClick={() => props.voteHandler(props.idea.id)}
            >Vote
            </button>;

        popUpItem =
            <Popup
                trigger={<button className="btn btn-info">Full Description</button>}
                position="right center"
                modal={true}
                closeOnDocumentClick
            >
                    <textarea
                        disabled={true}
                        defaultValue={props.idea.description}
                    />
            </Popup>
    }

    return (
        <tr>
            <td>{props.idea.createdAt}</td>
            <td className="tdText">{props.idea.name}</td>
            <td className="tdText">{props.idea.description}</td>
            <td>{popUpItem}</td>
            <td>{voteButtonItem}</td>
        </tr>
    );
}

export default ListItemsOfApprovedIdeasExceptTheUser;