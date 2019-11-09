import { Moment } from 'moment';
import { ISchool } from 'app/shared/model/school.model';

export interface IDelivery {
  id?: number;
  deliveryDate?: Moment;
  amountDeliveredRice?: number;
  amountDeliveredFlour?: number;
  deliveryNoteImageContentType?: string;
  deliveryNoteImage?: any;
  createdByUserId?: number;
  createdDate?: Moment;
  deliveryForSchool?: ISchool;
}

export class Delivery implements IDelivery {
  constructor(
    public id?: number,
    public deliveryDate?: Moment,
    public amountDeliveredRice?: number,
    public amountDeliveredFlour?: number,
    public deliveryNoteImageContentType?: string,
    public deliveryNoteImage?: any,
    public createdByUserId?: number,
    public createdDate?: Moment,
    public deliveryForSchool?: ISchool
  ) {}
}
