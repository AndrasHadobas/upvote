import React from 'react';
import Popup from "reactjs-popup";
import '../ListItemsOfApprovedIdeas/ListItemsOfApprovedIdeas.css'

function ListItemsOfUnApprovedIdeasForAdmin(props) {

    let approveButtonItem = null;
    let deleteButtonItem = null;
    let popUpItem = null;

    if (localStorage.user && JSON.parse(window.localStorage.getItem("user")).role === "ROLE_ADMIN") {

        approveButtonItem =
            <button type="button" className="btn btn-success" onClick={() => props.approveHandler(props.idea.id)}>
                Approve
            </button>;

        deleteButtonItem =
            <button type="button" className="btn btn-danger" onClick={() => props.deleteHandler(props.idea.id)}>
                Reject
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
            <td>{props.idea.vote}</td>
            <td>{approveButtonItem}</td>
            <td>{deleteButtonItem}</td>
        </tr>
    );
}

export default ListItemsOfUnApprovedIdeasForAdmin;