import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IProject } from 'app/shared/model/project.model';

type EntityResponseType = HttpResponse<IProject>;
type EntityArrayResponseType = HttpResponse<IProject[]>;

@Injectable({ providedIn: 'root' })
export class ProjectService {
  public resourceUrl = SERVER_API_URL + 'api/projects';

  constructor(protected http: HttpClient) {}

  create(project: IProject): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(project);
    return this.http
      .post<IProject>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(project: IProject): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(project);
    return this.http
      .put<IProject>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IProject>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IProject[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(project: IProject): IProject {
    const copy: IProject = Object.assign({}, project, {
      sponsorshipStart:
        project.sponsorshipStart != null && project.sponsorshipStart.isValid() ? project.sponsorshipStart.format(DATE_FORMAT) : null,
      sponsorshipEndDate:
        project.sponsorshipEndDate != null && project.sponsorshipEndDate.isValid() ? project.sponsorshipEndDate.format(DATE_FORMAT) : null,
      createdDate: project.createdDate != null && project.createdDate.isValid() ? project.createdDate.toJSON() : null
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.sponsorshipStart = res.body.sponsorshipStart != null ? moment(res.body.sponsorshipStart) : null;
      res.body.sponsorshipEndDate = res.body.sponsorshipEndDate != null ? moment(res.body.sponsorshipEndDate) : null;
      res.body.createdDate = res.body.createdDate != null ? moment(res.body.createdDate) : null;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((project: IProject) => {
        project.sponsorshipStart = project.sponsorshipStart != null ? moment(project.sponsorshipStart) : null;
        project.sponsorshipEndDate = project.sponsorshipEndDate != null ? moment(project.sponsorshipEndDate) : null;
        project.createdDate = project.createdDate != null ? moment(project.createdDate) : null;
      });
    }
    return res;
  }
}
