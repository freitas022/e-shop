import { BrowserRouter, Route, Routes } from "react-router-dom";
import Header from "./components/Header";
import Home from "./components/Home";
import { ProductProvider } from "./contexts/ProductProvider";
import ProductCardItem from "./components/ProductCardItem";

function App() {

  return (
    <>
      <ProductProvider>
        <BrowserRouter>
          <Header />
          <Routes>
            <Route path="/" element={<Home />} />
            <Route path="/products/:id" element={<ProductCardItem />} />
          </Routes>
        </BrowserRouter>
      </ProductProvider>
    </>
  )
}

export default App
