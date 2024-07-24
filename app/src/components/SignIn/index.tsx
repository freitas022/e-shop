import axios from "axios";
import { useState } from "react";
import { Alert, Button, Form, Modal } from "react-bootstrap";
import { useNavigate } from "react-router-dom";
import { useToken } from "../../contexts/AuthProvider/useToken";

function SignIn() {

    const [, setToken] = useToken();

    const [show, setShow] = useState(false);
    const handleClose = () => setShow(false);
    const handleShow = () => setShow(true);

    const [emailValue, setEmail] = useState("");
    const [passwordValue, setPassword] = useState("");
    const [error, setError] = useState(null);
    const history = useNavigate();

    const handleSubmit = async (e: React.FormEvent<HTMLFormElement>) => {
        e.preventDefault();
        await axios.post("http://localhost:8080/sign-in", {
            email: emailValue,
            password: passwordValue
        })
            .then(response => {
                const token = response.data;
                setToken(token);
                history("/");
                handleClose();
                location.reload();
            })
            .catch(error => { setError(error) });
    }

    return (
        <>
            <Button variant="primary" className="btn-sm" onClick={handleShow}>
                Sign in
            </Button>

            {error && <Alert variant="danger">{error}</Alert>}
            <Modal show={show} onHide={handleClose}>
                <Modal.Header closeButton>
                    <Modal.Title>Login now</Modal.Title>
                </Modal.Header>
                <Modal.Body>
                    <form onSubmit={handleSubmit}>
                        <Form.Group className="mb-3" controlId="email">
                            <Form.Label>Email</Form.Label>
                            <Form.Control
                                type="email"
                                placeholder="test@mail.com"
                                autoFocus
                                name="email"
                                value={emailValue}
                                onChange={(e) => setEmail(e.target.value)}
                            />
                        </Form.Group>
                        <Form.Group
                            className="mb-3"
                            controlId="password"
                        >
                            <Form.Label>Password</Form.Label>
                            <Form.Control type="password"
                                name="password"
                                value={passwordValue}
                                onChange={(e) => setPassword(e.target.value)}
                            />
                        </Form.Group>
                        <Modal.Footer>
                            <Button variant="primary" type="submit">
                                Send
                            </Button>
                        </Modal.Footer>
                    </form>
                </Modal.Body>
            </Modal>
        </>
    );
}

export default SignIn;