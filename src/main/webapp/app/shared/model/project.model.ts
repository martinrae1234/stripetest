import { Moment } from 'moment';
import { ISupporter } from 'app/shared/model/supporter.model';
import { ISchool } from 'app/shared/model/school.model';
import { TypeOfProject } from 'app/shared/model/enumerations/type-of-project.model';

export interface IProject {
  id?: number;
  salesforceUID?: string;
  typeOfProject?: TypeOfProject;
  fundraisingTarget?: number;
  ageCategory?: boolean;
  projectName?: string;
  projectDescription?: any;
  projectImageContentType?: string;
  projectImage?: any;
  sponsorshipStart?: Moment;
  sponsorshipEndDate?: Moment;
  createdByUserId?: number;
  createdDate?: Moment;
  projectOfsupporter?: ISupporter;
  projectForSchool?: ISchool;
}

export class Project implements IProject {
  constructor(
    public id?: number,
    public salesforceUID?: string,
    public typeOfProject?: TypeOfProject,
    public fundraisingTarget?: number,
    public ageCategory?: boolean,
    public projectName?: string,
    public projectDescription?: any,
    public projectImageContentType?: string,
    public projectImage?: any,
    public sponsorshipStart?: Moment,
    public sponsorshipEndDate?: Moment,
    public createdByUserId?: number,
    public createdDate?: Moment,
    public projectOfsupporter?: ISupporter,
    public projectForSchool?: ISchool
  ) {
    this.ageCategory = this.ageCategory || false;
  }
}
