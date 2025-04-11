import React from 'react';
import { Link as RouterLink } from 'react-router-dom';
import {
  AppBar,
  Toolbar,
  Typography,
  Button,
  Box,
} from '@mui/material';
import {
  ShoppingCart as PedidosIcon,
  Payment as PagamentosIcon,
  LocalShipping as EnviosIcon,
} from '@mui/icons-material';

function Navbar() {
  return (
    <AppBar position="static">
      <Toolbar>
        <Typography variant="h6" component="div" sx={{ flexGrow: 1 }}>
          Sistema de Pedidos
        </Typography>
        <Box>
          <Button
            color="inherit"
            component={RouterLink}
            to="/pedidos"
            startIcon={<PedidosIcon />}
          >
            Pedidos
          </Button>
          <Button
            color="inherit"
            component={RouterLink}
            to="/pagamentos"
            startIcon={<PagamentosIcon />}
          >
            Pagamentos
          </Button>
          <Button
            color="inherit"
            component={RouterLink}
            to="/envios"
            startIcon={<EnviosIcon />}
          >
            Envios
          </Button>
        </Box>
      </Toolbar>
    </AppBar>
  );
}

export default Navbar; 