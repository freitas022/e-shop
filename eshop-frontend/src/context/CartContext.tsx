import React, { createContext, useContext, useEffect, useState } from "react";
import * as cartService from "../services/cart-service";
import { CartDto } from "../types/Cart";

interface CartContextType {
  cart: CartDto | null;
  cartItemCount: number;
  fetchCart: () => void;
}

const CartContext = createContext<CartContextType>({
  cart: null,
  cartItemCount: 0,
  fetchCart: () => { },
});

export const useCart = () => useContext(CartContext);

export const CartProvider: React.FC<{ children: React.ReactNode }> = ({ children }) => {
  const [cart, setCart] = useState<CartDto | null>(null);

  const fetchCart = () => {
    cartService
      .getCartByUser()
      .then((resp) => {
        setTimeout(() => {
          setCart(resp.data)
        }, 300);
      })
      .catch(() => setCart(null));
  };

  const cartItemCount = cart?.items?.reduce((total, item) => total + item.quantity, 0) || 0;

  useEffect(() => {
    fetchCart();
  }, []);

  return (
    <CartContext.Provider value={{ cart, cartItemCount, fetchCart }}>
      {children}
    </CartContext.Provider>
  );
};