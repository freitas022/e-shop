import { Card, Stack } from "react-bootstrap";
import { ProductDto } from "../../../types/Product";
import { BsBoxSeam } from "react-icons/bs";

type Props = {
    product: ProductDto;
}

function ProductCard({ product }: Props) {

    return (

        <Stack direction="vertical" gap={3} key={product.id}>

            <Card style={{ boxShadow: "0 0 10px rgba(0,0,0,0.1)" }}>
                <div style={{ display: "flex", justifyContent: "center", alignItems: "center", height: 120 }}>
                    <BsBoxSeam size={48} color="#adb5bd" />
                </div>
                <Card.Body>
                    <Card.Title className="fw-bold">
                        {product.name}
                    </Card.Title>
                    <Card.Text style={{ fontSize: "0.95em" }}>
                        {product.description}
                    </Card.Text>
                    <Card.Text style={{ fontSize: "1.2em", fontWeight: "bold", color: "#28a745" }}>
                        {new Intl.NumberFormat('pt-BR', { style: 'currency', currency: 'BRL' }).format(product.price)}
                    </Card.Text>
                </Card.Body>
            </Card>
        </Stack>
    );
}

export default ProductCard;
