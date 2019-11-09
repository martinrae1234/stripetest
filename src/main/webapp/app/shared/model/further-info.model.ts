import { Moment } from 'moment';

export interface IFurtherInfo {
  id?: number;
  activeFurtherInfo?: boolean;
  bannerPictureContentType?: string;
  bannerPicture?: any;
  bannerName?: string;
  headerOne?: string;
  descriptionOne?: any;
  pictureOneContentType?: string;
  pictureOne?: any;
  descriptionTwo?: any;
  pictureTwoContentType?: string;
  pictureTwo?: any;
  descriptionThree?: any;
  pictureThreeContentType?: string;
  pictureThree?: any;
  bottomPictureContentType?: string;
  bottomPicture?: any;
  bottomDescription?: string;
  buttonText?: string;
  buttonURL?: string;
  createdByUserId?: number;
  createdDate?: Moment;
}

export class FurtherInfo implements IFurtherInfo {
  constructor(
    public id?: number,
    public activeFurtherInfo?: boolean,
    public bannerPictureContentType?: string,
    public bannerPicture?: any,
    public bannerName?: string,
    public headerOne?: string,
    public descriptionOne?: any,
    public pictureOneContentType?: string,
    public pictureOne?: any,
    public descriptionTwo?: any,
    public pictureTwoContentType?: string,
    public pictureTwo?: any,
    public descriptionThree?: any,
    public pictureThreeContentType?: string,
    public pictureThree?: any,
    public bottomPictureContentType?: string,
    public bottomPicture?: any,
    public bottomDescription?: string,
    public buttonText?: string,
    public buttonURL?: string,
    public createdByUserId?: number,
    public createdDate?: Moment
  ) {
    this.activeFurtherInfo = this.activeFurtherInfo || false;
  }
}
