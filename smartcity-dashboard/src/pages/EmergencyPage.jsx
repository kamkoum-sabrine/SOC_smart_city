import React, { useState } from "react";
import { createAlert } from "../api/emergencyGrpcClient";
import { Button, TextField, Typography } from "@mui/material";

export default function EmergencyPage() {
    const [alertData, setAlertData] = useState({
        type: "", priority: "HIGH", description: "", latitude: "", longitude: "", reporterName: "", reporterPhone: ""
    });
    const [response, setResponse] = useState(null);

    const handleCreate = async () => {
        try {
            const res = await createAlert({
                ...alertData,
                latitude: parseFloat(alertData.latitude),
                longitude: parseFloat(alertData.longitude)
            });
            setResponse(res);
        } catch (err) {
            console.error(err);
            setResponse(null);
        }
    };

    return (
        <div style={{ padding: "2rem" }}>
            <Typography variant="h4">Créer une alerte d'urgence</Typography>
            <div style={{ display: "flex", flexDirection: "column", gap: "1rem", margin: "1rem 0" }}>
                <TextField label="Type" value={alertData.type} onChange={e => setAlertData({ ...alertData, type: e.target.value })} />
                <TextField label="Priorité" value={alertData.priority} onChange={e => setAlertData({ ...alertData, priority: e.target.value })} />
                <TextField label="Description" value={alertData.description} onChange={e => setAlertData({ ...alertData, description: e.target.value })} />
                <TextField label="Latitude" value={alertData.latitude} onChange={e => setAlertData({ ...alertData, latitude: e.target.value })} />
                <TextField label="Longitude" value={alertData.longitude} onChange={e => setAlertData({ ...alertData, longitude: e.target.value })} />
                <TextField label="Nom du reporter" value={alertData.reporterName} onChange={e => setAlertData({ ...alertData, reporterName: e.target.value })} />
                <TextField label="Téléphone du reporter" value={alertData.reporterPhone} onChange={e => setAlertData({ ...alertData, reporterPhone: e.target.value })} />
                <Button variant="contained" onClick={handleCreate}>Créer</Button>
            </div>
            {response && (
                <div>
                    <Typography>⚠️ Alerte créée ID : {response.alertId}</Typography>
                    <Typography>Type : {response.type}</Typography>
                    <Typography>Priorité : {response.priority}</Typography>
                </div>
            )}
        </div>
    );
}
