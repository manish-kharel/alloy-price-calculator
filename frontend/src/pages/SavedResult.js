import React, {useEffect, useState} from 'react';
import {deletePrice, getAllSavedResults, getCalculationSummary} from "../helper/Controller";
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
import KeyboardArrowDownIcon from '@mui/icons-material/KeyboardArrowDown';
import KeyboardArrowUpIcon from '@mui/icons-material/KeyboardArrowUp';
import Button from "@mui/material/Button";
import Modal from '@mui/material/Modal';
import DeleteForeverIcon from '@mui/icons-material/DeleteForever';


function Row(props) {
    const {row} = props;
    const [open, setOpen] = useState(false);
    const measurementColumns = ["Metal", "Percentage Composition", "Standard Deviation", "Equivalent Weight", "Equivalent Price"]
    const [measurements, setMeasurements] = useState([]);
    const [openModal, setOpenModal] = useState(false);
    const handleOpenModal = () => setOpenModal(true);
    const handleCloseModal = () => setOpenModal(false);
    const [name, setName] = useState("");

    const onRowClick = async (name) => {
        if (!open) {
            console.log("on Roww clickkkkk ", name)
            getCalculationSummary(name).then((response) => {
                setMeasurements(response.data.elementValues)
                console.log(response.data.elementValues)

            }).catch(error => {
                console.log(error);
            })
        }
        setOpen(!open)
    };

    const calculateTotalPrice = () => {
        let sum = 0;
        measurements.map((item) => {
            sum = sum + item.equivalentPrice
        })
        console.log("summm", sum)
        return Math.round(sum * 100) / 100
    }

    function handleDeletePrice() {
        deletePrice(name).then((response) => {

            console.log(response.data)
            handleCloseModal()
            window.AbstractRangelocation.reload();

        }).catch(error => {
            console.log(error);
        })
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
                        Confirm deleting the record?
                    </Typography>

                    <Box
                        sx={{
                            display: 'flex',
                            justifyContent: 'space-around',
                            p: 1,
                            m: 1,
                            marginTop: "50px",
                        }}
                    >
                        <Button variant="contained" onClick={handleDeletePrice}>Yes</Button>
                        <Button variant="contained" onClick={handleCloseModal}>Cancel</Button>
                    </Box>

                </Box>
            </Modal>


            <TableContainer sx={{marginTop: "20px"}}>
                <TableRow sx={{'& > *': {borderBottom: 'unset'}}}>
                    <TableCell>
                        <IconButton
                            aria-label="expand row"
                            size="small"
                            onClick={() => onRowClick(row)}
                        >
                            {open ? <KeyboardArrowUpIcon/> : <KeyboardArrowDownIcon/>}
                        </IconButton>
                    </TableCell>
                    <TableCell component="th" scope="row" sx={{width: "80px"}}>
                        {row}
                    </TableCell>
                    <TableCell component="th" scope="row">
                        <DeleteForeverIcon onClick={() => {
                            handleOpenModal()
                            setName(row)
                        }}/>
                    </TableCell>
                </TableRow>
                <TableRow>
                    <TableCell style={{paddingBottom: 0, paddingTop: 0}} colSpan={4}>
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
                                                    <TableCell align="center">{measurement.equivalentWeight}</TableCell>
                                                    <TableCell
                                                        align="center">{Math.round(measurement.equivalentPrice * 100) / 100}</TableCell>
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

                            </Box>
                        </Collapse>
                    </TableCell>
                </TableRow>
            </TableContainer>
        </React.Fragment>
    );
}

function SavedResult() {
    const [resultIds, setresultIds] = useState([])

    useEffect(() => {
        getAllSavedResults().then((response) => {
            console.log(response.data)
            setresultIds(response.data)

        }).catch(error => {
            console.log(error);
        })
    }, [])

    return (
        <>

            <Table aria-label="collapsible table" size="small">
                <TableHead>
                    <TableRow>
                        <TableCell align="start">
                            <Typography variant="h5">
                                Saved Results
                            </Typography>
                        </TableCell>
                    </TableRow>
                </TableHead>
                <TableBody>
                    {resultIds.map((row) => (
                        <Row key={row} row={row}/>
                    ))}
                </TableBody>
            </Table>
        </>
    )
}

export default SavedResult