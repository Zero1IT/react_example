import React, {useRef, useState} from "react";
import {Button} from "@material-ui/core";
import Dialog from "@material-ui/core/Dialog";
import DialogTitle from "@material-ui/core/DialogTitle";
import DialogContent from "@material-ui/core/DialogContent";
import DialogActions from "@material-ui/core/DialogActions";
import Slide from "@material-ui/core/Slide";
import DetailForm from "./DetailForm";

const Transition = React.forwardRef(function Transition(props, ref) {
    return <Slide direction="up" ref={ref} {...props} />;
});

function DetailUpdateDialog({open, onClose, records}) {
    const [updateIndex, setUpdateIndex] = useState(0)
    const updating = useRef(false);

    const onUpdate = async (current) => {
        if (!updating.current) {
            updating.current = true;
            if (!records || updateIndex >= records.length) {
                onClose(true);
            } else if (records.length > 0) {
                const response = await fetch("http://localhost:8080/api/details", {
                    method: "PUT",
                    headers: {"Content-type": "application/json"},
                    body: JSON.stringify(current)
                });
                if (response.ok) {
                    const updatedInstance = await response.json();
                    Object.assign(records[updateIndex], updatedInstance);
                    if (updateIndex + 1 >= records.length) {
                        onClose(true)
                    } else {
                        setUpdateIndex(updateIndex + 1);
                    }
                } else {
                    updating.current = false;
                    throw new Error("cannot update instance");
                }
            }
            updating.current = false;
        }
    }

    return (
        <Dialog
            fullWidth={true}
            maxWidth={"lg"}
            open={open}
            TransitionComponent={Transition}
            keepMounted
            onClose={onClose}
            aria-labelledby="alert-dialog-slide-title"
            aria-describedby="alert-dialog-slide-description">
            <DialogTitle id="alert-dialog-slide-title">{"Update records"}</DialogTitle>
            <DialogContent>
                <DialogContent id="alert-dialog-slide-description">
                    <DetailForm onSubmit={onUpdate} prevInstance={records ? records[updateIndex] : null} />
                </DialogContent>
            </DialogContent>
            <DialogActions>
                <Button onClick={onClose} color="primary">Ok</Button>
                <Button onClick={onClose} color="primary">Cancel</Button>
            </DialogActions>
        </Dialog>
    )
}

export default DetailUpdateDialog