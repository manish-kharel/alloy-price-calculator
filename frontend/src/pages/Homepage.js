import React, {useEffect, useState} from 'react';
import Table from '@mui/material/Table';
import TableBody from '@mui/material/TableBody';
import TableCell from '@mui/material/TableCell';
import TableContainer from '@mui/material/TableContainer';
import TableHead from '@mui/material/TableHead';
import TablePagination from '@mui/material/TablePagination';
import TableRow from '@mui/material/TableRow';
import Container from '@mui/material/Container';
import Alert from '@mui/material/Alert';

import MenuItem from '@mui/material/MenuItem';
import InputLabel from '@mui/material/InputLabel';
import FormControl from '@mui/material/FormControl';
import Select from '@mui/material/Select';

import {getAllMetalPrices, getListOfAllExchangeRates} from "../helper/Controller";
import {Button} from "@mui/material";
import {useNavigate} from "react-router-dom"

import SavedResult from './SavedResult';

function Homepage(props) {
    let navigate = useNavigate()
    const [page, setPage] = React.useState(0);
    const [rowsPerPage, setRowsPerPage] = React.useState(10);
    const [prices, setPrices] = useState([])
    const [realtimeStatus, setRealtimeStatus] = useState(true)
    const [currencyRates, setCurrencyRates] = useState([])
    const [selectedCurrency, setSelectedCurrency] = useState("USD");
    const [currencyMap, setCurrencyMap] = useState(new Map);
    const [savedResultShower, setSavedResultShower] = useState(false);

    useEffect(() => {

        getAllMetalPrices().then((response) => {
            setPrices(response.data)
            if (response.status === 203) {
                setRealtimeStatus(false)
            }
        }).catch(error => {
            console.log(error);
        })

        getListOfAllExchangeRates().then((response) => {
            setCurrencyRates(response.data)

            const currencies = [...response.data]
            let temp = new Map()
            currencies.map(item => {
                temp[item.currency] = item.rate

            })
            setCurrencyMap(temp)
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
        {id: 'id', label: 'Id', minWidth: 170, align: 'center'},
        {id: 'name', label: 'Name', minWidth: 170},
        {id: 'unit', label: 'Weight Unit', minWidth: 100},
        {
            id: 'costPerUnit',
            label: 'Price Per Given Unit ' + `(` + selectedCurrency + `)`,
            minWidth: 170,
            align: 'center',
            format: (value) => value.toFixed(4)
        },
    ];

    const handleCurrencyChange = (event) => {
        setSelectedCurrency(event.target.value);
        console.log(event.target.value)
    };

    const ITEM_HEIGHT = 40;
    const ITEM_PADDING_TOP = 8;
    const MenuProps = {
        PaperProps: {
            style: {
                maxHeight: ITEM_HEIGHT * 4.5 + ITEM_PADDING_TOP,
                width: 150,
            },
        },
    };

    return (
        <div>
            <Container maxWidth="lg">
                {!realtimeStatus &&
                    <Alert severity="error" sx={{fontSize: "18px"}}>Metals API Server is down. Value is not
                        Realtime!</Alert>}
                <TableContainer sx={{maxHeight: 440}}>
                    <Table stickyHeader size="medium">
                        <TableHead>
                            <TableRow>
                                <TableCell align="center" colSpan={5}>
                                    Recently updated prices for metals
                                </TableCell>
                                <TableCell align="right" colSpan={5}>
                                    <FormControl>
                                        <InputLabel id="demo-simple-select-label">currency</InputLabel>
                                        <Select
                                            labelId="demo-simple-select-label"
                                            id="demo-simple-select"
                                            value={selectedCurrency}
                                            label="currency"
                                            onChange={handleCurrencyChange}
                                            MenuProps={MenuProps}
                                            autowidth="true"
                                        >
                                            {currencyRates.map((currency) => (
                                                <MenuItem value={currency.currency}>{currency.currency}</MenuItem>

                                            ))}
                                        </Select>
                                    </FormControl>
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
                                                let value
                                                if (column.id === "costPerUnit") {
                                                    value = price[column.id] * currencyMap[selectedCurrency]
                                                } else value = price[column.id]
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
                <Button onClick={() => navigate("/login")}>
                    Fetch from XRF Device</Button>
                <Button onClick={() => navigate("/manualInput",)}>Enter Data Manually</Button>

                <div>
                    <Button variant="contained" onClick={() => setSavedResultShower(true)} sx={{margin: "30px"}}>Show
                        Previously saved Results</Button>
                </div>
                {savedResultShower && <SavedResult/>}
            </Container>
        </div>
    );
}

export default Homepage;