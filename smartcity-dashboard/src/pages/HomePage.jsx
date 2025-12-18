import React from "react";
import { useNavigate } from "react-router-dom";
import { Card, CardContent, Typography, Grid, Button } from "@mui/material";

export default function HomePage() {
    const navigate = useNavigate();

    const services = [
        {
            name: "Mobilité Intelligente",
            description: "Gérer les lignes de bus et consulter les horaires.",
            route: "/mobility",
            color: "#1976d2"
        },
        {
            name: "Qualité de l'Air",
            description: "Consulter les données environnementales par zone.",
            route: "/air-quality",
            color: "#388e3c"
        },
        {
            name: "Urgences & Santé Publique",
            description: "Créer et suivre les alertes d'urgence.",
            route: "/emergency",
            color: "#d32f2f"
        },
        {
            name: "Zones Personnalisables",
            description: "Consulter les infos complètes par zone (GraphQL).",
            route: "/zone-info",
            color: "#f57c00"
        }
    ];

    return (
        <div style={{ padding: "2rem" }}>
            <Typography variant="h3" gutterBottom>Smart City Dashboard</Typography>
            <Grid container spacing={4}>
                {services.map((service) => (
                    <Grid item xs={12} sm={6} md={3} key={service.name}>
                        <Card sx={{ backgroundColor: service.color, color: "white" }}>
                            <CardContent>
                                <Typography variant="h5" gutterBottom>{service.name}</Typography>
                                <Typography variant="body2">{service.description}</Typography>
                                <Button
                                    variant="contained"
                                    sx={{ mt: 2, backgroundColor: "white", color: service.color }}
                                    onClick={() => navigate(service.route)}
                                >
                                    Accéder
                                </Button>
                            </CardContent>
                        </Card>
                    </Grid>
                ))}
            </Grid>
        </div>
    );
}
