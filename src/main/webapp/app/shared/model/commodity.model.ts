import { Moment } from 'moment';
import { ISchool } from 'app/shared/model/school.model';

export interface ICommodity {
  id?: number;
  dateOfLastStockCheck?: Moment;
  name?: string;
  amount?: number;
  perishable?: boolean;
  amountExpirableInNext3months?: number;
  createdByUserId?: number;
  createdDate?: Moment;
  commodityForSchool?: ISchool;
}

export class Commodity implements ICommodity {
  constructor(
    public id?: number,
    public dateOfLastStockCheck?: Moment,
    public name?: string,
    public amount?: number,
    public perishable?: boolean,
    public amountExpirableInNext3months?: number,
    public createdByUserId?: number,
    public createdDate?: Moment,
    public commodityForSchool?: ISchool
  ) {
    this.perishable = this.perishable || false;
  }
}
