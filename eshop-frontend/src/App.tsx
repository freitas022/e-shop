import { BrowserRouter, Route, Routes } from 'react-router-dom'
import Footer from './components/Footer/Footer'
import Header from './components/Header/Header'
import ProductList from './components/Product/ProductList/ProductList'
import ProductDetails from './components/Product/ProductDetails/ProductDetails'
import { AuthProvider } from './context/AuthContext'
import Cart from './components/Cart/Cart'
import { CartProvider } from './context/CartContext'


function App() {

  return (
    <BrowserRouter>
      <AuthProvider>
        <CartProvider>
          <Header />
          <main className="my-5">
            <Routes>
              <Route path="/" element={<ProductList />} />
              <Route path="/products/:productId" element={<ProductDetails />} />
              <Route path="/profile" element={<div>Profile Page</div>} />
              <Route path="/cart" element={<Cart />} />
              <Route path="/purchases" element={<div>Pedidos works!</div>} />
            </Routes>
          </main>
          <Footer />
        </CartProvider>
      </AuthProvider>
    </BrowserRouter>
  )
}

export default App
