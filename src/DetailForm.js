import React, {useEffect, useMemo, useReducer, useState} from "react";
import {TextField, CircularProgress, Button, Fab} from "@material-ui/core";
import {makeStyles} from "@material-ui/core/styles";
import {green} from "@material-ui/core/colors";
import CheckIcon from '@material-ui/icons/Check';
import SaveIcon from '@material-ui/icons/Save';
import FormControl from "@material-ui/core/FormControl";
import InputLabel from "@material-ui/core/InputLabel";
import Select from "@material-ui/core/Select";
import MenuItem from "@material-ui/core/MenuItem";

const useStyles = makeStyles((theme) => ({
    root: {
        display: 'flex',
        alignItems: 'center',
    },
    wrapper: {
        margin: theme.spacing(1),
        position: 'relative',
    },
    buttonSuccess: {
        backgroundColor: green[500],
        '&:hover': {
            backgroundColor: green[700],
        },
    },
    fabProgress: {
        color: green[500],
        position: 'absolute',
        top: -6,
        left: -6,
        zIndex: 1,
    },
    buttonProgress: {
        color: green[500],
        position: 'absolute',
        top: '50%',
        left: '50%',
        marginTop: -12,
        marginLeft: -12,
    },
}));

function formReducer(prevState, action) {
    switch (action.type) {
        case "setCode":
            return {...prevState, code: action.code}
        case "setDescription":
            return {...prevState, description: action.description}
        case "setName":
            return {...prevState, name: action.name}
        case "setCount":
            return {...prevState, count: action.count}
        case "setUnits":
            return {...prevState, units: {id: action.units}}
        case "setSorts":
            return {...prevState, sorts: {id: action.sorts}}
        case "setTypes":
            return {...prevState, types: {id: action.types}}
        case "setTags":
            return {...prevState, tags: {id: action.tags}}
        case "all":
            return action.prev
        default: throw new Error("So, it's reducer error")
    }
}

function useReducerInput(dispatch, value, field, actionType) {
    const onChange = event => {
        dispatch({type: actionType, [field]: event.target.value})
    }
    return {
        value, onChange
    }
}

function useDetailsInfoLoad() {
    const [info, setInfo] = useState({units: [], sorts: [], types: [], tags: []})
    useEffect(() => {
        const fetchData = async () => {
            const responseUnits = await fetch("http://localhost:8080/api/units");
            const responseSorts = await fetch("http://localhost:8080/api/sorts");
            const responseTypes = await fetch("http://localhost:8080/api/types");
            const responseTags = await fetch("http://localhost:8080/api/tags");
            setInfo({
                units: await responseUnits.json(),
                sorts: await responseSorts.json(),
                types: await responseTypes.json(),
                tags: await responseTags.json()
            })
        }
        fetchData().catch(console.log);
    }, []);
    return [info, setInfo];
}

function getInitializerForm() {
    return {
        code: 0, description: "",
        name: "", count: 0,
        units: {id: 0}, sorts: {id: 0},
        types: {id: 0}, tags:{id: 0}
    };
}

