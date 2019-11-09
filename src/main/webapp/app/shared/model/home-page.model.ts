import { TypeOfProject } from 'app/shared/model/enumerations/type-of-project.model';

export interface IHomePage {
  id?: number;
  costOfFeedingAChild?: number;
  typeOfProject?: TypeOfProject;
}

export class HomePage implements IHomePage {
  constructor(public id?: number, public costOfFeedingAChild?: number, public typeOfProject?: TypeOfProject) {}
}
