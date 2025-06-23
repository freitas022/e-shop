import { useState } from "react";
import { Alert, Button, Form, Spinner } from "react-bootstrap";
import { useNavigate } from "react-router-dom";
import { useAuth } from "../../context/AuthContext";
import * as authService from "../../services/auth-service";

function SignIn({ onSuccess }: { onSuccess?: () => void }) {

    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState<string | null>(null);
    const { setAuthenticated } = useAuth();

    const navigate = useNavigate();

    function handleSubmit(event: React.FormEvent<HTMLFormElement>) {
        event.preventDefault();
        setError(null);
        setLoading(true);

        authService.authenticate({ email, password })
            .then(() => {
                setAuthenticated(true);
                if (onSuccess) onSuccess();
                navigate("/");
            })
            .catch(err => {
                if (err.response && err.response.status === 401) {
                    setError(err.response.data.message);
                } else {
                    setError(err.response.data.error);
                }
            })
            .finally(() => {
                setLoading(false);
            });
    }

    return (
        <Form className="w-100" onSubmit={handleSubmit} style={{ maxWidth: 400, margin: "0 auto" }}>

            {error && <Alert variant="danger">{error}</Alert>}

            <Form.Group className="my-3" controlId="signInEmail">
                <Form.Label>Email</Form.Label>
                <Form.Control
                    type="email"
                    placeholder="Digite seu email"
                    value={email}
                    onChange={(e) => setEmail(e.target.value)}
                    autoComplete="email"
                    required
                />
            </Form.Group>

            <Form.Group className="mb-3" controlId="signInPassword">
                <Form.Label>Senha</Form.Label>
                <Form.Control
                    type="password"
                    placeholder="Digite sua senha"
                    value={password}
                    onChange={(e) => setPassword(e.target.value)}
                    autoComplete="current-password"
                    required
                />
            </Form.Group>

            <Button
                variant="primary"
                type="submit"
                className="w-100 my-3"
                disabled={loading}
            >
                {loading ? <Spinner animation="border" size="sm" /> : "Entrar"}
            </Button>
        </Form>
    );
}

export default SignIn;