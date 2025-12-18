// src/api/mobilityRestClient.js
const API_BASE = "http://localhost:8085/gateway/mobility/lines";

export async function getLines() {
    const res = await fetch(API_BASE);
    if (!res.ok) throw new Error("Failed to fetch bus lines");
    return res.json();
}

export async function createLine(line) {
    const res = await fetch(API_BASE, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(line),
    });
    return res.json();
}

export async function updateLine(id, line) {
    const res = await fetch(`${API_BASE}/${id}`, {
        method: "PUT",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(line),
    });
    return res.json();
}

export async function deleteLine(id) {
    await fetch(`${API_BASE}/${id}`, { method: "DELETE" });
}
