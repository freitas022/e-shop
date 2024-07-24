import { Button, Container, Dropdown, Form, FormControl, InputGroup, Nav, Navbar, NavbarBrand, NavItem, NavLink } from 'react-bootstrap';
import { useUser } from '../../contexts/AuthProvider/useUser';
import SignIn from '../SignIn';

function Header() {

    const user = useUser();
    
    return (
        <header style={{ height: "66px" }}>
            <Navbar expand="lg" fixed="top" className="bg-body-tertiary">
                <Container>
                    <NavbarBrand href="/" className="fw-bolder nav-link">E-shop</NavbarBrand>
                    <Navbar.Toggle aria-controls="navbar-nav" />
                    <Navbar.Collapse id="navbar-nav" className="justify-content-between">
                        <div className="col-sm-6 offset-lg-2">
                            <Form>
                                <InputGroup size="sm" className='my-3'>
                                    <FormControl
                                        type="search"
                                        placeholder="Search for products and more..."
                                    />
                                    <Button variant="outline-secondary" className='border border-secondary'>Search</Button>
                                </InputGroup>
                            </Form>
                        </div>
                        <Nav className="ml-auto">
                            {!user ? (
                                <SignIn />
                            ) : (
                                <Dropdown as={NavItem}>
                                    <Dropdown.Toggle as={NavLink} key={user!.id}>
                                        Signed in as: {user!.name}
                                    </Dropdown.Toggle>
                                    <Dropdown.Menu>
                                        <Dropdown.Item href="#">My purchases</Dropdown.Item>
                                        <Dropdown.Divider />
                                        <Dropdown.Item href="#">Logout</Dropdown.Item>
                                    </Dropdown.Menu>
                                </Dropdown>
                            )}
                        </Nav>
                    </Navbar.Collapse>
                </Container>
            </Navbar>
        </header>
    );
}

export default Header;