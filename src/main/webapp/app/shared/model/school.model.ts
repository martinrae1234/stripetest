import { Moment } from 'moment';

export interface ISchool {
  id?: number;
  salesforceUID?: string;
  legacySchoolID?: string;
  schoolName?: string;
  sponsored?: boolean;
  attendanceTotal?: number;
  attendanceBoys?: number;
  attendanceGirls?: number;
  enrolmentTotal?: number;
  locationCoordinateX?: number;
  locationCoordinateY?: number;
  imageBannerContentType?: string;
  imageBanner?: any;
  textBanner?: string;
  imageOneContentType?: string;
  imageOne?: any;
  imageTwoContentType?: string;
  imageTwo?: any;
  imageThreeContentType?: string;
  imageThree?: any;
  imageFourContentType?: string;
  imageFour?: any;
  dateOfLastStockCheck?: Moment;
  createdByUserId?: number;
  createdDate?: Moment;
}

export class School implements ISchool {
  constructor(
    public id?: number,
    public salesforceUID?: string,
    public legacySchoolID?: string,
    public schoolName?: string,
    public sponsored?: boolean,
    public attendanceTotal?: number,
    public attendanceBoys?: number,
    public attendanceGirls?: number,
    public enrolmentTotal?: number,
    public locationCoordinateX?: number,
    public locationCoordinateY?: number,
    public imageBannerContentType?: string,
    public imageBanner?: any,
    public textBanner?: string,
    public imageOneContentType?: string,
    public imageOne?: any,
    public imageTwoContentType?: string,
    public imageTwo?: any,
    public imageThreeContentType?: string,
    public imageThree?: any,
    public imageFourContentType?: string,
    public imageFour?: any,
    public dateOfLastStockCheck?: Moment,
    public createdByUserId?: number,
    public createdDate?: Moment
  ) {
    this.sponsored = this.sponsored || false;
  }
}
