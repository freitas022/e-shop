import { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
import { ProductDto } from '../../../types/ProductDto';
import * as productService from '../../../services/product-service';
import { Col, Container, Row, Image, Form, Button } from 'react-bootstrap';
import { BsCart } from 'react-icons/bs';

function ProductDetails() {
    const params = useParams();
    const [product, setProduct] = useState<ProductDto>();
    const [quantity, setQuantity] = useState(1);

    useEffect(() => {
        productService.findById(Number(params.productId))
            .then(response => {
                setProduct(response.data);
            });
    }, [params.productId]);

    return (
        <section>
            <Container className="my-5 d-flex flex-column border rounded">
                {product && (
                    <>
                        <Row className="p-5">
                            {/* Coluna da Imagem e Miniaturas */}
                            <Col md={6} className="d-flex flex-column align-items-baseline">
                                <Image
                                    src="https://png.pngtree.com/png-clipart/20241125/original/pngtree-robot-photo-png-image_17308416.png"
                                    alt={product.name}
                                    fluid
                                    className="border rounded mb-3"
                                />
                            </Col>

                            {/* Coluna de Detalhes */}
                            <Col md="6">
                                <h2>{product.name}</h2>
                                <h2 className="text-success">R$ {product.price.toFixed(2)}</h2>

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

                                    <Button variant="dark" size="lg" className="w-100 mb-2">Comprar Agora</Button>
                                    <Button variant="dark" size="lg" className="w-100">
                                        <BsCart className="me-2" /> Adicionar ao Carrinho
                                    </Button>
                                </Form>
                            </Col>
                        </Row>
                        <Row>
                            <Col md="auto" className="d-flex flex-column justify-content-center p-3">
                                <h4 className="mt-2 mb-3">Descrição do produto</h4>
                                <p>
                                    Lorem ipsum dolor sit amet, consectetur adipiscing elit. Praesent orci libero, convallis et suscipit ac, suscipit a ante. Aenean eget dictum eros. Nam feugiat varius metus, ut imperdiet nisl porta nec. Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas. Cras a mi posuere, tempor massa sed, accumsan lacus. Nullam eleifend, magna nec consectetur viverra, neque urna feugiat enim, auctor auctor felis augue quis enim. Suspendisse pulvinar ullamcorper leo eu auctor.
                                </p>
                                <p>
                                    Suspendisse eleifend odio nec varius feugiat. Quisque euismod accumsan tellus sed tincidunt. Curabitur hendrerit tempor maximus. Nullam vitae nulla consequat, porta purus quis, dignissim risus. In ligula ex, iaculis at tincidunt et, porttitor a metus. Praesent tristique ut orci in aliquet. Class aptent taciti sociosqu ad litora torquent per conubia nostra, per inceptos himenaeos. Nulla quis dui leo. Maecenas tristique mauris sed augue cursus blandit sed at dui. Nullam in tempus ante, eu hendrerit nisl.
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
