import { Moment } from 'moment';
import { ISchool } from 'app/shared/model/school.model';

export interface IReasonForNonFeeding {
  id?: number;
  dateOfNonFeeding?: Moment;
  reasonForNonFeeding?: string;
  createdByUserId?: number;
  createdDate?: Moment;
  schoolNotFed?: ISchool;
}

export class ReasonForNonFeeding implements IReasonForNonFeeding {
  constructor(
    public id?: number,
    public dateOfNonFeeding?: Moment,
    public reasonForNonFeeding?: string,
    public createdByUserId?: number,
    public createdDate?: Moment,
    public schoolNotFed?: ISchool
  ) {}
}
