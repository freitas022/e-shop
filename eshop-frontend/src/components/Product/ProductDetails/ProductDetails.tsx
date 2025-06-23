import { useEffect, useState } from 'react';
import { Alert, Button, Col, Container, Form, Row } from 'react-bootstrap';
import { BsBoxSeam, BsCart } from 'react-icons/bs';
import { useParams } from 'react-router-dom';
import { useCart } from '../../../context/CartContext';
import * as cartService from '../../../services/cart-service';
import * as productService from '../../../services/product-service';
import { ProductDto } from '../../../types/Product';

function ProductDetails() {
    const params = useParams();
    const [product, setProduct] = useState<ProductDto>();
    const [quantity, setQuantity] = useState(1);
    const [loading, setLoading] = useState(false);
    const [feedback, setFeedback] = useState<{ type: "success" | "danger", message: string } | null>(null);
    const { fetchCart } = useCart();

    useEffect(() => {
        productService.findById(Number(params.productId))
            .then(response => {
                setProduct(response.data);
            });
    }, [params.productId]);

    function handleAddToCart() {
        if (!product) return;
        setLoading(true);
        setFeedback(null);

        cartService.addToCart({ productId: product.id, quantity })
            .then(() => {
                setFeedback({ type: "success", message: "Produto adicionado ao carrinho!" });
            })
            .then(() => fetchCart())
            .catch((err) => {
                setFeedback({
                    type: "danger",
                    message:
                        err.response.data.message
                });
            })
            .finally(() => setLoading(false));
    }

    return (
        <section>
            <Container className="my-5 d-flex flex-column border rounded">
                {product && (
                    <>
                        <Row className="p-5">
                            {/* Coluna da Imagem e Miniaturas */}
                            <Col md={6}>

                                <div
                                    className="d-flex align-items-center justify-content-center bg-light"
                                    style={{ height: "100%", width: "100%" }}
                                >
                                    <BsBoxSeam size={48} color="#adb5bd" />
                                </div>
                            </Col>

                            {/* Coluna de Detalhes */}
                            <Col md="6">
                                <h2>{product.name}</h2>
                                <h2 className="text-success">R$ {product.price.toFixed(2)}</h2>

                                {feedback && (
                                    <Alert variant={feedback.type} className="my-2">
                                        {feedback.message}
                                    </Alert>
                                )}

                                <Form>
                                    <Form.Group controlId="quantity" className="mb-3 d-flex align-items-center">
                                        <Form.Label className="me-2 mb-0">Quantidade</Form.Label>
                                        <Form.Select
                                            value={quantity}
                                            onChange={(e) => setQuantity(Number(e.target.value))}
                                            className="w-auto"
                                        >
                                            {[...Array(5)].map((_, i) => (
                                                <option key={i + 1} value={i + 1}>{i + 1}</option>
                                            ))}
                                        </Form.Select>
                                    </Form.Group>

                                    <Button variant="dark" size="lg" className="w-100 mb-2" disabled={loading}>
                                        Comprar Agora
                                    </Button>
                                    <Button
                                        variant="primary"
                                        size="lg"
                                        className="w-100"
                                        disabled={loading}
                                        onClick={handleAddToCart}
                                    >
                                        <BsCart className="me-2" />
                                        {loading ? "Adicionando..." : "Adicionar ao Carrinho"}
                                    </Button>
                                </Form>
                            </Col>
                        </Row>
                        <Row>
                            <Col md="auto" className="d-flex flex-column justify-content-center p-3">
                                <h4 className="mt-2 mb-3">Descrição do produto</h4>
                                <p>
                                    {product.description}
                                </p>
                            </Col>
                        </Row>
                    </>
                )}
            </Container>
        </section>
    );
}

export default ProductDetails;
