import { Moment } from 'moment';
import { ICar } from 'app/shared/model//car.model';

export const enum Status {
  RUNNING = 'RUNNING',
  PENDING = 'PENDING',
  PAYED = 'PAYED',
  CANCELLED = 'CANCELLED',
  DELETED = 'DELETED'
}

export interface IRentHistory {
  id?: number;
  regDate?: Moment;
  startDate?: Moment;
  endDate?: Moment;
  totalPaid?: number;
  status?: Status;
  cars?: ICar[];
  clientId?: number;
}

export const defaultValue: Readonly<IRentHistory> = {};
