import dayjs from 'dayjs/esm';
import { IUser } from 'app/entities/user/user.model';
import { IProduct } from 'app/entities/product/product.model';

export interface IOrder {
  id: number;
  quantityOrder?: number | null;
  date?: dayjs.Dayjs | null;
  user?: Pick<IUser, 'id' | 'login'> | null;
  product?: Pick<IProduct, 'id' | 'productName'> | null;
}

export type NewOrder = Omit<IOrder, 'id'> & { id: null };
