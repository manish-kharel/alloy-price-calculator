import './App.css';
import {BrowserRouter as Router, Routes, Route} from "react-router-dom";
import Homepage from "./pages/Homepage";
import LoginPage from "./pages/LoginPage";
import AnalysisSummariesPage from "./pages/AnalysisSummariesPage";
import PrivateLayout from "./helper/hoc/PrivateLayout";
import ManualInputPage from "./pages/ManualInputPage";
import Navbar from "./helper/Navbar";


function App() {
    return (
        <div className="App">
            <Router>
                <Navbar/>
                <Routes>
                    <Route path="/" element={<Homepage/>}></Route>
                    <Route path="/login" element={<LoginPage/>}></Route>
                    <Route path="/manualInput" element={<ManualInputPage/>}></Route>
                    <Route path="/getAnalysisList" element={
                        <PrivateLayout> <AnalysisSummariesPage/> </PrivateLayout>
                    }
                    />
                </Routes>
            </Router>
        </div>
    );
}

export default App;
