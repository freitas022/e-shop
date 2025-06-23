import { ProductDto } from "./Product";
import { ClientDto } from "./User";

export interface CartItemDto {
  product: ProductDto;
  quantity: number;
  subTotal: number;
}

export interface CartDto {
  id: number;
  client: ClientDto;
  items: CartItemDto[];
  total: number;
}
