import React, {useEffect, useRef, useState} from 'react';
import {DataGrid} from '@material-ui/data-grid';
import {Button} from "@material-ui/core";
import DetailForm from "./DetailForm"
import DetailUpdateDialog from "./DetailUpdateDialog";

const columns = [
    {field: 'id', headerName: 'ID', type: 'number', width: 70,
        align: 'center', headerAlign: 'center'},
    {field: 'code', headerName: 'CODE', type: 'number', width: 100,
        align: 'center', headerAlign: 'center'},
    {field: 'name', headerName: 'NAME', type: 'string', width: 100,
        align: 'center', headerAlign: 'center'},
    {field: 'count', headerName: 'COUNT', type: 'number', width: 100,
        align: 'center', headerAlign: 'center'},
    {field: 'description', headerName: 'DESCRIPTION', type: 'string', width: 150,
        align: 'center', headerAlign: 'center'},
    {field: 'units', headerName: 'UNITS', type: 'string', width: 100, sortable: false,
        valueGetter: p => p.data.units.name, align: 'center', headerAlign: 'center'},
    {field: 'sorts', headerName: 'SORTS', type: 'string', width: 100, sortable: false,
        valueGetter: p => p.data.sorts.name, align: 'center', headerAlign: 'center'},
    {field: 'types', headerName: 'TYPES', type: 'string', width: 100, sortable: false,
        valueGetter: p => p.data.types.name, align: 'center', headerAlign: 'center'},
    {field: 'tags', headerName: 'TAGS', type: 'string', width: 100, sortable: false,
        valueGetter: p => p.data.tags.name, align: 'center', headerAlign: 'center'},
];

function App() {
    const [rows, setRows] = useState([])
    const [selectedRows, setSelectedRows] = useState([])
    const [disableRemove, setDisableRemove] = useState(false)
    const [openDialog, setOpenDialog] = useState(false);
    const dataGridApi = useRef(null);
    useEffect(() => {
        const fetchData = async () => {
            const response = await fetch("http://localhost:8080/api/details");
            setRows(await response.json())
        }
        fetchData().catch(console.log);
    }, []);

    const submitForm = async data => {
        const response = await fetch("http://localhost:8080/api/details", {
            method: "POST",
            headers: {"Content-type": "application/json"},
            body: JSON.stringify(data)
        });
        if (!response.ok) {
            throw new Error("Cannot add detail");
        }
        const obj = await response.json();
        return () => {
            setRows([...rows, obj]);
        }
    };

    const onRemove = async () => {
        if (setSelectedRows.length > 0) {
            setDisableRemove(true);
            const response = await fetch("http://localhost:8080/api/details/all", {
                method: "DELETE",
                headers: {"Content-type": "application/json"},
                body: JSON.stringify(selectedRows)
            });
            if (response.ok) { // VERY BAD IDEA
                const newRows = [];
                for (let row of rows) {
                    if (!selectedRows.includes(row)) {
                        newRows.push(row);
                    }
                }
                setRows(newRows);
                setSelectedRows([])
            }
            setDisableRemove(false);
        }
    };

    const onCloseDialog = last => {
        setOpenDialog(false);
        if (last && dataGridApi.current) {
            dataGridApi.current.selectRows([], false, true);
        }
    }

    return (
        <React.Fragment>
            <DetailForm onSubmit={submitForm} />
            <div style={{height: 500}}>
                <DataGrid columns={columns} rows={rows} pageSize={7} checkboxSelection
                          onSelectionChange={param => {
                              setSelectedRows(param.rows)
                          }}
                          components={{
                              noRowsOverlay: params => {
                                  if (!dataGridApi.current) {
                                      dataGridApi.current = params.api.current;
                                  }
                                  return <div className="MuiDataGrid-overlay" style={{top: 56}}>No Rows</div>;
                              }
                          }}
                />
            </div>
            <Button variant={"contained"} color={"primary"} disabled={disableRemove} onClick={onRemove}>
                Remove selected
            </Button>
            <Button variant={"contained"} color={"primary"} onClick={() => setOpenDialog(true)}>
                Update selected
            </Button>
            <DetailUpdateDialog open={openDialog} onClose={onCloseDialog} records={selectedRows} />
        </React.Fragment>
    )
}


export default App;
