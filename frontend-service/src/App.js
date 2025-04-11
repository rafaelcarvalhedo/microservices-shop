import React from 'react';
import { Routes, Route } from 'react-router-dom';
import { Box } from '@mui/material';
import Navbar from './components/Navbar';
import Pedidos from './pages/Pedidos';
import Pagamentos from './pages/Pagamentos';
import Envios from './pages/Envios';

function App() {
  return (
    <Box sx={{ display: 'flex', flexDirection: 'column', minHeight: '100vh' }}>
      <Navbar />
      <Box component="main" sx={{ flexGrow: 1, p: 3 }}>
        <Routes>
          <Route path="/" element={<Pedidos />} />
          <Route path="/pedidos" element={<Pedidos />} />
          <Route path="/pagamentos" element={<Pagamentos />} />
          <Route path="/envios" element={<Envios />} />
        </Routes>
      </Box>
    </Box>
  );
}

export default App; 