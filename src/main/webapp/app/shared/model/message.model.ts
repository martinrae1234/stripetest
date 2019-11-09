import { Moment } from 'moment';

export interface IMessage {
  id?: number;
  activeMessage?: boolean;
  bannerPictureContentType?: string;
  bannerPicture?: any;
  bannerName?: string;
  headingOne?: string;
  descriptionOne?: any;
  quotePictureContentType?: string;
  quotePicture?: any;
  quoteContent?: string;
  headerTwo?: string;
  descriptionTwo?: any;
  bottomPictureContentType?: string;
  bottomPicture?: any;
  bottomDescription?: any;
  buttonText?: string;
  buttonURL?: string;
  createdByUserId?: number;
  createdDate?: Moment;
}

export class Message implements IMessage {
  constructor(
    public id?: number,
    public activeMessage?: boolean,
    public bannerPictureContentType?: string,
    public bannerPicture?: any,
    public bannerName?: string,
    public headingOne?: string,
    public descriptionOne?: any,
    public quotePictureContentType?: string,
    public quotePicture?: any,
    public quoteContent?: string,
    public headerTwo?: string,
    public descriptionTwo?: any,
    public bottomPictureContentType?: string,
    public bottomPicture?: any,
    public bottomDescription?: any,
    public buttonText?: string,
    public buttonURL?: string,
    public createdByUserId?: number,
    public createdDate?: Moment
  ) {
    this.activeMessage = this.activeMessage || false;
  }
}
