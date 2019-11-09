import { Moment } from 'moment';
import { ISupporter } from 'app/shared/model/supporter.model';
import { IProject } from 'app/shared/model/project.model';
import { Currency } from 'app/shared/model/enumerations/currency.model';
import { PaymentMethod } from 'app/shared/model/enumerations/payment-method.model';
import { Frequency } from 'app/shared/model/enumerations/frequency.model';
import { CollectionDate } from 'app/shared/model/enumerations/collection-date.model';
import { CardType } from 'app/shared/model/enumerations/card-type.model';

export interface IDonation {
  id?: number;
  salesforceUID?: string;
  currency?: Currency;
  amount?: number;
  paymentMethod?: PaymentMethod;
  frequency?: Frequency;
  ageCategory?: boolean;
  giftAidable?: boolean;
  giftAidMessage?: string;
  accountHolderName?: string;
  accountNumber?: number;
  sortcode?: number;
  collectionDate?: CollectionDate;
  isAccountHolder?: boolean;
  cardType?: CardType;
  cardNumber?: number;
  expiryMonth?: number;
  expiryYear?: number;
  cardSecurityCode?: number;
  createdByUserId?: number;
  createdDate?: Moment;
  donationToSupporter?: ISupporter;
  donatesToProject?: IProject;
}

export class Donation implements IDonation {
  constructor(
    public id?: number,
    public salesforceUID?: string,
    public currency?: Currency,
    public amount?: number,
    public paymentMethod?: PaymentMethod,
    public frequency?: Frequency,
    public ageCategory?: boolean,
    public giftAidable?: boolean,
    public giftAidMessage?: string,
    public accountHolderName?: string,
    public accountNumber?: number,
    public sortcode?: number,
    public collectionDate?: CollectionDate,
    public isAccountHolder?: boolean,
    public cardType?: CardType,
    public cardNumber?: number,
    public expiryMonth?: number,
    public expiryYear?: number,
    public cardSecurityCode?: number,
    public createdByUserId?: number,
    public createdDate?: Moment,
    public donationToSupporter?: ISupporter,
    public donatesToProject?: IProject
  ) {
    this.ageCategory = this.ageCategory || false;
    this.giftAidable = this.giftAidable || false;
    this.isAccountHolder = this.isAccountHolder || false;
  }
}
