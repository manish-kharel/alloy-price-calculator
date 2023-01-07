import {useEffect, useState} from 'react';
import {useNavigate} from 'react-router-dom';

const PrivateLayout = (props) => {
    const [checking, setChecking] = useState(true);
    const navigate = useNavigate();

    useEffect(() => {
        // const checkAuth = async () => {
        //     try {
        //         await axios.get("/v1/users/checkAuth", {
        //             withCredentials: true,
        //         });
        //         setChecking(false);
        //     } catch (error) {
        //         navigate('/');
        //     }
        // };
        // checkAuth();

        const token = localStorage.getItem("token")
        setChecking(false)
        if (!token) navigate("/login")
    }, [navigate]);

    if (checking) {
        return <div> spinner </div>;
    }

    return <div>{props.children}</div>;
};

export default PrivateLayout;