import React from 'react';
import '../ListItemsOfApprovedIdeasExceptTheUser/ListItemsOfApprovedIdeasExceptTheUser.css'
import Popup from "reactjs-popup";

function ListItemsOfApprovedIdeasOfTheUser(props) {

    let popUpItem = null;

    if (localStorage.user && JSON.parse(window.localStorage.getItem("user")).role === "ROLE_USER") {

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
        </tr>
    );
}

export default ListItemsOfApprovedIdeasOfTheUser;