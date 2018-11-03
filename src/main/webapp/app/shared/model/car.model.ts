import { Moment } from 'moment';
import { ITag } from 'app/shared/model//tag.model';

export const enum CarClass {
  AAA = 'AAA',
  BBB = 'BBB',
  CCC = 'CCC',
  DDD = 'DDD',
  EEE = 'EEE'
}

export interface ICar {
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
}

export const defaultValue: Readonly<ICar> = {};
