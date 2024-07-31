import { useContext } from "react";
import { Col, Row } from "react-bootstrap";
import { Link } from "react-router-dom";
import { ProductContext } from "../../contexts/ProductProvider";

function ProductsCard() {

    const { products } = useContext(ProductContext);

    return (
        <main>
            <section className="container">
                <Row>
                    {
                        products.map((product) => (

                            <Col xs="12" sm="6" md="4" xl="3" className="my-3" key={product.id}>
                                <Link to={`/products/${product.id}`} className="nav-link">
                                    <p className="fw-semibold">
                                        {product.name}
                                    </p>
                                    <img src="https://pixelprowess.com/i/shop.jpg" alt={product.name} className="img-fluid" />
                                    <div className="text-truncate py-2" >
                                        {product.description}
                                    </div>
                                    <h6>
                                        <span>R$</span>{product.price}
                                    </h6>
                                </Link>
                            </Col>
                        ))
                    }
                </Row>
            </section>
        </main>
    );
}

export default ProductsCard;
