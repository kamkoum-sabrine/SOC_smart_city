import React, { useEffect, useState } from "react";
import { getLines, createLine, updateLine, deleteLine } from "../api/mobilityRestClient";
import { Button, TextField, Typography, List, ListItem, ListItemText, IconButton } from "@mui/material";
import DeleteIcon from "@mui/icons-material/Delete";

export default function MobilityPage() {
    const [lines, setLines] = useState([]);
    const [newLine, setNewLine] = useState({ code: "", nom: "", horairesJson: "[]" });

    const fetchLines = async () => {
        const data = await getLines();
        setLines(data);
    };

    useEffect(() => { fetchLines(); }, []);

    const handleCreate = async () => {
        await createLine(newLine);
        setNewLine({ code: "", nom: "", horairesJson: "[]" });
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
                <TextField label="Code" value={newLine.code} onChange={e => setNewLine({ ...newLine, code: e.target.value })} />
                <TextField label="Nom" value={newLine.nom} onChange={e => setNewLine({ ...newLine, nom: e.target.value })} />
                <Button variant="contained" onClick={handleCreate}>Ajouter</Button>
            </div>
            <List>
                {lines.map(line => (
                    <ListItem key={line.id} secondaryAction={
                        <IconButton edge="end" onClick={() => handleDelete(line.id)}>
                            <DeleteIcon />
                        </IconButton>
                    }>
                        <ListItemText primary={`${line.code} - ${line.nom}`} secondary={line.horairesJson} />
                    </ListItem>
                ))}
            </List>
        </div>
    );
}
