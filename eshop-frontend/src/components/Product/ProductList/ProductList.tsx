import { useEffect, useState } from "react";
import { Col, Container, Pagination, Row, Spinner } from "react-bootstrap";
import { Link } from "react-router-dom";
import * as productService from "../../../services/product-service";
import { PagedResponse } from "../../../types/PagedResponse";
import { ProductDto } from "../../../types/Product";
import ProductCard from "../ProductCard/ProductCard";

function ProductList() {

    const [pageData, setPageData] = useState<PagedResponse<ProductDto>>();
    const [pageNumber, setPageNumber] = useState(0);
    const [pageSize] = useState(12);
    const [loading, setLoading] = useState(false);

    useEffect(() => {
        setLoading(true);
        window.scrollTo({ top: 0, behavior: "smooth" });

        productService
            .findAll({ page: pageNumber, size: pageSize })
            .then((response) => {
                setPageData(response.data);
            })
            .finally(() => setLoading(false));
    }, [pageNumber, pageSize]);

    function handlePageChange(page: number) {
        if (
            page !== pageNumber &&
            page >= 0 &&
            page < (pageData?.totalPages ?? 1)
        ) {
            setPageNumber(page);
        }
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
                        {pageData?.content.map((product) => (
                            <Col key={product.id} className="p-2">
                                <Link to={`/products/${product.id}`}>
                                    <ProductCard product={product} />
                                </Link>
                            </Col>
                        ))}
                    </Row>

                    {pageData && pageData.totalPages > 1 && (
                        <div className="d-flex justify-content-center mt-3">
                            <Pagination>
                                <Pagination.First
                                    onClick={() => handlePageChange(0)}
                                    disabled={pageNumber === 0}
                                />
                                <Pagination.Prev
                                    onClick={() => handlePageChange(pageNumber - 1)}
                                    disabled={pageNumber === 0}
                                />
                                {[...Array(pageData.totalPages)].map((_, idx) => (
                                    <Pagination.Item
                                        key={idx}
                                        active={idx === pageNumber}
                                        onClick={() => handlePageChange(idx)}
                                    >
                                        {idx + 1}
                                    </Pagination.Item>
                                ))}
                                <Pagination.Next
                                    onClick={() => handlePageChange(pageNumber + 1)}
                                    disabled={pageNumber === pageData.totalPages - 1}
                                />
                                <Pagination.Last
                                    onClick={() => handlePageChange(pageData.totalPages - 1)}
                                    disabled={pageNumber === pageData.totalPages - 1}
                                />
                            </Pagination>
                        </div>
                    )}
                </>
            )}
        </Container>
    );
}

export default ProductList;