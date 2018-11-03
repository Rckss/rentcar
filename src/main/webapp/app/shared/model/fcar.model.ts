import { Moment } from 'moment';
import { ITag } from 'app/shared/model//tag.model';
import { IPhoto } from 'app/shared/model//photo.model';

export const enum CarClass {
  AAA = 'AAA',
  BBB = 'BBB',
  CCC = 'CCC',
  DDD = 'DDD',
  EEE = 'EEE'
}

export interface IfCar {
  id?: number;
  name?: string;
  serial?: string;
  manufacturer?: string;
  model?: Moment;
  color?: string;
  classification?: CarClass;
  albumId?: number;
  tags?: ITag[];
  photoId?: number;
  photo?: IPhoto;
}

export const defaultValue: Readonly<IfCar> = {};
