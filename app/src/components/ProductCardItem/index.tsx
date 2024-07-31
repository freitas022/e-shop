import axios from "axios";
import { useEffect, useState } from "react";
import { Col, Container, Image, Row } from "react-bootstrap";
import { useParams } from "react-router-dom";
import Product from "../../model/Product";

function ProductCardItem() {
    const param = useParams();
    const [product, setProduct] = useState<Product>();

    useEffect(() => {
        axios.get(`http://localhost:8080/products/${Number(param.id)}`)
            .then(response => {
                setProduct(response.data);
            });
    }, [param.id]);

    return (
        <section className="mt-5">
            <Container>
                {product && (
                    <Row>
                        <Col xs={12} md={4} lg={4} className="order-1 order-md-2 mb-3">
                            <div className="rounded px-2 py-2" style={{ backgroundColor: "#f1f1f1" }}>
                                <div>
                                    <h5>{product.name}</h5>
                                    <p>{product.description}</p>
                                    <h6>{product.price}</h6>
                                    {/* car component here */}
                                </div>
                            </div>
                        </Col>
                        <Col xs={12} md={8} lg={8} className="order-2 order-md-1 mb-3">
                            <Image
                                rounded
                                fluid
                                src="https://pixelprowess.com/i/shop.jpg"
                                alt={product.name}
                            />
                        </Col>
                        <Col xs={12} md={8} lg={8} className="order-3">
                            <h4 className="my-2">Description</h4>
                            <div className="p-2 my-2 rounded" style={{ backgroundColor: "#f1f1f1" }}>
                                <p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Morbi eget nulla eget nunc ornare hendrerit. Maecenas quis tincidunt tortor. Praesent ultricies sollicitudin lectus ut tempus. Vestibulum placerat feugiat purus, iaculis consequat lorem. Vivamus diam sem, semper vel laoreet ut, iaculis non augue. Sed mollis massa eros, vitae sagittis eros aliquet nec. Vestibulum dolor turpis, pharetra eget volutpat eget, interdum a elit.</p>
                                <p>Sed bibendum elit consequat, maximus sapien eu, aliquet libero. Sed eleifend risus vulputate, tincidunt velit non, elementum mauris. Aliquam in velit dui. Praesent quis leo vel elit imperdiet interdum at eget nulla. Donec vehicula nulla velit, eget bibendum enim interdum at. Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas. Quisque egestas non diam id dignissim. Curabitur mollis fringilla tortor, a vestibulum eros sodales vel. Morbi ex sem, eleifend non molestie vitae, scelerisque ac lorem. Suspendisse fringilla enim erat, quis porttitor erat sodales id. Phasellus imperdiet ut erat ut feugiat. Sed purus purus, sagittis in tellus non, commodo aliquet lacus.</p>
                                <p>Donec tempor feugiat mauris nec viverra. Morbi molestie fringilla dolor. Cras urna massa, placerat a nibh vel, pharetra vulputate leo. Ut viverra risus finibus est lobortis aliquet eget eget magna. Morbi accumsan, eros id tincidunt suscipit, ligula est mattis felis, quis facilisis ligula ex nec lorem. Ut tristique enim sapien, vitae lobortis nibh consectetur in. In imperdiet imperdiet tellus vitae imperdiet. Proin id ligula nec lacus pellentesque porta malesuada sit amet turpis. Nulla laoreet, nibh vel sagittis venenatis, magna tellus feugiat ligula, non ultricies neque nibh ut odio. Fusce cursus at eros sit amet egestas.</p>
                                <p>Suspendisse vestibulum scelerisque dolor at molestie. Sed mattis egestas leo. Sed id faucibus ligula. Quisque ac quam tortor. Aenean at euismod leo. Phasellus placerat venenatis lacinia. Aliquam a lobortis dolor. Nulla vitae congue ipsum. Duis eu elit eros. Aliquam sit amet massa diam.</p>
                            </div>
                        </Col>
                    </Row>
                )}
            </Container>
        </section>
    );
}

export default ProductCardItem;
