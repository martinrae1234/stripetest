import { Moment } from 'moment';
import { IUser } from 'app/core/user/user.model';
import { IAffiliate } from 'app/shared/model/affiliate.model';
import { SupporterSalutation } from 'app/shared/model/enumerations/supporter-salutation.model';

export interface ISupporter {
  id?: number;
  salesforceUID?: string;
  ageCategory?: boolean;
  supporterSalutation?: SupporterSalutation;
  firstName?: string;
  secondName?: string;
  email?: string;
  streetAddress?: string;
  city?: string;
  county?: string;
  postcode?: string;
  country?: string;
  supporterPictureContentType?: string;
  supporterPicture?: any;
  contactableByEmail?: boolean;
  contactableByPost?: boolean;
  locationCoordinateX?: number;
  locationCoordinateY?: number;
  facebookUrl?: string;
  instagramUrl?: string;
  twitterUrl?: string;
  createdByUserId?: number;
  createdDate?: Moment;
  user?: IUser;
  supporterOfAffiliate?: IAffiliate;
}

export class Supporter implements ISupporter {
  constructor(
    public id?: number,
    public salesforceUID?: string,
    public ageCategory?: boolean,
    public supporterSalutation?: SupporterSalutation,
    public firstName?: string,
    public secondName?: string,
    public email?: string,
    public streetAddress?: string,
    public city?: string,
    public county?: string,
    public postcode?: string,
    public country?: string,
    public supporterPictureContentType?: string,
    public supporterPicture?: any,
    public contactableByEmail?: boolean,
    public contactableByPost?: boolean,
    public locationCoordinateX?: number,
    public locationCoordinateY?: number,
    public facebookUrl?: string,
    public instagramUrl?: string,
    public twitterUrl?: string,
    public createdByUserId?: number,
    public createdDate?: Moment,
    public user?: IUser,
    public supporterOfAffiliate?: IAffiliate
  ) {
    this.ageCategory = this.ageCategory || false;
    this.contactableByEmail = this.contactableByEmail || false;
    this.contactableByPost = this.contactableByPost || false;
  }
}
