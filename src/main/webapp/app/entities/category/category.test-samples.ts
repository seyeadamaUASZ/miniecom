import { ICategory, NewCategory } from './category.model';

export const sampleWithRequiredData: ICategory = {
  id: 2529,
  categoryName: 'Namibia Sausages',
};

export const sampleWithPartialData: ICategory = {
  id: 554,
  categoryName: 'USB Down-sized c',
  description: 'synthesizing a',
};

export const sampleWithFullData: ICategory = {
  id: 32315,
  categoryName: 'target Realigned Handmade',
  description: 'Concrete Steel transform',
};

export const sampleWithNewData: NewCategory = {
  categoryName: 'Account Poitou-Charentes',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
