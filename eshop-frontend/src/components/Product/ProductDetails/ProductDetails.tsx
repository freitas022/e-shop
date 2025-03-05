import { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
import { ProductDto } from '../../../types/ProductDto';
import * as productService from '../../../services/product-service';
import { Col, Container, Row } from 'react-bootstrap';

function ProductDetails() {

    const params = useParams();
    const [product, setProduct] = useState<ProductDto>();

    useEffect(() => {
        productService.findById(Number(params.productId))
            .then(response => {
                setProduct(response.data);
            })
    }, [params.productId]);

    return (
        <Container>
            <Row>
                <Col>
                    <div>{product?.name}</div>
                </Col>
            </Row>
        </Container>
    );
}
export default ProductDetails;