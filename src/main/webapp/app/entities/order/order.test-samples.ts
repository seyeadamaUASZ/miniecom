import dayjs from 'dayjs/esm';

import { IOrder, NewOrder } from './order.model';

export const sampleWithRequiredData: IOrder = {
  id: 47761,
  quantityOrder: 34511,
  date: dayjs('2023-05-06T05:05'),
};

export const sampleWithPartialData: IOrder = {
  id: 52696,
  quantityOrder: 69128,
  date: dayjs('2023-05-06T13:56'),
};

export const sampleWithFullData: IOrder = {
  id: 47091,
  quantityOrder: 56605,
  date: dayjs('2023-05-06T01:42'),
};

export const sampleWithNewData: NewOrder = {
  quantityOrder: 10948,
  date: dayjs('2023-05-06T05:44'),
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
