import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import { CssBaseline, ThemeProvider, createTheme } from '@mui/material';
import Navbar from "./components/Navbar";
import HomePage from "./pages/HomePage";
import MobilityPage from "./pages/MobilityPage";
import AirQualityPage from "./pages/AirQualityPage";
import EmergencyPage from "./pages/EmergencyPage";
import ZoneInfoPage from "./pages/ZoneInfoPage";
import DashboardLayout from './pages/DashboardLayout';
import DashboardHome from './pages/DashboardHome';

const theme = createTheme({
  palette: {
    primary: { main: '#4dabf5' },
    background: { default: '#f4f7fe' },
  },
  typography: {
    fontFamily: "'Inter', sans-serif",
    h4: { fontWeight: 700 },
  },
  shape: { borderRadius: 12 }
});
function App() {
  return (
    <ThemeProvider theme={theme}>
      <CssBaseline />
      <Router>
        <DashboardLayout>
          <Routes>
            <Route path="/" element={<DashboardHome />} />
            <Route path="/mobility" element={<MobilityPage />} />
            <Route path="/air-quality" element={<AirQualityPage />} />
            <Route path="/emergency" element={<EmergencyPage />} />
            <Route path="/zone-info" element={<ZoneInfoPage />} />
          </Routes>
        </DashboardLayout>
      </Router>
    </ThemeProvider>
  );
}

export default App;