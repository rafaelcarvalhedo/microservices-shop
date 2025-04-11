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

function Pagamentos() {
  const [pagamentos, setPagamentos] = useState([]);
  const [novoPagamento, setNovoPagamento] = useState({
    pedidoId: '',
    valor: '',
    metodoPagamento: '',
  });

  useEffect(() => {
    carregarPagamentos();
  }, []);

  const carregarPagamentos = async () => {
    try {
      const response = await axios.get('http://localhost:8081/api/pagamentos');
      setPagamentos(response.data);
    } catch (error) {
      console.error('Erro ao carregar pagamentos:', error);
    }
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      await axios.post('http://localhost:8081/api/pagamentos', novoPagamento);
      setNovoPagamento({ pedidoId: '', valor: '', metodoPagamento: '' });
      carregarPagamentos();
    } catch (error) {
      console.error('Erro ao criar pagamento:', error);
    }
  };

  return (
    <Box>
      <Typography variant="h4" gutterBottom>
        Pagamentos
      </Typography>

      <Card sx={{ mb: 4 }}>
        <CardContent>
          <Typography variant="h6" gutterBottom>
            Novo Pagamento
          </Typography>
          <form onSubmit={handleSubmit}>
            <Grid container spacing={2}>
              <Grid item xs={12} sm={4}>
                <TextField
                  fullWidth
                  label="ID do Pedido"
                  value={novoPagamento.pedidoId}
                  onChange={(e) =>
                    setNovoPagamento({ ...novoPagamento, pedidoId: e.target.value })
                  }
                />
              </Grid>
              <Grid item xs={12} sm={4}>
                <TextField
                  fullWidth
                  label="Valor"
                  type="number"
                  value={novoPagamento.valor}
                  onChange={(e) =>
                    setNovoPagamento({ ...novoPagamento, valor: e.target.value })
                  }
                />
              </Grid>
              <Grid item xs={12} sm={4}>
                <TextField
                  fullWidth
                  label="Método de Pagamento"
                  value={novoPagamento.metodoPagamento}
                  onChange={(e) =>
                    setNovoPagamento({
                      ...novoPagamento,
                      metodoPagamento: e.target.value,
                    })
                  }
                />
              </Grid>
              <Grid item xs={12}>
                <Button type="submit" variant="contained" color="primary">
                  Criar Pagamento
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
              <TableCell>Valor</TableCell>
              <TableCell>Método</TableCell>
              <TableCell>Status</TableCell>
            </TableRow>
          </TableHead>
          <TableBody>
            {pagamentos.map((pagamento) => (
              <TableRow key={pagamento.id}>
                <TableCell>{pagamento.id}</TableCell>
                <TableCell>{pagamento.pedidoId}</TableCell>
                <TableCell>R$ {pagamento.valor}</TableCell>
                <TableCell>{pagamento.metodoPagamento}</TableCell>
                <TableCell>{pagamento.status}</TableCell>
              </TableRow>
            ))}
          </TableBody>
        </Table>
      </TableContainer>
    </Box>
  );
}

export default Pagamentos; 