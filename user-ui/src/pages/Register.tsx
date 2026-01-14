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

export default function Register() {
  const [name, setName] = useState("");
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [error, setError] = useState("");
  const navigate = useNavigate();

  const handleRegister = async (e: React.FormEvent) => {
    e.preventDefault();
    try {
      await api.post("/api/auth/register", { name, email, password });
      navigate("/login");
    } catch (err: any) {
      setError(err.response?.data?.message || "Registration failed");
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
            Create Account
          </Typography>

          <form onSubmit={handleRegister}>
            <TextField
              fullWidth
              label="Name"
              margin="normal"
              value={name}
              onChange={(e) => setName(e.target.value)}
            />

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
              sx={{ mt: 2, py: 1.2 }}
              type="submit"
            >
              Register
            </Button>

           <Typography align="center" mt={2}>
             Already have an account?{" "}
             <MuiLink
               component={RouterLink}
               to="/login"
               sx={{ color: "primary.main", fontWeight: 500, textDecoration: "none" }}
             >
               Login
             </MuiLink>
           </Typography>

          </form>
        </Paper>
      </Box>
    </Container>
  );
}
