import React, { useState } from "react";
import { planRoute } from "../api/graphqlClient";
import { Button, TextField, Typography } from "@mui/material";

export default function PlanRoutePage() {
    const [from, setFrom] = useState("");
    const [to, setTo] = useState("");
    const [prefer, setPrefer] = useState("");
    const [routeInfo, setRouteInfo] = useState(null);

    const handleFetch = async () => {
        try {
            const res = await planRoute(from, to, prefer);
            console.log("Résultat reçu :", res);
            setRouteInfo(res); // res contient from, to, recommendedMode, reason, availableLines
        } catch (err) {
            console.error(err);
            setRouteInfo(null);
        }
    };

    return (
        <div style={{ padding: "2rem" }}>
            <Typography variant="h4">Planification d'itinéraire</Typography>

            <div style={{ display: "flex", gap: "1rem", margin: "1rem 0" }}>
                <TextField
                    label="Départ"
                    value={from}
                    onChange={e => setFrom(e.target.value)}
                />
                <TextField
                    label="Arrivée"
                    value={to}
                    onChange={e => setTo(e.target.value)}
                />
                <TextField
                    label="Préférence (optionnel)"
                    value={prefer}
                    onChange={e => setPrefer(e.target.value)}
                />
                <Button variant="contained" onClick={handleFetch}>
                    Consulter
                </Button>
            </div>

            {routeInfo && (
                <div style={{ marginTop: "1rem" }}>
                    <Typography><strong>Départ :</strong> {routeInfo.from}</Typography>
                    <Typography><strong>Arrivée :</strong> {routeInfo.to}</Typography>
                    <Typography><strong>Mode recommandé :</strong> {routeInfo.recommendedMode}</Typography>
                    <Typography><strong>Raison :</strong> {routeInfo.reason}</Typography>
                    <Typography>
                        <strong>Lignes disponibles :</strong> {routeInfo.availableLines.join(", ")}
                    </Typography>
                </div>
            )}
        </div>
    );
}
