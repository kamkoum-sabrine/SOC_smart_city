// src/App.js
import React from "react";
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import Navbar from "./components/Navbar";
import AirQualityPage from "./pages/AirQualityPage";

function App() {
  return (
    <Router>
      <Navbar />
      <Routes>
        <Route path="/airquality" element={<AirQualityPage />} />
        {/* Ajoute d'autres pages CRUD pour Traffic, Sensors, etc. */}
      </Routes>
    </Router>
  );
}

export default App;
