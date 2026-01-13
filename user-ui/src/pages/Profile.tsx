import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import {
  AppBar,
  Toolbar,
  Typography,
  Button,
  Container,
  Card,
  CardContent,
  Box,
  Avatar,
  Table,
  TableBody,
  TableCell,
  TableHead,
  TableRow,
  Paper,
} from "@mui/material";
import api from "../api/apiClient";

export default function Profile() {
  const [profile, setProfile] = useState<any>(null);
  const [orders, setOrders] = useState<any[]>([]);
  const navigate = useNavigate();

useEffect(() => {
   api.get("/api/profile")
     .then((res) => {
       const userProfile = res.data.data;
       setProfile(userProfile);           // { id, email, orders }
       setOrders(userProfile.orders);     // array of orders
     })
     .catch(() => {
       localStorage.removeItem("token");
       navigate("/login");
     });
}, []);

  const handleLogout = () => {
    localStorage.removeItem("token");
    navigate("/login");
  };

  if (!profile) return <div>Loading...</div>;

  return (
    <>
      <AppBar position="static">
        <Toolbar>
          <Typography variant="h6" sx={{ flexGrow: 1 }}>
            Enterprise Order Platform
          </Typography>
          <Button color="inherit" onClick={handleLogout}>
            Logout
          </Button>
        </Toolbar>
      </AppBar>

      <Container maxWidth="md">
        <Box mt={5}>
          <Card sx={{ borderRadius: 3, mb: 4 }}>
            <CardContent sx={{ textAlign: "center" }}>
              <Avatar sx={{ width: 80, height: 80, mx: "auto", mb: 2 }}>
                {profile.name?.charAt(0)}
              </Avatar>

              <Typography variant="h5">{profile.name}</Typography>
              <Typography color="text.secondary">{profile.email}</Typography>
              <Typography variant="body2" mt={1}>
                <b>User ID:</b> {profile.id}
              </Typography>
            </CardContent>
          </Card>

          <Paper sx={{ borderRadius: 3, p: 2 }}>
            <Typography variant="h6" gutterBottom>
              My Orders
            </Typography>

            <Table>
              <TableHead>
                <TableRow>
                  <TableCell><b>Order ID</b></TableCell>
                  <TableCell><b>Status</b></TableCell>
                  <TableCell><b>Created At</b></TableCell>
                </TableRow>
              </TableHead>
              <TableBody>
                {orders.map((order) => (
                  <TableRow key={order.orderId}>
                    <TableCell>{order.orderId}</TableCell>
                    <TableCell>{order.status}</TableCell>
                    <TableCell>
                      {new Date(order.createdAt).toLocaleString()}
                    </TableCell>
                  </TableRow>
                ))}
              </TableBody>
            </Table>

            {orders.length === 0 && (
              <Typography align="center" mt={2}>
                No orders found.
              </Typography>
            )}
          </Paper>
        </Box>
      </Container>
    </>
  );
}
