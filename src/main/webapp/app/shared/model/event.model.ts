import { Moment } from 'moment';
import { IProject } from 'app/shared/model/project.model';

export interface IEvent {
  id?: number;
  salesforceUID?: string;
  eventName?: string;
  eventDate?: Moment;
  eventDescription?: string;
  eventBannerContentType?: string;
  eventBanner?: any;
  eventPictureOneContentType?: string;
  eventPictureOne?: any;
  eventPrictureTwoContentType?: string;
  eventPrictureTwo?: any;
  locationCoordinateX?: number;
  locationCoordinateY?: number;
  attendees?: number;
  maximumAttendees?: number;
  createdByUserId?: number;
  createdDate?: Moment;
  project?: IProject;
}

export class Event implements IEvent {
  constructor(
    public id?: number,
    public salesforceUID?: string,
    public eventName?: string,
    public eventDate?: Moment,
    public eventDescription?: string,
    public eventBannerContentType?: string,
    public eventBanner?: any,
    public eventPictureOneContentType?: string,
    public eventPictureOne?: any,
    public eventPrictureTwoContentType?: string,
    public eventPrictureTwo?: any,
    public locationCoordinateX?: number,
    public locationCoordinateY?: number,
    public attendees?: number,
    public maximumAttendees?: number,
    public createdByUserId?: number,
    public createdDate?: Moment,
    public project?: IProject
  ) {}
}
