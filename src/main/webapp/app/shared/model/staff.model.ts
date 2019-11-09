import { Moment } from 'moment';
import { IUser } from 'app/core/user/user.model';
import { IAffiliate } from 'app/shared/model/affiliate.model';
import { TypeOfStaff } from 'app/shared/model/enumerations/type-of-staff.model';

export interface IStaff {
  id?: number;
  salesforceUID?: string;
  firstName?: string;
  secondName?: string;
  affiliate?: string;
  typeOfStaff?: TypeOfStaff;
  locationCoordinateX?: number;
  locationCoordinateY?: number;
  staffPictureContentType?: string;
  staffPicture?: any;
  position?: string;
  description?: any;
  createdByUserId?: number;
  createdDate?: Moment;
  user?: IUser;
  staffOfAffiliate?: IAffiliate;
}

export class Staff implements IStaff {
  constructor(
    public id?: number,
    public salesforceUID?: string,
    public firstName?: string,
    public secondName?: string,
    public affiliate?: string,
    public typeOfStaff?: TypeOfStaff,
    public locationCoordinateX?: number,
    public locationCoordinateY?: number,
    public staffPictureContentType?: string,
    public staffPicture?: any,
    public position?: string,
    public description?: any,
    public createdByUserId?: number,
    public createdDate?: Moment,
    public user?: IUser,
    public staffOfAffiliate?: IAffiliate
  ) {}
}
