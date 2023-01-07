import * as React from 'react';
import {useEffect, useState} from 'react';
import {getAllMetalPrices, getAnalysisSummaryList, getMeasurement} from "../helper/Controller";
import {useNavigate} from "react-router-dom";
import Box from '@mui/material/Box';
import Collapse from '@mui/material/Collapse';
import IconButton from '@mui/material/IconButton';
import Table from '@mui/material/Table';
import TableBody from '@mui/material/TableBody';
import TableCell from '@mui/material/TableCell';
import TableContainer from '@mui/material/TableContainer';
import TableHead from '@mui/material/TableHead';
import TableRow from '@mui/material/TableRow';
import Typography from '@mui/material/Typography';
import Paper from '@mui/material/Paper';
import KeyboardArrowDownIcon from '@mui/icons-material/KeyboardArrowDown';
import KeyboardArrowUpIcon from '@mui/icons-material/KeyboardArrowUp';
import {TextField} from "@mui/material";
import Button from "@mui/material/Button";

function Row(props) {
    const {row} = props;
    const [open, setOpen] = useState(false);
    const [measurements, setMeasurements] = useState([]);
    const [totalWeight, setTotalWeight] = useState(0);

    const [measurementColumns, setMeasurementColumns] = useState(["Metal", "Percentage Composition", "Standard Deviation"])

    const onRowClick = async (uuid) => {
        if (!open) {
            const measurement = await getMeasurement(uuid)
            setMeasurements(measurement.data[0].elementCompositions)
        }
        setOpen(!open)
    };

    const onWeightChange = (event) => {
        setTotalWeight(event.target.value)
    }

    const calculatePrice = () => {

        getAllMetalPrices().then((response) => {
            const prices = [...response.data]
            let priceMap = new Map()

            prices.map(price => {
                priceMap[price.name] = price.costPerUnit

            })
            let newItems = []
            measurements.map(item => {
                let equivalentPrice = 0;
                const equivalentWeight = Math.round(((item.value / 100) * totalWeight) * 100) / 100
                if (priceMap[item.metal]) {
                    equivalentPrice = Math.round((equivalentWeight * priceMap[item.metal]) * 100) / 100
                }

                const newMeasurement = {...item, equivalentWeight, equivalentPrice}
                newItems.push(newMeasurement)
            })

            console.log(newItems)
            setMeasurementColumns(["Metal", "Percentage Composition", "Standard Deviation", 'Equivalent Weight', 'Equivalent Price'])
            setMeasurements(newItems)
        }).catch(error => {
            console.log(error);
        })
    }

    return (
        <React.Fragment>
            <TableRow sx={{'& > *': {borderBottom: 'unset'}}}>
                <TableCell>
                    <IconButton
                        aria-label="expand row"
                        size="small"
                        onClick={() => onRowClick(row.id)}
                    >
                        {open ? <KeyboardArrowUpIcon/> : <KeyboardArrowDownIcon/>}
                    </IconButton>
                </TableCell>
                <TableCell component="th" scope="row">
                    {row.id}
                </TableCell>
                <TableCell align="right">{row.username}</TableCell>
                <TableCell align="right">{row.profileName}</TableCell>
                <TableCell align="right">{row.referenceName}</TableCell>
                <TableCell align="right">{row.resultType}</TableCell>
                <TableCell align="right">{row.measurementType}</TableCell>
            </TableRow>
            <TableRow>
                <TableCell style={{paddingBottom: 0, paddingTop: 0}} colSpan={6}>
                    <Collapse in={open} timeout="auto" unmountOnExit>
                        <Box sx={{margin: 1}}>
                            <Typography variant="h6" gutterBottom component="div">
                                Measurements
                            </Typography>
                            <Table size="small" aria-label="purchases">
                                <TableHead>
                                    <TableRow>
                                        {measurementColumns.map((item, index) => (
                                            <TableCell key={index}>{item}</TableCell>
                                        ))}

                                    </TableRow>
                                </TableHead>
                                <TableBody>
                                    {measurements.map((measurement, index) => (
                                        <TableRow key={index}>
                                            <TableCell component="th" scope="row">
                                                {measurement.metal}
                                            </TableCell>
                                            <TableCell>{measurement.value}</TableCell>
                                            <TableCell align="right">{measurement.standardDeviation}</TableCell>
                                            {measurement.equivalentWeight && <TableCell align="right">{measurement.equivalentWeight}</TableCell>}
                                            {(measurement.equivalentPrice  || measurement.equivalentPrice === 0) && <TableCell align="right">{measurement.equivalentPrice}</TableCell>}
                                        </TableRow>
                                    ))}
                                </TableBody>
                            </Table>
                            <p> Enter Sample Weight in Grams</p>
                            <TextField
                                id="sample-weight"
                                label="Weight"
                                value={totalWeight}
                                onChange={onWeightChange}
                            />
                            <Button onClick={calculatePrice} variant="contained">Calculate Price</Button>
                        </Box>
                    </Collapse>
                </TableCell>
            </TableRow>
        </React.Fragment>
    );
}

function AnalysisSummariesPage() {
    let navigate = useNavigate()

    const [summaryList, setSummaryList] = useState([])

    useEffect(() => {
        getAnalysisSummaryList().then((response) => {
            setSummaryList(response.data)
        }).catch(error => {
            console.log(error);
        })
    }, [])

    return (
        <TableContainer component={Paper}>
            <Table aria-label="collapsible table">
                <TableHead>
                    <TableRow>
                        <TableCell/>
                        <TableCell align="left">Id</TableCell>
                        <TableCell align="right">Username</TableCell>
                        <TableCell align="right">Profile Name</TableCell>
                        <TableCell align="right">Reference Name</TableCell>
                        <TableCell align="right">Result Type</TableCell>
                        <TableCell align="right">Measurement Type</TableCell>
                    </TableRow>
                </TableHead>
                <TableBody>
                    {summaryList.map((row) => (
                        <Row key={row.id} row={row}/>
                    ))}
                </TableBody>
            </Table>
        </TableContainer>
    );
}

export default AnalysisSummariesPage;