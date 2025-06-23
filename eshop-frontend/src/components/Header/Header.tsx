import { useState } from "react";
import { Container, Modal, Nav, Navbar, NavDropdown } from "react-bootstrap";
import { BsPerson } from "react-icons/bs";
import { Link } from "react-router-dom";
import { useAuth } from "../../context/AuthContext";
import CartIcon from "../Cart/CartIcon";
import SignIn from "../Form/SignIn";

function Header() {

    const [showModal, setShowModal] = useState(false);
    const { isAuthenticated, setAuthenticated } = useAuth();

    function handleLogout() {
        setAuthenticated(false);
    }
    return (
        <>
            <Navbar bg="dark" variant="dark" className="sticky-top">
                <Container>
                    <Navbar.Brand href="/">E-shop</Navbar.Brand>
                    <Nav className="ml-auto">
                        {!isAuthenticated ?
                            (
                                <Nav.Link onClick={() => setShowModal(true)}>
                                    Entrar
                                </Nav.Link>
                            ) : (
                                <div className="d-flex align-items-center">
                                    <CartIcon />
                                    <NavDropdown
                                        id="nav-dropdown-dark-example"
                                        title={<BsPerson className="text-white" size={25} />}
                                        menuVariant="dark"
                                    >
                                        <NavDropdown.Item>
                                            <Link to="/profile" className="text-white">Perfil</Link>
                                        </NavDropdown.Item>
                                        <NavDropdown.Item >
                                            <Link to="/purchases" className="text-white">Meus pedidos</Link>
                                        </NavDropdown.Item>
                                        <NavDropdown.Divider />
                                        <NavDropdown.Item onClick={handleLogout}>
                                            Sair
                                        </NavDropdown.Item>
                                    </NavDropdown>
                                </div>
                            )}
                    </Nav>
                </Container>
            </Navbar>

            <Modal
                className="py-2" show={showModal} onHide={() => setShowModal(false)}>
                <Modal.Header closeButton>
                    <Modal.Title className="text-dark fs-5 text-center">Entrar</Modal.Title>
                </Modal.Header>
                <SignIn onSuccess={() => setShowModal(false)} />
            </Modal>
        </>
    );
}

export default Header;