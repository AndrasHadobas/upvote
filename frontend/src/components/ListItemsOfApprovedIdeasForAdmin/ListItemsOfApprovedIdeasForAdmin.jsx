import React from 'react';
import Popup from "reactjs-popup";
import '../ListItemsOfApprovedIdeasExceptTheUser/ListItemsOfApprovedIdeasExceptTheUser.css'

function ListItemsOfApprovedIdeasForAdmin(props) {

    let popUpItem = null;

    if (localStorage.user && JSON.parse(window.localStorage.getItem("user")).role === "ROLE_ADMIN") {

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
            <td>{props.idea.vote}</td>
        </tr>
    );
}

export default ListItemsOfApprovedIdeasForAdmin;