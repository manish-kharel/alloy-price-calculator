import * as React from 'react';
import Box from '@mui/material/Box';
import TextField from '@mui/material/TextField';
import MenuItem from '@mui/material/MenuItem';
import {useEffect, useState} from "react";
import {getAllMetalPrices} from "../helper/Controller";
import Button from "@mui/material/Button";
import Table from '@mui/material/Table';
import TableBody from '@mui/material/TableBody';
import TableCell from '@mui/material/TableCell';
import TableContainer from '@mui/material/TableContainer';
import TableHead from '@mui/material/TableHead';
import TableRow from '@mui/material/TableRow';
import Paper from '@mui/material/Paper';


export default function ManualInputPage() {

    const [priceMap, setPriceMap] = useState(new Map)

    const [metalOptions, setMetalOptions] = useState([])

    const [enteredValues, setEnteredValues] = useState({name: '', composition: 0})

    const [tableValues, setTableValues] = useState([])

    const [totalWeight, setTotalWeight] = useState(0);

    // const [bool, setBool] = useState(false)

    const [tableColumns, setTableColumns] = useState(['Metal Name', 'Percentage Composition'])

    const onWeightChange = (event) => {
        setTotalWeight(event.target.value)
    }

    const selectMetalName = (e) => {
        setEnteredValues({...enteredValues, name: e.target.value})
    }

    const setMetalComposition = (e) => {
        setEnteredValues({...enteredValues, composition: +e.target.value})
    }

    const addMetalComposition = () => {
        let bool = true
        for (const element of tableValues) {
            if (element.name === enteredValues.name) {
                bool = false
                break;
            }
        }
        if (enteredValues.name && (enteredValues.composition !== 0) && bool)
            setTableValues([...tableValues, enteredValues])
    }

    useEffect(() => {
        getAllMetalPrices().then((response) => {
                const prices = [...response.data]
                let temp = new Map()
                prices.map(price => {
                    temp[price.name] = price.costPerUnit

                })
                setPriceMap(temp)
                setMetalOptions(prices)
            }
        )
    }, [])

    const calculatePrice = () => {
        let newItems = []
        tableValues.map(item => {
            let equivalentPrice = 0;
            const equivalentWeight = Math.round(((item.composition / 100) * totalWeight) * 100) / 100
            if (priceMap[item.name]) {
                equivalentPrice = Math.round((equivalentWeight * priceMap[item.name]) * 100) / 100
            }

            const newMeasurement = {...item, equivalentWeight, equivalentPrice}
            console.log(newMeasurement)

            newItems.push(newMeasurement)
        })

        console.log(newItems)
        setTableColumns(["Metal", "Percentage Composition", 'Equivalent Weight', 'Equivalent Price'])
        setTableValues(newItems)
    }

    const calculateTotalPrice = () => {
        let sum = 0;
        tableValues.map((item) => {
            sum = sum + item.equivalentPrice
        })
        return sum;
    }

    return (
        <div>
            <p>Enter items</p>
            <Box
                component="form"
                noValidate
                autoComplete="off"
            >

                <TextField
                    id="outlined-select-currency"
                    select
                    label="Select"
                    // defaultValue=""
                    value={enteredValues.name}
                    onChange={selectMetalName}
                    helperText="Please select the metal"
                >
                    {
                        metalOptions.map((option) => (
                            <MenuItem key={option.name} value={option.name}>
                                {option.name}
                            </MenuItem>
                        ))
                    }
                </TextField>
                <TextField
                    id="composition"
                    label="% Composition"
                    type='number'
                    value={enteredValues.composition}
                    onChange={setMetalComposition}
                />
                <Button onClick={addMetalComposition} variant="contained">Add Metal</Button>
            </Box>
            {
                tableValues[0] && <TableContainer component={Paper}>
                    <Table aria-label="a dense table">
                        <TableHead>
                            <TableRow>
                                {tableColumns.map((item) => (
                                    <TableCell key={item}>{item}</TableCell>
                                ))}
                            </TableRow>
                        </TableHead>
                        <TableBody>
                            {tableValues.map((row) => (
                                <TableRow
                                    key={row.name}
                                >
                                    <TableCell component="th" scope="row">
                                        {row.name}
                                    </TableCell>
                                    <TableCell>{row.composition}</TableCell>
                                    {row.equivalentWeight && <TableCell>{row.equivalentWeight}</TableCell>}
                                    {(row.equivalentPrice || row.equivalentPrice === 0) &&
                                        <TableCell>{row.equivalentPrice}</TableCell>}
                                </TableRow>
                            ))}

                            {tableValues[0].equivalentPrice &&
                                <TableRow>
                                    <TableCell rowSpan={3}/>
                                    <TableCell colSpan={2}>Subtotal</TableCell>
                                    <TableCell>{calculateTotalPrice()}</TableCell>
                                </TableRow>
                            }

                        </TableBody>
                    </Table>
                </TableContainer>
            }
            <p> Enter Sample Weight in Grams</p>
            <TextField
                id="sample-weight"
                label="Weight"
                value={totalWeight}
                onChange={onWeightChange}
            />
            <Button onClick={calculatePrice} variant="contained">Calculate Price</Button>

        </div>
    );
}