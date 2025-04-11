import React, { useState, useEffect } from 'react';
import {
  Box,
  Button,
  Card,
  CardContent,
  Typography,
  Grid,
  TextField,
  Table,
  TableBody,
  TableCell,
  TableContainer,
  TableHead,
  TableRow,
  Paper,
} from '@mui/material';
import axios from 'axios';

function Envios() {
  const [envios, setEnvios] = useState([]);
  const [novoEnvio, setNovoEnvio] = useState({
    pedidoId: '',
    enderecoEntrega: '',
  });

  useEffect(() => {
    carregarEnvios();
  }, []);

  const carregarEnvios = async () => {
    try {
      const response = await axios.get('http://localhost:8082/api/envios');
      setEnvios(response.data);
    } catch (error) {
      console.error('Erro ao carregar envios:', error);
    }
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      await axios.post('http://localhost:8082/api/envios', novoEnvio);
      setNovoEnvio({ pedidoId: '', enderecoEntrega: '' });
      carregarEnvios();
    } catch (error) {
      console.error('Erro ao criar envio:', error);
    }
  };

  return (
    <Box>
      <Typography variant="h4" gutterBottom>
        Envios
      </Typography>

      <Card sx={{ mb: 4 }}>
        <CardContent>
          <Typography variant="h6" gutterBottom>
            Novo Envio
          </Typography>
          <form onSubmit={handleSubmit}>
            <Grid container spacing={2}>
              <Grid item xs={12} sm={6}>
                <TextField
                  fullWidth
                  label="ID do Pedido"
                  value={novoEnvio.pedidoId}
                  onChange={(e) =>
                    setNovoEnvio({ ...novoEnvio, pedidoId: e.target.value })
                  }
                />
              </Grid>
              <Grid item xs={12} sm={6}>
                <TextField
                  fullWidth
                  label="Endereço de Entrega"
                  value={novoEnvio.enderecoEntrega}
                  onChange={(e) =>
                    setNovoEnvio({
                      ...novoEnvio,
                      enderecoEntrega: e.target.value,
                    })
                  }
                />
              </Grid>
              <Grid item xs={12}>
                <Button type="submit" variant="contained" color="primary">
                  Criar Envio
                </Button>
              </Grid>
            </Grid>
          </form>
        </CardContent>
      </Card>

      <TableContainer component={Paper}>
        <Table>
          <TableHead>
            <TableRow>
              <TableCell>ID</TableCell>
              <TableCell>Pedido ID</TableCell>
              <TableCell>Endereço</TableCell>
              <TableCell>Status</TableCell>
              <TableCell>Código Rastreamento</TableCell>
            </TableRow>
          </TableHead>
          <TableBody>
            {envios.map((envio) => (
              <TableRow key={envio.id}>
                <TableCell>{envio.id}</TableCell>
                <TableCell>{envio.pedidoId}</TableCell>
                <TableCell>{envio.enderecoEntrega}</TableCell>
                <TableCell>{envio.status}</TableCell>
                <TableCell>{envio.codigoRastreamento}</TableCell>
              </TableRow>
            ))}
          </TableBody>
        </Table>
      </TableContainer>
    </Box>
  );
}

export default Envios; 