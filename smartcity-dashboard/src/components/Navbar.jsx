// src/components/Navbar.jsx
import React from "react";
import { AppBar, Toolbar, Typography, Button } from "@mui/material";

export default function Navbar() {
    return (
        <AppBar position="static">
            <Toolbar>
                <Typography variant="h6" style={{ flexGrow: 1 }}>
                    Smart City Dashboard
                </Typography>
                <Button color="inherit" href="/airquality">Air Quality</Button>
                <Button color="inherit" href="/traffic">Traffic</Button>
            </Toolbar>
        </AppBar>
    );
}
