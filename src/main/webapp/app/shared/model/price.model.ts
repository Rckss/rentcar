import { Moment } from 'moment';

export const enum CarClass {
  AAA = 'AAA',
  BBB = 'BBB',
  CCC = 'CCC',
  DDD = 'DDD',
  EEE = 'EEE'
}

export interface IPrice {
  id?: number;
  adjDate?: Moment;
  name?: string;
  serial?: string;
  price?: number;
  factorOne?: number;
  factorTwo?: number;
  factorThree?: number;
  tax?: number;
  total?: number;
  classification?: CarClass;
}

export const defaultValue: Readonly<IPrice> = {};
