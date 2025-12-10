import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import Navbar from "./components/Navbar";
import HomePage from "./pages/HomePage";
import MobilityPage from "./pages/MobilityPage";
import AirQualityPage from "./pages/AirQualityPage";
import EmergencyPage from "./pages/EmergencyPage";
import ZoneInfoPage from "./pages/ZoneInfoPage";

function App() {
  return (
    <Router>
      {/* <Navbar /> */}
      <Routes>
        <Route path="/" element={<HomePage />} />
        <Route path="/mobility" element={<MobilityPage />} />
        <Route path="/air-quality" element={<AirQualityPage />} />
        <Route path="/emergency" element={<EmergencyPage />} />
        <Route path="/zone-info" element={<ZoneInfoPage />} />
      </Routes>
    </Router>
  );
}

export default App;
