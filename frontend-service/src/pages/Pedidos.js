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

function Pedidos() {
  const [pedidos, setPedidos] = useState([]);
  const [novoPedido, setNovoPedido] = useState({
    clienteId: '',
    valorTotal: '',
  });

  useEffect(() => {
    carregarPedidos();
  }, []);

  const carregarPedidos = async () => {
    try {
      const response = await axios.get('http://localhost:8080/api/pedidos');
      setPedidos(response.data);
    } catch (error) {
      console.error('Erro ao carregar pedidos:', error);
    }
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      await axios.post('http://localhost:8080/api/pedidos', novoPedido);
      setNovoPedido({ clienteId: '', valorTotal: '' });
      carregarPedidos();
    } catch (error) {
      console.error('Erro ao criar pedido:', error);
    }
  };

  return (
    <Box>
      <Typography variant="h4" gutterBottom>
        Pedidos
      </Typography>

      <Card sx={{ mb: 4 }}>
        <CardContent>
          <Typography variant="h6" gutterBottom>
            Novo Pedido
          </Typography>
          <form onSubmit={handleSubmit}>
            <Grid container spacing={2}>
              <Grid item xs={12} sm={6}>
                <TextField
                  fullWidth
                  label="ID do Cliente"
                  value={novoPedido.clienteId}
                  onChange={(e) =>
                    setNovoPedido({ ...novoPedido, clienteId: e.target.value })
                  }
                />
              </Grid>
              <Grid item xs={12} sm={6}>
                <TextField
                  fullWidth
                  label="Valor Total"
                  type="number"
                  value={novoPedido.valorTotal}
                  onChange={(e) =>
                    setNovoPedido({ ...novoPedido, valorTotal: e.target.value })
                  }
                />
              </Grid>
              <Grid item xs={12}>
                <Button type="submit" variant="contained" color="primary">
                  Criar Pedido
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
              <TableCell>Cliente ID</TableCell>
              <TableCell>Valor Total</TableCell>
              <TableCell>Status</TableCell>
              <TableCell>Status Pagamento</TableCell>
              <TableCell>Status Envio</TableCell>
            </TableRow>
          </TableHead>
          <TableBody>
            {pedidos.map((pedido) => (
              <TableRow key={pedido.id}>
                <TableCell>{pedido.id}</TableCell>
                <TableCell>{pedido.clienteId}</TableCell>
                <TableCell>R$ {pedido.valorTotal}</TableCell>
                <TableCell>{pedido.status}</TableCell>
                <TableCell>{pedido.statusPagamento}</TableCell>
                <TableCell>{pedido.statusEnvio}</TableCell>
              </TableRow>
            ))}
          </TableBody>
        </Table>
      </TableContainer>
    </Box>
  );
}

export default Pedidos; 