import { BrowserRouter, Route, Routes } from 'react-router-dom'
import Footer from './components/Footer/Footer'
import Header from './components/Header/Header'
import ProductList from './components/Product/ProductList/ProductList'
import ProductDetails from './components/Product/ProductDetails/ProductDetails'


function App() {

  return (
    <>
      <BrowserRouter>
        <Header />
        <main className="my-5">
          <Routes>
            <Route path="/" element={<ProductList />} />
            <Route path="/products/:productId" element={<ProductDetails />} />
          </Routes>
        </main>
        <Footer />
      </BrowserRouter>
    </>
  )
}

export default App
