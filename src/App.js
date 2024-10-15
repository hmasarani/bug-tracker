import React from 'react';
import { Route, BrowserRouter as Router, Routes } from 'react-router-dom'; // Added Routes import
import Home from './Components/Home';
import Bug from './Components/Bug'; // Make sure the path is correct

function App() {
  return (
    <Router>
      <Routes>
        <Route path="/" element={<Home />} />
        <Route path="/bugs" element={<Bug />} />
      </Routes>
    </Router>
  );
}

export default App;
