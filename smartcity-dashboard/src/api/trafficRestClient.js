// src/api/trafficRestClient.js
const API_BASE = "http://localhost:8085/api";

export async function getTrafficStatus(area) {
    const res = await fetch(`${API_BASE}/traffic/${area}`);
    if (!res.ok) throw new Error("Failed to fetch traffic status");
    return res.json();
}
