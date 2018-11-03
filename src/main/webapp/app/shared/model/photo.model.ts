import { Moment } from 'moment';
import { ITag } from 'app/shared/model//tag.model';

export interface IPhoto {
  id?: number;
  title?: string;
  description?: any;
  imageContentType?: string;
  image?: any;
  height?: number;
  width?: number;
  taken?: Moment;
  uploaded?: Moment;
  userId?: number;
  albumTitle?: string;
  albumId?: number;
  tags?: ITag[];
}

export const defaultValue: Readonly<IPhoto> = {};
