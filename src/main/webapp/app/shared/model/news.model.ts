import { Moment } from 'moment';

export interface INews {
  id?: number;
  activeMessage?: boolean;
  bannerPictureContentType?: string;
  bannerPicture?: any;
  dateOfNewsCreation?: Moment;
  bannerName?: string;
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

export class News implements INews {
  constructor(
    public id?: number,
    public activeMessage?: boolean,
    public bannerPictureContentType?: string,
    public bannerPicture?: any,
    public dateOfNewsCreation?: Moment,
    public bannerName?: string,
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
    this.activeMessage = this.activeMessage || false;
  }
}
