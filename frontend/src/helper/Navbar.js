import React from 'react';
import AppBar from '@mui/material/AppBar';
import Box from '@mui/material/Box';
import Toolbar from '@mui/material/Toolbar';
import Typography from '@mui/material/Typography';
import Button from '@mui/material/Button';
import IconButton from '@mui/material/IconButton';
import MenuIcon from '@mui/icons-material/Menu';


import Container from '@mui/material/Container';

import {useNavigate, useLocation} from "react-router-dom"

function Navbar() {
    let navigate = useNavigate()
    const withouSidebarRoutes = ["/login"];
    const {pathname} = useLocation();
    if (withouSidebarRoutes.some((item) => pathname.includes(item))) return null;

    return (
        <>
            <AppBar position="static" sx={{backgroundColor: "#ffffff", marginBottom: "20px"}}>
                <Container maxWidth="lg">
                    <Toolbar>
                        <IconButton
                            size="large"
                            edge="start"
                            aria-label="menu"
                            sx={{mr: 2}}
                            onClick={() => navigate("/")}
                        >
                            <Button> Home </Button>
                        </IconButton>
                        <Typography variant="h6" component="div" sx={{flexGrow: 1}}>
                            News
                        </Typography>
                        {!localStorage.getItem("token") ?
                            <Button color="primary" onClick={() => navigate("/login")}>Login</Button> :
                            <Typography variant="h6" component="div" sx={{color: "purple"}}>
                                User: {localStorage.getItem("username")}
                            </Typography>}


                        {localStorage.getItem("token") &&
                            <Button variant="contained" sx={{marginLeft: "20px"}} onClick={() => {
                                localStorage.removeItem("token");
                                localStorage.removeItem("username")
                                navigate("/")
                            }}>Logout</Button>}
                    </Toolbar>
                </Container>
            </AppBar>
        </>
    )
}

export default Navbar