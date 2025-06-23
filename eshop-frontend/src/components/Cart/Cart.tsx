import { useState } from "react";
import { Alert, Badge, Button, ButtonGroup, Card, Col, Row, Spinner } from "react-bootstrap";
import { BsBoxSeam, BsCartCheck, BsDash, BsPlus, BsTrash } from "react-icons/bs";
import { useCart } from "../../context/CartContext";
import * as cartService from '../../services/cart-service';

function Cart() {

  const [loading] = useState(false);
  const [error, setError] = useState<string | null>(null);
  const [updating, setUpdating] = useState<number | null>(null); // id do produto sendo atualizado
  const { cart, fetchCart } = useCart();


  function handleUpdateQuantity(productId: number, newQuantity: number) {
    setUpdating(productId);
    cartService
      .updateItem({ productId, quantity: newQuantity })
      .then(() => fetchCart())
      .finally(() => setUpdating(null));
  }

  function handleRemoveItem(productId: number) {
    setUpdating(productId);
    cartService
      .removeFromCart({ productId })
      .then(() => fetchCart())
      .catch((err) => {
        setError(err.response.data.message)
      })
      .finally(() => setUpdating(null));
  }

  function handleClearCart() {
    cartService
      .clearCart()
      .then(() => fetchCart())
      .finally(() => setUpdating(null));
  }

  if (loading)
    return (
      <div className="text-center py-5">
        <Spinner animation="border" role="status" variant="primary" />
      </div>
    );

  if (error)
    return (
      <Alert variant="danger" className="my-4 text-center">
        {error}
      </Alert>
    );

  if (!cart || !cart.items || cart.items.length === 0)
    return (
      <div className="text-center text-muted py-5">
        <BsCartCheck size={48} className="mb-3" />
        <div>Carrinho vazio.</div>
      </div>
    );

  return (
    <div className="container my-5 h-auto">
      <div className="d-flex flex-column gap-4">
        {cart.items.map((item, idx) => (
          <Card key={item.product?.id ?? idx} className="mb-3 shadow-sm">
            <Row className="g-0 align-items-center">
              <Col md={3} xs={12}>
                {item.product?.imgUrl ? (
                  <Card.Img
                    src={item.product.imgUrl}
                    alt={item.product.name}
                    className="img-fluid rounded-start"
                    style={{ objectFit: "cover", height: 160, width: "100%" }}
                  />
                ) : (
                  <div
                    className="d-flex align-items-center justify-content-center bg-light"
                    style={{ height: 160, width: "100%" }}
                  >
                    <BsBoxSeam size={48} color="#adb5bd" />
                  </div>
                )}
              </Col>
              <Col md={9} xs={12}>
                <Card.Body>
                  <div className="d-flex justify-content-between align-items-center flex-wrap">
                    <div>
                      <Card.Title className="mb-1">{item.product?.name ?? "Produto"}</Card.Title>
                      <Card.Text className="mb-2 text-muted" style={{ fontSize: 14 }}>
                        {item.product?.description}
                      </Card.Text>
                      <div className="position-absolute top-0 py-2">
                        {item.product?.categories && item.product.categories.length > 0 && (
                          <div>
                            {item.product.categories.map((cat, i) => (
                              <Badge key={cat.id ?? i} bg="secondary" className="me-1">
                                {cat.name}
                              </Badge>
                            ))}
                          </div>
                        )}
                      </div>
                    </div>
                    <div className="text-end">
                      <div>
                        <Badge bg="light" text="dark" className="mb-1">
                          Preço unitário R$ {" "}
                          {item.product?.price !== undefined
                            ? item.product.price.toFixed(2)
                            : "--"}
                        </Badge>
                      </div>
                      <div className="d-flex align-items-center justify-content-end gap-2 mt-2">
                        <ButtonGroup>
                          <Button
                            variant="outline-secondary"
                            size="sm"
                            disabled={updating === item.product.id}
                            onClick={() =>
                              handleUpdateQuantity(
                                item.product.id,
                                item.quantity - 1
                              )
                            }
                          >
                            <BsDash />
                          </Button>
                          <span className="px-2 fw-bold" style={{ minWidth: 32, textAlign: "center" }}>
                            {item.quantity ?? "--"}
                          </span>
                          <Button
                            variant="outline-secondary"
                            size="sm"
                            disabled={updating === item.product.id}
                            onClick={() =>
                              handleUpdateQuantity(
                                item.product.id,
                                item.quantity + 1
                              )
                            }
                          >
                            <BsPlus />
                          </Button>
                        </ButtonGroup>
                        <Button
                          variant="outline-danger"
                          size="sm"
                          disabled={updating === item.product.id}
                          onClick={() => handleRemoveItem(item.product.id)}
                          className="ms-2"
                          title="Remover item"
                        >
                          <BsTrash />
                        </Button>
                      </div>
                      <div className="mt-2">
                        <span className="fw-bold text-success" style={{ fontSize: 18 }}>
                          Subtotal: R${" "}
                          {item.subTotal !== undefined
                            ? item.subTotal.toFixed(2)
                            : "--"}
                        </span>
                      </div>
                    </div>
                  </div>
                </Card.Body>
              </Col>
            </Row>
          </Card>
        ))}
      </div>
      <div className="d-flex flex-column align-items-end mt-4">
        <div className="mb-2" style={{ fontSize: 22 }}>
          <strong>
            Total:{" "}
            <span className="text-primary">
              R$ {cart.total !== undefined ? cart.total.toFixed(2) : "--"}
            </span>
          </strong>
        </div>
        <div className="d-flex flex-row">
          <Button variant="success" className="shadow me-2">
            Finalizar compra
          </Button>
          <Button variant="danger" className="shadow"
            onClick={handleClearCart} disabled={loading || updating !== null}>
            Limpar carrinho
          </Button>
        </div>
      </div>
    </div>
  );
}

export default Cart;
