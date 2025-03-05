import { useEffect, useState } from "react";
import { Col, Container, Pagination, Row, Spinner } from "react-bootstrap";
import { Link } from "react-router-dom";
import * as productService from "../../../services/product-service";
import { ProductDto } from "../../../types/ProductDto";
import ProductCard from "../ProductCard/ProductCard";

function ProductList() {
    
    const [products, setProducts] = useState<ProductDto[]>([]);
    const [pageNumber, setPageNumber] = useState(0);
    const [pageSize] = useState(12);
    const [loading, setLoading] = useState(false);
    const [hasMorePages, setHasMorePages] = useState(true);

    useEffect(() => {
        if (loading) return;

        setLoading(true);
        window.scrollTo({ top: 0, behavior: 'smooth' });

        setTimeout(() => {
            productService.findAll(pageNumber, pageSize)
                .then(response => {
                    setProducts(response.data);
                    setHasMorePages(response.data.length === pageSize);
                    setLoading(false);
                })
                .catch(() => {
                    setLoading(false);
                });
        }, 1500);

    }, [pageNumber, pageSize]);

    function handlePageChange(page: number) {
        setPageNumber(page);
    }

    return (
        <Container className="position-relative min-vh-100">
            {loading ? (
                <div className="position-absolute top-50 start-50 translate-middle">
                    <Spinner animation="border" />
                </div>
            ) : (
                <>
                    <Row sm={2} md={3} lg={4}>
                        {products.map((product) => (
                            <Col key={product.id} className="p-2">
                                <Link to={`/products/${product.id}`}>
                                    <ProductCard product={product} />
                                </Link>
                            </Col>
                        ))}
                    </Row>

                    <div className="d-flex justify-content-center mt-3">
                        <Pagination>
                            <Pagination.First onClick={() => handlePageChange(0)} disabled={pageNumber === 0} />
                            <Pagination.Prev onClick={() => handlePageChange(pageNumber - 1)} disabled={pageNumber === 0} />
                            <Pagination.Item active>{pageNumber + 1}</Pagination.Item>
                            <Pagination.Next onClick={() => handlePageChange(pageNumber + 1)} disabled={!hasMorePages} />
                        </Pagination>
                    </div>
                </>
            )}
        </Container>
    );
}

export default ProductList;