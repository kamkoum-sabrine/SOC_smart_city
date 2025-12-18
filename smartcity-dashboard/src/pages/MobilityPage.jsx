import React, { useEffect, useState } from "react";
import { getLines, createLine, deleteLine } from "../api/mobilityRestClient";
import {
    Button,
    TextField,
    Typography,
    List,
    ListItem,
    ListItemText,
    IconButton,
    Paper
} from "@mui/material";
import DeleteIcon from "@mui/icons-material/Delete";

export default function MobilityPage() {
    const [lines, setLines] = useState([]);
    const [newLine, setNewLine] = useState({
        code: "",
        nom: "",
        horaires: "" // saisie utilisateur
    });

    const fetchLines = async () => {
        const data = await getLines();
        setLines(data);
    };

    useEffect(() => {
        fetchLines();
    }, []);

    const handleCreate = async () => {
        const horairesArray = newLine.horaires
            .split(",")
            .map(h => h.trim())
            .filter(h => h !== "");

        const payload = {
            code: newLine.code,
            nom: newLine.nom,
            horairesJson: JSON.stringify(horairesArray)
        };

        await createLine(payload);

        setNewLine({ code: "", nom: "", horaires: "" });
        fetchLines();
    };

    const handleDelete = async (id) => {
        await deleteLine(id);
        fetchLines();
    };

    return (
        <div style={{ padding: "2rem" }}>
            <Typography variant="h4">Lignes de Bus</Typography>

            <div style={{ display: "flex", gap: "1rem", margin: "1rem 0" }}>
                <TextField
                    label="Code"
                    value={newLine.code}
                    onChange={e => setNewLine({ ...newLine, code: e.target.value })}
                />

                <TextField
                    label="Nom"
                    value={newLine.nom}
                    onChange={e => setNewLine({ ...newLine, nom: e.target.value })}
                />

                <TextField
                    label="Horaires (séparés par ,)"
                    placeholder="08:00,12:00,17:00"
                    value={newLine.horaires}
                    onChange={e => setNewLine({ ...newLine, horaires: e.target.value })}
                />

                <Button variant="contained" onClick={handleCreate}>
                    Ajouter
                </Button>
            </div>

            <List>
                {lines.map(line => (
                    <Paper key={line.id} sx={{ mb: 1 }}>
                        <ListItem
                            secondaryAction={
                                <IconButton color="error" onClick={() => handleDelete(line.id)}>
                                    <DeleteIcon />
                                </IconButton>
                            }
                        >
                            <ListItemText
                                primary={`${line.code} - ${line.nom}`}
                                secondary={`Horaires : ${line.horairesJson}`}
                            />
                        </ListItem>
                    </Paper>
                ))}
            </List>

        </div>
    );
}
