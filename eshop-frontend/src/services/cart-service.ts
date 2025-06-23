import axios from 'axios';
import { CartDto } from '../types/Cart';
import { BACKEND_URL } from '../util/request';

export function addToCart(request: { productId: number, quantity: number }) {
  return axios.post<CartDto>(
    `${BACKEND_URL}/cart/add`,
    request,
    { withCredentials: true }
  );
}

export function removeFromCart(request: { productId: number }) {
  return axios.delete<CartDto>(
    `${BACKEND_URL}/cart/remove`,
    {
      withCredentials: true,
      data: request
    }
  );
}

export function updateItem(request: { productId: number, quantity: number }) {
  return axios.put<CartDto>(
    `${BACKEND_URL}/cart/update-item`,
    request,
    { withCredentials: true }
  );
}

export function getCartByUser() {
  return axios.get<CartDto>(
    `${BACKEND_URL}/cart/my-cart`,
    { withCredentials: true }
  );
}

export async function clearCart() {
  return axios.delete<void>(
    `${BACKEND_URL}/cart/clear`,
    { withCredentials: true }
  );
}