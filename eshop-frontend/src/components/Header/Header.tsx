import { useState } from "react";
import { Container, Modal, Nav, Navbar, NavDropdown } from "react-bootstrap";
import { BsPerson } from "react-icons/bs";

function Header() {

    const [showModal, setShowModal] = useState(false);
    const isAuthenticated = useState(false);

    return (
        <>
            <Navbar bg="dark" variant="dark" className="sticky-top">
                <Container>
                    <Navbar.Brand href="/">E-shop</Navbar.Brand>
                    <Nav className="ml-auto">
                        {isAuthenticated ?
                            (
                                <Nav.Link onClick={() => setShowModal(true)}>
                                    Entrar
                                </Nav.Link>
                            ) : (
                                <NavDropdown
                                    id="nav-dropdown-dark-example"
                                    title={<BsPerson size={20} />}
                                    menuVariant="dark"
                                >
                                    <NavDropdown.Item href="#action/3.2">
                                        Meus pedidos
                                    </NavDropdown.Item>
                                    <NavDropdown.Divider />
                                    <NavDropdown.Item>
                                        Sair
                                    </NavDropdown.Item>
                                </NavDropdown>
                            )}
                    </Nav>
                </Container>
            </Navbar>

            <Modal show={showModal} onHide={() => setShowModal(false)}>
                <Modal.Header closeButton>
                    <Modal.Title>Login</Modal.Title>
                </Modal.Header>
                <Modal.Body>
                    <p>Form work! </p>
                </Modal.Body>
            </Modal>
        </>
    );
}

export default Header;