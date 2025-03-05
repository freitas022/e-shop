import { Card, Stack } from "react-bootstrap";
import { ProductDto } from "../../../types/ProductDto";

type Props = {
    product: ProductDto;
}

function ProductCard({ product }: Props) {

    return (

        <Stack direction="vertical" gap={3} key={product.id}>

            <Card style={{ boxShadow: "0 0 10px rgba(0,0,0,0.1)" }}>
                <Card.Img
                    src="https://png.pngtree.com/png-clipart/20241125/original/pngtree-robot-photo-png-image_17308416.png"
                    alt={product.name}
                />
                <Card.Body>
                    <Card.Title className="fw-bold">
                        {product.name}
                    </Card.Title>
                    <Card.Text style={{fontSize: "0.95em"}}>
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
