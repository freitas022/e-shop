import axios from 'axios';
import { ProductDto } from '../types/ProductDto';
import { BACKEND_URL } from '../util/request';

export function findAll(pageNumber: number, pageSize: number) {
    return axios.get<ProductDto[]>(`${BACKEND_URL}/products`, { params: { pageNumber, pageSize} });
}

export function findById(productId: number) {
    return axios.get<ProductDto>(`${BACKEND_URL}/products/${productId}`);
}
