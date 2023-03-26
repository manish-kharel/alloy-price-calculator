import React, {useEffect, useState} from 'react';
import {
    getAllMetalPrices,
    getAnalysisSummaryList,
    getMeasurement,
    getListOfAllExchangeRates,
    createResultPrice
} from "../helper/Controller";
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

import Container from '@mui/material/Container';

import MenuItem from '@mui/material/MenuItem';
import InputLabel from '@mui/material/InputLabel';
import FormControl from '@mui/material/FormControl';
import Select from '@mui/material/Select';

import SaveIcon from '@mui/icons-material/Save';
import Modal from '@mui/material/Modal';

function Row(props) {
    const {row} = props;
    const [open, setOpen] = useState(false);
    const [measurements, setMeasurements] = useState([]);
    const [totalWeight, setTotalWeight] = useState(0);
    const [measurementColumns, setMeasurementColumns] = useState(["Metal", "Percentage Composition", "Standard Deviation"])
    const [currencyRates, setCurrencyRates] = useState([])
    const [selectedCurrency, setSelectedCurrency] = useState("USD");
    const [currencyMap, setCurrencyMap] = useState(new Map);
    const [openModal, setOpenModal] = useState(false);
    const [name, setName] = useState(false);

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

    const handleCurrencyChange = (event) => {
        setSelectedCurrency(event.target.value);
        console.log(event.target.value)

    };
    const handleOpenModal = () => setOpenModal(true);
    const handleCloseModal = () => setOpenModal(false);

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

    useEffect(() => {
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

    function calculatePrice() {
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
                    equivalentPrice = (equivalentWeight * priceMap[item.metal])
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

    const calculateTotalPrice = () => {
        let sum = 0;
        measurements.map((item) => {
            sum = sum + item.equivalentPrice
        })
        console.log("summm", sum)
        return Math.round(sum * currencyMap[selectedCurrency] * 100) / 100
    }

    const style = {
        position: 'absolute',
        top: '50%',
        left: '50%',
        transform: 'translate(-50%, -50%)',
        width: 400,
        bgcolor: 'background.paper',
        borderRadius: "30px",
        boxShadow: 24,
        p: 4,
    };

    function handleSave() {
        let lado = {
            name: name,
            sampleWeight: parseInt(totalWeight),
            currency: selectedCurrency,
            elementValues: measurements
        }
        createResultPrice(lado).then((response) => {
            console.log(response)
            handleCloseModal()

        }).catch(error => {
            console.log(error);
        })

    }

    return (
        <React.Fragment>
            <Modal
                open={openModal}
                onClose={handleCloseModal}
                aria-labelledby="modal-modal-title"
                aria-describedby="modal-modal-description"
            >
                <Box sx={style}>
                    <Typography id="modal-modal-title" variant="h4" component="h2" sx={{marginBottom: "30px"}}>
                        Enter a name to save
                    </Typography>
                    <TextField id="outlined-basic" label="name" variant="outlined" onChange={(e) => {
                        setName(e.target.value)
                    }}/>

                    <Box
                        sx={{
                            display: 'flex',
                            justifyContent: 'space-around',
                            p: 1,
                            m: 1,
                            marginTop: "50px",
                        }}
                    >
                        <Button variant="contained" onClick={handleSave}>Save</Button>
                        <Button variant="contained" onClick={handleCloseModal}>Cancel</Button>
                    </Box>

                </Box>
            </Modal>

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
                <TableCell align="center">{row.username}</TableCell>
                <TableCell align="center">{row.profileName}</TableCell>
                <TableCell align="center">{row.referenceName}</TableCell>
                <TableCell align="center">{row.resultType}</TableCell>
                <TableCell align="center">{row.measurementType}</TableCell>
            </TableRow>
            <TableRow>
                <TableCell style={{paddingBottom: 0, paddingTop: 0}} colSpan={6}>
                    <Collapse in={open} timeout="auto" unmountOnExit>
                        <Box sx={{margin: 1}}>

                            <Box
                                sx={{
                                    display: 'flex',
                                    justifyContent: 'space-between',
                                    p: 1,
                                    m: 1,
                                    bgcolor: 'background.paper',
                                    borderRadius: 1,
                                }}
                            >
                                <Typography variant="h6" gutterBottom component="span">
                                    Measurements
                                </Typography>
                                <span>{measurementColumns.length > 3 && <FormControl sx={{textAlign: "end"}}>
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
                                </FormControl>}</span>

                            </Box>

                            {
                                measurements[0] && <Table size="small" aria-label="purchases">
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
                                                <TableCell align="center">{measurement.standardDeviation}</TableCell>
                                                {(measurement.equivalentWeight || measurement.equivalentWeight === 0) &&
                                                    <TableCell align="center">{measurement.equivalentWeight}</TableCell>}
                                                {(measurement.equivalentPrice || measurement.equivalentPrice === 0) &&
                                                    <TableCell
                                                        align="center">{Math.round(measurement.equivalentPrice * currencyMap[selectedCurrency] * 100) / 100}</TableCell>}
                                            </TableRow>
                                        ))}
                                        {measurements[0].equivalentPrice &&
                                            <TableRow>
                                                <TableCell colSpan={3}></TableCell>
                                                <TableCell align="center">Subtotal</TableCell>
                                                <TableCell align="center">{calculateTotalPrice()}</TableCell>
                                            </TableRow>
                                        }


                                    </TableBody>
                                </Table>
                            }
                            <p> Enter Sample Weight in Ounce</p>
                            <TextField
                                id="sample-weight"
                                label="Weight"
                                value={totalWeight}
                                onChange={onWeightChange}
                            />
                            <Button onClick={calculatePrice} variant="contained">Calculate Price</Button>
                            <SaveIcon sx={{fontSize: "36px"}} onClick={() => {
                                handleOpenModal()
                            }}/>
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
        <Container maxWidth="lg">
            <TableContainer component={Paper} sx={{marginTop: "20px"}}>
                <Table aria-label="collapsible table" size="small">
                    <TableHead>
                        <TableRow>
                            <TableCell/>
                            <TableCell align="center">Id</TableCell>
                            <TableCell align="center">Username</TableCell>
                            <TableCell align="center">Profile Name</TableCell>
                            <TableCell align="center">Reference Name</TableCell>
                            <TableCell align="center">Result Type</TableCell>
                            <TableCell align="center">Measurement Type</TableCell>
                        </TableRow>
                    </TableHead>
                    <TableBody>
                        {summaryList.map((row) => (
                            <Row key={row.id} row={row}/>
                        ))}
                    </TableBody>
                </Table>
            </TableContainer>

        </Container>
    );
}

export default AnalysisSummariesPage;