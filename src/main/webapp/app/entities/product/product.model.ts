import { ICategory } from 'app/entities/category/category.model';
import { StatusProduct } from 'app/entities/enumerations/status-product.model';

export interface IProduct {
  id: number;
  productName?: string | null;
  quantity?: number | null;
  imageProduct?: string | null;
  imageProductContentType?: string | null;
  unitprice?: number | null;
  status?: StatusProduct | null;
  category?: Pick<ICategory, 'id' | 'categoryName'> | null;
}

export type NewProduct = Omit<IProduct, 'id'> & { id: null };
