// src/components/Cart/CartIcon.tsx
import React from 'react';
import { Link } from 'react-router-dom';
import { BsCart } from 'react-icons/bs';
import { useCart } from '../../context/CartContext';

const CartIcon: React.FC = () => {
  const { cartItemCount } = useCart();

  return (
    <Link to="/cart" className="text-white me-3 position-relative">
      <BsCart size={25} />
      {cartItemCount > 0 && (
        <span
          className="position-absolute top-0 start-100 translate-middle badge rounded-pill bg-white text-secondary"
          style={{ fontSize: '0.7rem' }}
        >
          {cartItemCount}
        </span>
      )}
    </Link>
  );
};

export default CartIcon;