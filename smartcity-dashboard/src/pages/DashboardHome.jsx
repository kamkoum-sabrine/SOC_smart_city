import React from 'react';
import { Grid, Paper, Typography, Box, Card, CardContent } from '@mui/material';
import { LineChart, Line, XAxis, YAxis, CartesianGrid, Tooltip, ResponsiveContainer, BarChart, Bar } from 'recharts';
import { TrendingUp, People, WarningAmber, Power, Cloud } from '@mui/icons-material';
import {
    Drawer, AppBar, Toolbar, List,
    Divider, IconButton, ListItem, ListItemIcon,
    ListItemText, Avatar, Container
} from '@mui/material';

const mockData = [
    { name: '08:00', aqi: 45, traffic: 120 },
    { name: '10:00', aqi: 52, traffic: 450 },
    { name: '12:00', aqi: 48, traffic: 380 },
    { name: '14:00', aqi: 61, traffic: 310 },
    { name: '16:00', aqi: 55, traffic: 520 },
    { name: '18:00', aqi: 67, traffic: 680 },
];

const StatCard = ({ title, value, icon, color }) => (
    <Card sx={{ height: '100%', borderRadius: 4, boxShadow: '0 4px 20px 0 rgba(0,0,0,0.05)' }}>
        <CardContent>
            <Box sx={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center' }}>
                <Box>
                    <Typography color="textSecondary" variant="overline" sx={{ fontWeight: 'bold' }}>{title}</Typography>
                    <Typography variant="h4" sx={{ fontWeight: 700 }}>{value}</Typography>
                </Box>
                <Avatar sx={{ bgcolor: `${color}15`, color: color, width: 56, height: 56 }}>{icon}</Avatar>
            </Box>
        </CardContent>
    </Card>
);

export default function DashboardHome() {
    return (
        <Box>
            <Grid container spacing={3} sx={{ mb: 4 }}>
                <Grid item xs={12} sm={6} md={3}><StatCard title="Utilisateurs" value="12,450" icon={<People />} color="#4dabf5" /></Grid>
                <Grid item xs={12} sm={6} md={3}><StatCard title="AQI Moyen" value="54" icon={<Cloud />} color="#4caf50" /></Grid>
                <Grid item xs={12} sm={6} md={3}><StatCard title="Alertes Actives" value="3" icon={<WarningAmber />} color="#f44336" /></Grid>
                <Grid item xs={12} sm={6} md={3}><StatCard title="Consommation" value="89%" icon={<Power />} color="#ff9800" /></Grid>
            </Grid>

            <Grid container spacing={3}>
                <Grid item xs={12} md={8}>
                    <Paper sx={{ p: 3, borderRadius: 4 }}>
                        <Typography variant="h6" gutterBottom>Évolution du Trafic & Qualité de l'Air</Typography>
                        <Box sx={{ height: 300 }}>
                            <ResponsiveContainer width="100%" height="100%">
                                <LineChart data={mockData}>
                                    <CartesianGrid strokeDasharray="3 3" vertical={false} />
                                    <XAxis dataKey="name" />
                                    <YAxis />
                                    <Tooltip />
                                    <Line type="monotone" dataKey="traffic" stroke="#4dabf5" strokeWidth={3} dot={{ r: 6 }} />
                                    <Line type="monotone" dataKey="aqi" stroke="#4caf50" strokeWidth={3} />
                                </LineChart>
                            </ResponsiveContainer>
                        </Box>
                    </Paper>
                </Grid>
                <Grid item xs={12} md={4}>
                    <Paper sx={{ p: 3, borderRadius: 4, height: '100%' }}>
                        <Typography variant="h6" gutterBottom>Répartition Urgences</Typography>
                        <Box sx={{ height: 300 }}>
                            <ResponsiveContainer width="100%" height="100%">
                                <BarChart data={mockData}>
                                    <Bar dataKey="aqi" fill="#ff9800" radius={[4, 4, 0, 0]} />
                                    <XAxis dataKey="name" hide />
                                    <Tooltip />
                                </BarChart>
                            </ResponsiveContainer>
                        </Box>
                    </Paper>
                </Grid>
            </Grid>
        </Box>
    );
}