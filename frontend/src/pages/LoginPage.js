import * as React from 'react';
import {useEffect, useState} from 'react';
import Button from '@mui/material/Button';
import CssBaseline from '@mui/material/CssBaseline';
import TextField from '@mui/material/TextField';
import Box from '@mui/material/Box';
import Typography from '@mui/material/Typography';
import Container from '@mui/material/Container';
import {createTheme, ThemeProvider} from '@mui/material/styles';
import {getAuthentication} from "../helper/Controller";
import {useNavigate} from "react-router-dom";

const theme = createTheme();


export default function SignIn() {

    const [auth, setAuth] = useState(false)

    const [loginErrorMsg, setLoginErrorMsg] = useState("")

    let navigate = useNavigate()

    useEffect(() => {
        const token = localStorage.getItem("token")
        if (token) navigate("/getAnalysisList")
    }, [auth])

    const handleSubmit = async (event) => {
        event.preventDefault();
        const data = new FormData(event.currentTarget);
        try {
            const authentication = await getAuthentication(
                data.get("username"), data.get("password")
            )
            localStorage.setItem('token', authentication.data.token);
            localStorage.setItem('username', data.get("username"));
            setAuth(true)
        } catch (exception) {
            if (exception.response.status === 401) setLoginErrorMsg("Username or Password invalid")
            console.log(exception.response.status)
        }
    };

    return (
        <ThemeProvider theme={theme}>
            <Container component="main" maxWidth="xs">
                <CssBaseline/>
                <Box
                    sx={{
                        marginTop: 8,
                        display: 'flex',
                        flexDirection: 'column',
                        alignItems: 'center',
                    }}
                >
                    <Typography component="h1" variant="h5">
                        Login
                    </Typography>
                    <Box component="form" onSubmit={handleSubmit} noValidate sx={{mt: 1}}>
                        <TextField
                            margin="normal"
                            required
                            fullWidth
                            id="username"
                            label="Username"
                            name="username"
                            autoComplete="username"
                            autoFocus
                        />
                        <TextField
                            margin="normal"
                            required
                            fullWidth
                            name="password"
                            label="Password"
                            type="password"
                            id="password"
                            autoComplete="current-password"
                        />
                        <Typography variant="h6" sx={{color: "red"}}>
                            {loginErrorMsg}
                        </Typography>
                        <Button
                            type="submit"
                            fullWidth
                            variant="contained"
                            sx={{mt: 3, mb: 2}}
                        >
                            Sign In
                        </Button>
                    </Box>
                </Box>
            </Container>
        </ThemeProvider>
    );
}