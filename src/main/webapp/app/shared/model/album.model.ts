import { Moment } from 'moment';

export interface IAlbum {
  id?: number;
  title?: string;
  description?: any;
  created?: Moment;
  userId?: number;
}

export const defaultValue: Readonly<IAlbum> = {};
