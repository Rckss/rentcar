import { Moment } from 'moment';

export interface IContact {
  id?: number;
  phoneNumber?: string;
  birthday?: Moment;
  addressOne?: string;
  addressTwo?: string;
  postalCode?: string;
  city?: string;
  stateProvince?: string;
  countryName?: string;
  clientId?: number;
  photoId?: number;
}

export const defaultValue: Readonly<IContact> = {};
