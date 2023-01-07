import React from 'react';
import axios from "./axios";

export const getAllMetalPrices = async () => {
    const priceList = await axios.get("/prices");
    return priceList
};

export const getAuthentication = async (username, password) => {
    return await axios.post("/getAuthentication", {
        username,
        password,
    });
}

export const getAnalysisSummaryList = async () => {
    const HEADERS = {
        "authentication": localStorage.getItem("token")
    }
    const summaryList = await axios.get("/getAnalysisSummaryList",
        {
            headers: HEADERS
        }
    );
    return summaryList
}

export const getMeasurement = async (uuid) => {
    const HEADERS = {
        "authentication": localStorage.getItem("token")
    }
    const measurement = await axios.get(`/getMeasurement?uuid=${uuid}`,
        {
            headers: HEADERS
        }
    );
    return measurement
}

