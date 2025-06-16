import axios from 'axios';
import { ProductDto } from '../types/ProductDto';
import { BACKEND_URL } from '../util/request';
import { PagedResponse, RequestParam } from '../types/PagedResponse';


export function findAll(params: RequestParam = {}) {
  return axios.get<PagedResponse<ProductDto>>(`${BACKEND_URL}/products`, { params });
}

export function findById(productId: number) {
    return axios.get<ProductDto>(`${BACKEND_URL}/products/${productId}`);
}
