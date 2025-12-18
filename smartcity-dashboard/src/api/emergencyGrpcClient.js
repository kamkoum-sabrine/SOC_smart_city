import axios from "axios";

export const createAlert = async (data) => {
    try {
        const response = await axios.post("http://gateway:8085/api/emergency/alerts", {
            type: data.type,
            priority: data.priority,
            description: data.description,
            latitude: data.latitude,
            longitude: data.longitude,
            zone: data.zone,
            address: data.address,
            reporterName: data.reporterName,
            reporterPhone: data.reporterPhone
        });
        return response.data;
    } catch (err) {
        throw err;
    }
};
