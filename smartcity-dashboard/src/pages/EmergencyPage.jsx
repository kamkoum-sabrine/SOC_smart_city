import React, { useState } from "react";
import { Button, TextField, Typography, MenuItem } from "@mui/material";
import axios from "axios";

const EMERGENCY_TYPES = [
    "ACCIDENT",
    "FIRE",
    "MEDICAL",
    "CRIME",
    "NATURAL_DISASTER",
    "OTHER"
];

const PRIORITIES = [
    "LOW",
    "MEDIUM",
    "HIGH",
    "CRITICAL"
];

export default function EmergencyPage() {
    const [alertData, setAlertData] = useState({
        type: "FIRE",
        priority: "HIGH",
        description: "",
        latitude: "",
        longitude: "",
        zone: "",
        address: "",
        reporterName: "",
        reporterPhone: ""
    });

    const [response, setResponse] = useState(null);

    const handleCreate = async () => {
        try {
            const body = {
                ...alertData,
                latitude: parseFloat(alertData.latitude),
                longitude: parseFloat(alertData.longitude)
            };

            const res = await axios.post(
                "http://localhost:8085/api/emergency/alerts",
                body
            );

            setResponse(res.data);
        } catch (err) {
            console.error(err);
            setResponse(null);
        }
    };

    return (
        <div style={{ padding: "2rem" }}>
            <Typography variant="h4">Créer une alerte d'urgence</Typography>

            <div style={{ display: "flex", flexDirection: "column", gap: "1rem", margin: "1rem 0" }}>
                <TextField
                    select
                    label="Type d'urgence"
                    value={alertData.type}
                    onChange={e => setAlertData({ ...alertData, type: e.target.value })}
                >
                    {EMERGENCY_TYPES.map(type => (
                        <MenuItem key={type} value={type}>{type}</MenuItem>
                    ))}
                </TextField>

                <TextField
                    select
                    label="Priorité"
                    value={alertData.priority}
                    onChange={e => setAlertData({ ...alertData, priority: e.target.value })}
                >
                    {PRIORITIES.map(p => (
                        <MenuItem key={p} value={p}>{p}</MenuItem>
                    ))}
                </TextField>

                <TextField
                    label="Description"
                    value={alertData.description}
                    onChange={e => setAlertData({ ...alertData, description: e.target.value })}
                />
                <TextField
                    label="Latitude"
                    value={alertData.latitude}
                    onChange={e => setAlertData({ ...alertData, latitude: e.target.value })}
                />
                <TextField
                    label="Longitude"
                    value={alertData.longitude}
                    onChange={e => setAlertData({ ...alertData, longitude: e.target.value })}
                />
                <TextField
                    label="Zone"
                    value={alertData.zone}
                    onChange={e => setAlertData({ ...alertData, zone: e.target.value })}
                />
                <TextField
                    label="Adresse"
                    value={alertData.address}
                    onChange={e => setAlertData({ ...alertData, address: e.target.value })}
                />
                <TextField
                    label="Nom du reporter"
                    value={alertData.reporterName}
                    onChange={e => setAlertData({ ...alertData, reporterName: e.target.value })}
                />
                <TextField
                    label="Téléphone du reporter"
                    value={alertData.reporterPhone}
                    onChange={e => setAlertData({ ...alertData, reporterPhone: e.target.value })}
                />

                <Button variant="contained" onClick={handleCreate}>Créer l’alerte</Button>
            </div>

            {response && (
                <div>
                    <Typography>🚨 Alerte créée !</Typography>
                    <pre>{JSON.stringify(response, null, 2)}</pre>
                </div>
            )}
        </div>
    );
}
