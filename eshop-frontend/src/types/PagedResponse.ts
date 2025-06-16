export interface PagedResponse<T> {
  content: T[];
  pageNumber: number;
  pageSize: number;
  totalElements: number;
  totalPages: number;
  last: boolean;
}

export interface RequestParam {
  page?: number; // default: 0
  size?: number; // default: 10
  sort?: string; // default: 'id,asc'
}