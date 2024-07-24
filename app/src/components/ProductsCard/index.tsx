import { useContext } from "react";
import { Col, Row } from "react-bootstrap";
import { ProductContext } from "../../contexts/ProductProvider";

function ProductsCard() {

    const { products } = useContext(ProductContext);

    return (
        <section className="container">
            <Row>
                {
                    products.map((product) => (

                        <Col xs="12" sm="6" md="4" xl="3" className="my-3" key={product.id}>
                            <p className="fw-semibold">
                                {product.name}
                            </p>
                            <img src="https://pixelprowess.com/i/shop.jpg" alt="Order image" className="img-fluid" />
                            <div className="text-truncate py-2" >
                                {product.description}
                            </div>
                            <span style={{ fontSize: "1.1rem" }}>
                                R${product.price}
                            </span>
                        </Col>
                    ))
                }
            </Row>
        </section>
    );
}

export default ProductsCard;
