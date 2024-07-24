import axios from "axios";
import { createContext, ReactNode, useEffect, useMemo, useState } from "react";
import Product from "../../model/Product";

interface IProductContext {
    products: Product[];
    setProducts: React.Dispatch<React.SetStateAction<Product[]>>;
}

export const ProductContext = createContext<IProductContext>({} as IProductContext);

type ContextProviderProps = {
    children: ReactNode;
}

export const ProductProvider = ({ children }: ContextProviderProps) => {

    const [products, setProducts] = useState<Product[]>([]);

    useEffect(() => {
        axios.get(`http://localhost:8080/products`)
            .then(response => {
                setProducts(response.data.products);
            });
    }, []);

    const productContextValue = useMemo(() => ({ products, setProducts }), [products, setProducts]);

    return (
        <ProductContext.Provider value={productContextValue}>
            {children}
        </ProductContext.Provider>
    );
}

