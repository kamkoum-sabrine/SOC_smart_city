import React, { useState } from "react";
import { getZoneInfo, planRoute } from "../api/graphqlClient";
import { Button, TextField, Typography } from "@mui/material";

export default function ZoneInfoPage() {
    const [zone, setZone] = useState("");
    const [info, setInfo] = useState(null);

    const handleFetch = async () => {
        try {
            const res = await getZoneInfo(zone);
            setInfo(res.zoneInfo);
        } catch (err) {
            console.error(err);
            setInfo(null);
        }
    };

    return (
        <div style={{ padding: "2rem" }}>
            <Typography variant="h4">Informations de la zone</Typography>
            <div style={{ display: "flex", gap: "1rem", margin: "1rem 0" }}>
                <TextField label="Zone" value={zone} onChange={e => setZone(e.target.value)} />
                <Button variant="contained" onClick={handleFetch}>Consulter</Button>
            </div>
            {info && (
                <div>
                    <Typography>Zone : {info.zone}</Typography>
                    <Typography>AQI : {info.aqi}</Typography>
                    <Typography>Polluant principal : {info.mainPollutant}</Typography>
                    <Typography>Transport disponible : {info.transportAvailable ? "Oui" : "Non"}</Typography>
                    <Typography>Lignes de transport : {info.transportLines.join(", ")}</Typography>
                    <Typography>Urgences actives : {info.activeEmergencies}</Typography>
                </div>
            )}
        </div>
    );
}
