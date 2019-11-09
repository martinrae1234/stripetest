import { Moment } from 'moment';
import { Currency } from 'app/shared/model/enumerations/currency.model';

export interface IAffiliate {
  id?: number;
  salesforceUID?: string;
  affiliateName?: string;
  currency?: Currency;
  streetAddress?: string;
  city?: string;
  county?: string;
  postcode?: string;
  country?: string;
  email?: string;
  phoneNumber?: string;
  locationCoordinateX?: number;
  locationCoordinateY?: number;
  defaultLanguage?: string;
  cardPayment?: boolean;
  singleCardPayment?: boolean;
  recurringCardPayment?: boolean;
  directDebit?: boolean;
  giftAid?: boolean;
  generalFundraising?: boolean;
  schoolFundraising?: boolean;
  createdByUserId?: number;
  createdDate?: Moment;
}

export class Affiliate implements IAffiliate {
  constructor(
    public id?: number,
    public salesforceUID?: string,
    public affiliateName?: string,
    public currency?: Currency,
    public streetAddress?: string,
    public city?: string,
    public county?: string,
    public postcode?: string,
    public country?: string,
    public email?: string,
    public phoneNumber?: string,
    public locationCoordinateX?: number,
    public locationCoordinateY?: number,
    public defaultLanguage?: string,
    public cardPayment?: boolean,
    public singleCardPayment?: boolean,
    public recurringCardPayment?: boolean,
    public directDebit?: boolean,
    public giftAid?: boolean,
    public generalFundraising?: boolean,
    public schoolFundraising?: boolean,
    public createdByUserId?: number,
    public createdDate?: Moment
  ) {
    this.cardPayment = this.cardPayment || false;
    this.singleCardPayment = this.singleCardPayment || false;
    this.recurringCardPayment = this.recurringCardPayment || false;
    this.directDebit = this.directDebit || false;
    this.giftAid = this.giftAid || false;
    this.generalFundraising = this.generalFundraising || false;
    this.schoolFundraising = this.schoolFundraising || false;
  }
}