function DetailForm({onSubmit, prevInstance}) {
    const classes = useStyles();
    const [progress, setProgress] = React.useState({load: false, success: false});
    const [state, dispatch] = useReducer(formReducer, getInitializerForm(prevInstance), undefined);
    const timer = React.useRef(0);
    const buttonClassname = useMemo(() => ({
        [classes.buttonSuccess]: progress.success,
    }), [progress.success, classes.buttonSuccess]);

    useEffect(() => {
        if (prevInstance) {
            dispatch({type: "all", prev: prevInstance});
        }
    }, [prevInstance])

    const [info, ] = useDetailsInfoLoad()
    const codeInput = useReducerInput(dispatch, state.code, "code", "setCode");
    const descriptionInput = useReducerInput(dispatch, state.description, "description", "setDescription");
    const nameInput = useReducerInput(dispatch, state.name, "name", "setName");
    const countInput = useReducerInput(dispatch, state.count, "count", "setCount");
    const unitsInput = useReducerInput(dispatch, state.units.id, "units", "setUnits");
    const sortsInput = useReducerInput(dispatch, state.sorts.id, "sorts", "setSorts");
    const typesInput = useReducerInput(dispatch, state.types.id, "types", "setTypes");
    const tagsInput = useReducerInput(dispatch, state.tags.id, "tags", "setTags");

    React.useEffect(() => {
        return () => {
            clearTimeout(timer.current);
        };
    }, []);

    const handleButtonClick = () => {
        if (!progress.load) {
            setProgress({load: true, success: false});
            const answer = onSubmit(state);
            dispatch({type: "all", prev: getInitializerForm()});
            if (answer && answer.then) {
                answer.then(callback => {
                    if (callback) {
                        callback();
                    }
                    setProgress({load: false, success: true});
                    timer.current = setTimeout(() => {
                        setProgress({load: false, success: false});
                    }, 1000);
                });
                answer.catch(() => {
                    setProgress({load: false, success: false});
                });
            } else {
                timer.current = setTimeout(() => {
                    setProgress({load: false, success: false});
                }, 1000);
            }
        }
    };

    return (
        <div>
            <form noValidate autoComplete={"off"}>
                <div className={classes.root}>
                    {
                        !!prevInstance && <TextField label={"Id"} variant={"filled"} type={"number"} value={state.id} disabled />
                    }
                    <TextField label={"Code"} variant={"filled"} type={"number"} {...codeInput} />
                    <TextField label={"Description"} variant={"filled"} {...descriptionInput} />
                    <TextField label={"Name"} variant={"filled"} {...nameInput} />
                    <TextField label={"Count"} variant={"filled"} type={"number"} {...countInput} />
                    <div className={classes.root}>
                        <div className={classes.wrapper}>
                            <Fab
                                aria-label="save"
                                color="primary"
                                className={buttonClassname}
                                onClick={handleButtonClick}>
                                {progress.success ? <CheckIcon /> : <SaveIcon />}
                            </Fab>
                            {progress.load && <CircularProgress size={68} className={classes.fabProgress} />}
                        </div>
                        <div className={classes.wrapper}>
                            <Button
                                variant="contained"
                                color="primary"
                                className={buttonClassname}
                                disabled={progress.load}
                                onClick={handleButtonClick}>
                                Accept
                            </Button>
                            {progress.load && <CircularProgress size={24} className={classes.buttonProgress} />}
                        </div>
                    </div>
                </div>
                <div>
                    <FormControl variant="filled">
                        <InputLabel id="units-select-filled-label">Units</InputLabel>
                        <Select
                            labelId="units-select-filled-label"
                            id="units-select-filled"
                            value={state.units.id}
                            onChange={unitsInput.onChange}>
                            <MenuItem value={0}>
                                <em>None</em>
                            </MenuItem>
                            {info.units.map(it => <MenuItem key={it.id} value={it.id}>{it.name}</MenuItem>)}
                        </Select>
                    </FormControl>
                    <FormControl variant="filled">
                        <InputLabel id="sorts-select-filled-label">Sorts</InputLabel>
                        <Select
                            labelId="sorts-select-filled-label"
                            id="sorts-select-filled"
                            value={state.sorts.id}
                            onChange={sortsInput.onChange}>
                            <MenuItem value={0}>
                                <em>None</em>
                            </MenuItem>
                            {info.sorts.map(it => <MenuItem key={it.id} value={it.id}>{it.name}</MenuItem>)}
                        </Select>
                    </FormControl>
                    <FormControl variant="filled">
                        <InputLabel id="types-select-filled-label">Types</InputLabel>
                        <Select
                            labelId="types-select-filled-label"
                            id="types-select-filled"
                            value={state.types.id}
                            onChange={typesInput.onChange}>
                            <MenuItem value={0}>
                                <em>None</em>
                            </MenuItem>
                            {info.types.map(it => <MenuItem key={it.id} value={it.id}>{it.name}</MenuItem>)}
                        </Select>
                    </FormControl>
                    <FormControl variant="filled">
                        <InputLabel id="tags-select-filled-label">Tags</InputLabel>
                        <Select
                            labelId="tags-select-filled-label"
                            id="tags-select-filled"
                            value={state.tags.id}
                            onChange={tagsInput.onChange}>
                            <MenuItem value={0}>
                                <em>None</em>
                            </MenuItem>
                            {info.tags.map(it => <MenuItem key={it.id} value={it.id}>{it.name}</MenuItem>)}
                        </Select>
                    </FormControl>
                </div>
            </form>
        </div>
    )
}

export default DetailForm