import { useState } from "react";
import { useNavigate} from "react-router-dom";
import { Link as MuiLink } from "@mui/material";
import { Link as RouterLink } from "react-router-dom";
import {
  Container,
  TextField,
  Button,
  Box,
  Typography,
  Paper,
} from "@mui/material";
import api from "../api/apiClient";

export default function Login() {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [error, setError] = useState("");
  const navigate = useNavigate();

const handleLogin = async (e: React.FormEvent) => {
  e.preventDefault();
  try {
    const res = await api.post("/api/auth/login", { email, password });
    console.log("Login response:", res.data);
    localStorage.setItem("token", res.data.data.token);
    navigate("/profile");
  } catch (err: any) {
    console.error("Login error:", err.response?.data);
    setError(err.response?.data?.message || "Login failed");
  }
};


  return (
    <Container maxWidth="sm">
      <Box mt={10}>
        <Paper elevation={6} sx={{ p: 4, borderRadius: 3 }}>
          <Typography variant="h5" align="center" gutterBottom>
            Enterprise Order Platform
          </Typography>
          <Typography variant="subtitle1" align="center" mb={3}>
            Login
          </Typography>

          <form onSubmit={handleLogin}>
            <TextField
              fullWidth
              label="Email"
              margin="normal"
              value={email}
              onChange={(e) => setEmail(e.target.value)}
            />
            <TextField
              fullWidth
              label="Password"
              type="password"
              margin="normal"
              value={password}
              onChange={(e) => setPassword(e.target.value)}
            />

            {error && (
              <Typography color="error" mt={1}>
                {error}
              </Typography>
            )}

            <Button
              fullWidth
              variant="contained"
              color="primary"
              sx={{ mt: 2, py: 1.2 }}
              type="submit"
            >
              Login
            </Button>

            <Typography align="center" mt={2}>
              New user?{" "}
              <MuiLink
                component={RouterLink}
                to="/register"
                sx={{ color: "primary.main", fontWeight: 500, textDecoration: "none" }}
              >
                Register
              </MuiLink>
            </Typography>
          </form>
        </Paper>
      </Box>
    </Container>
  );
}
