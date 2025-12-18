import React, { useState } from 'react';
import { Box, Drawer, AppBar, Toolbar, List, Typography, Divider, IconButton, ListItem, ListItemIcon, ListItemText, Avatar, Container } from '@mui/material';
import { Menu as MenuIcon, Dashboard as DashIcon, DirectionsBus, Cloud, Warning, Route, Settings, Notifications } from '@mui/icons-material';
import { Link, useLocation } from 'react-router-dom';

const drawerWidth = 260;

export default function DashboardLayout({ children }) {
    const [mobileOpen, setMobileOpen] = useState(false);
    const location = useLocation();

    const menuItems = [
        { text: 'Dashboard', icon: <DashIcon />, path: '/' },
        { text: 'Mobilité', icon: <DirectionsBus />, path: '/mobility' },
        { text: 'Qualité Air', icon: <Cloud />, path: '/air-quality' },
        { text: 'Urgences', icon: <Warning />, path: '/emergency' },
        { text: 'Itinéraires', icon: <Route />, path: '/zone-info' },
    ];

    const drawer = (
        <Box sx={{ height: '100%', bgcolor: '#1a2035', color: 'white' }}>
            <Toolbar sx={{ display: 'flex', justifyContent: 'center', py: 2 }}>
                <Typography variant="h6" sx={{ fontWeight: 800, color: '#4dabf5' }}>SMART CITY</Typography>
            </Toolbar>
            <Divider sx={{ bgcolor: 'rgba(255,255,255,0.1)' }} />
            <List>
                {menuItems.map((item) => (
                    <ListItem
                        button
                        key={item.text}
                        component={Link}
                        to={item.path}
                        selected={location.pathname === item.path}
                        sx={{
                            margin: '8px 16px',
                            borderRadius: '8px',
                            '&.Mui-selected': { bgcolor: '#4dabf5', '&:hover': { bgcolor: '#2196f3' } },
                            '&:hover': { bgcolor: 'rgba(255,255,255,0.08)' }
                        }}
                    >
                        <ListItemIcon sx={{ color: 'inherit', minWidth: 40 }}>{item.icon}</ListItemIcon>
                        <ListItemText primary={item.text} />
                    </ListItem>
                ))}
            </List>
        </Box>
    );

    return (
        <Box sx={{ display: 'flex', bgcolor: '#f4f7fe', minHeight: '100vh' }}>
            <AppBar position="fixed" sx={{ width: { sm: `calc(100% - ${drawerWidth}px)` }, ml: { sm: `${drawerWidth}px` }, bgcolor: 'white', color: '#333', boxShadow: 'none', borderBottom: '1px solid #e0e0e0' }}>
                <Toolbar>
                    <IconButton color="inherit" edge="start" onClick={() => setMobileOpen(!mobileOpen)} sx={{ mr: 2, display: { sm: 'none' } }}><MenuIcon /></IconButton>
                    <Typography variant="h6" noWrap sx={{ flexGrow: 1, fontWeight: 600 }}>Vue d'ensemble</Typography>
                    <IconButton><Notifications /></IconButton>
                    <Avatar sx={{ ml: 2, bgcolor: '#4dabf5' }}>SK</Avatar>
                </Toolbar>
            </AppBar>

            <Box component="nav" sx={{ width: { sm: drawerWidth }, flexShrink: { sm: 0 } }}>
                <Drawer variant="temporary" open={mobileOpen} onClose={() => setMobileOpen(false)} ModalProps={{ keepMounted: true }} sx={{ display: { xs: 'block', sm: 'none' }, '& .MuiDrawer-paper': { boxSizing: 'border-box', width: drawerWidth } }}>{drawer}</Drawer>
                <Drawer variant="permanent" sx={{ display: { xs: 'none', sm: 'block' }, '& .MuiDrawer-paper': { boxSizing: 'border-box', width: drawerWidth, border: 'none' } }} open>{drawer}</Drawer>
            </Box>

            <Box component="main" sx={{ flexGrow: 1, p: 3, mt: 8, width: { sm: `calc(100% - ${drawerWidth}px)` } }}>
                <Container maxWidth="xl">{children}</Container>
            </Box>
        </Box>
    );
}