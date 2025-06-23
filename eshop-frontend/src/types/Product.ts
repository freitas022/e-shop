import { CategoryDto } from "./Category";

export interface ProductDto {
    id: number;
    name: string;
    description: string;
    price: number;
    imgUrl: string;
    categories: CategoryDto[]
}