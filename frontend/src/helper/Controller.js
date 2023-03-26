import React from 'react';
import axios from "./axios";

export const getAllMetalPrices = async () => {
    return await axios.get("/prices");
};

export const getListOfAllExchangeRates = async () => {
    return await axios.get("/getListOfAllExchangeRates");
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
    return await axios.get("/getAnalysisSummaryList",
        {
            headers: HEADERS
        }
    );
}

export const getMeasurement = async (uuid) => {
    const HEADERS = {
        "authentication": localStorage.getItem("token")
    }
    return await axios.get(`/getMeasurement?uuid=${uuid}`,
        {
            headers: HEADERS
        }
    );
}

export const createResultPrice = async (resultPrice) => {
    return await axios.post("/createResultPrice", resultPrice);
};

export const getAllSavedResults = async () => {
    return await axios.get("/getAllSavedResults");
};

export const getCalculationSummary = async (name) => {
    const params = new URLSearchParams([['name', name]]);
    return await axios.get("/getCalculationSummary", {params});

};

export const deletePrice = async (name) => {
    const params = new URLSearchParams([['name', name]]);
    return await axios.delete("/deletePrice", {params});
};