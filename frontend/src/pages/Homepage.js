import React, {useEffect, useState} from 'react';
import Paper from '@mui/material/Paper';
import Table from '@mui/material/Table';
import TableBody from '@mui/material/TableBody';
import TableCell from '@mui/material/TableCell';
import TableContainer from '@mui/material/TableContainer';
import TableHead from '@mui/material/TableHead';
import TablePagination from '@mui/material/TablePagination';
import TableRow from '@mui/material/TableRow';
import {getAllMetalPrices} from "../helper/Controller";
import {Button} from "@mui/material";
import {useNavigate} from "react-router-dom"

function Homepage(props) {
    let navigate = useNavigate()
    const [page, setPage] = React.useState(0);
    const [rowsPerPage, setRowsPerPage] = React.useState(10);
    const [prices, setPrices] = useState([])

    useEffect(() => {
        getAllMetalPrices().then((response) => {
            setPrices(response.data)
        }).catch(error => {
            console.log(error);
        })
    }, [])

    const handleChangeRowsPerPage = (event) => {
        setRowsPerPage(+event.target.value);
        setPage(0);
    };

    const handleChangePage = (event, newPage) => {
        setPage(newPage);
    };


    const columns = [
        {id: 'name', label: 'Name', minWidth: 170},
        {id: 'unit', label: 'Weight Unit', minWidth: 100},
        {
            id: 'costPerUnit', label: 'Price Per Given Unit', minWidth: 170, align: 'center',
            format: (value) => value.toFixed(4)
        },
        {
            id: 'baseCurrency', label: 'Base Currency', minWidth: 170, align: 'right',
            format: (value) => value.toLocaleString('en-US'),
        },
        {id: 'id', label: 'Id', minWidth: 170, align: 'center'},
    ];


    return (
        <div>
            <Paper sx={{width: '100%'}}>
                <TableContainer sx={{maxHeight: 440}}>
                    <Table stickyHeader aria-label="sticky table">
                        <TableHead>
                            <TableRow>
                                <TableCell align="center" colSpan={5}>
                                    Recently updated prices for metals
                                </TableCell>
                            </TableRow>
                            <TableRow>
                                {columns.map((column) => (
                                    <TableCell
                                        key={column.id}
                                        align={column.align}
                                        style={{top: 57, minWidth: column.minWidth}}
                                    >
                                        {column.label}
                                    </TableCell>
                                ))}
                            </TableRow>
                        </TableHead>
                        <TableBody>
                            {prices
                                .slice(page * rowsPerPage, page * rowsPerPage + rowsPerPage)
                                .map((price) => {
                                    return (
                                        <TableRow hover role="checkbox" tabIndex={-1} key={price.code}>
                                            {columns.map((column) => {
                                                const value = price[column.id];
                                                return (
                                                    <TableCell key={column.id} align={column.align}>
                                                        {column.format && typeof value === 'number'
                                                            ? column.format(value)
                                                            : value}
                                                    </TableCell>
                                                );
                                            })}
                                        </TableRow>
                                    );
                                })}
                        </TableBody>
                    </Table>
                </TableContainer>
                <TablePagination
                    rowsPerPageOptions={[5, 10, 15]}
                    component="div"
                    count={prices.length}
                    rowsPerPage={rowsPerPage}
                    page={page}
                    onPageChange={handleChangePage}
                    onRowsPerPageChange={handleChangeRowsPerPage}
                />
            </Paper>

            <Button onClick={() => navigate("/login")}>
                Fetch from XRF Device</Button>
            <Button onClick={() => navigate("/manualInput",)}>Enter Data Manually</Button>
        </div>
    );
}

export default Homepage;