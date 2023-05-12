import { StatusProduct } from 'app/entities/enumerations/status-product.model';

import { IProduct, NewProduct } from './product.model';

export const sampleWithRequiredData: IProduct = {
  id: 77672,
  productName: 'Customer-focused',
  quantity: 96307,
  unitprice: 78560,
};

export const sampleWithPartialData: IProduct = {
  id: 7747,
  productName: 'Programmable Networked deliver',
  quantity: 39641,
  unitprice: 99933,
  status: StatusProduct['EMPTY'],
};

export const sampleWithFullData: IProduct = {
  id: 71472,
  productName: 'Saint-Dominique',
  quantity: 66440,
  imageProduct: '../fake-data/blob/hipster.png',
  imageProductContentType: 'unknown',
  unitprice: 85387,
  status: StatusProduct['NOT_ENOUGH'],
};

export const sampleWithNewData: NewProduct = {
  productName: 'Account functionalities Garden',
  quantity: 22675,
  unitprice: 13372,
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
