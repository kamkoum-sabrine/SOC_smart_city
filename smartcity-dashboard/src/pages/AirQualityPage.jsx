import React, { useState } from "react";
import { getAirQuality } from "../api/airQualitySoapClient";
import { Button, TextField, Typography } from "@mui/material";

export default function AirQualityPage() {
    const [zone, setZone] = useState("");
    const [result, setResult] = useState(null);

    const handleFetch = async () => {
        try {
            const data = await getAirQuality(zone);
            setResult({
                zone: data.zone[0],
                aqi: parseInt(data.aQI[0]),
                pollutant: data.mainPollutant[0]
            });
        } catch (err) {
            console.error(err);
            setResult(null);
        }
    };

    return (
        <div style={{ padding: "2rem" }}>
            <Typography variant="h4">Qualité de l'air</Typography>
            <div style={{ display: "flex", gap: "1rem", margin: "1rem 0" }}>
                <TextField label="Zone" value={zone} onChange={e => setZone(e.target.value)} />
                <Button variant="contained" onClick={handleFetch}>Consulter</Button>
            </div>
            {result && (
                <div>
                    <Typography>Zone : {result.zone}</Typography>
                    <Typography>AQI : {result.aqi}</Typography>
                    <Typography>Polluant principal : {result.pollutant}</Typography>
                </div>
            )}
        </div>
    );
}
