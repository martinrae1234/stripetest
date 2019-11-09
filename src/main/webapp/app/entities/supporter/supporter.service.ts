import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ISupporter } from 'app/shared/model/supporter.model';

type EntityResponseType = HttpResponse<ISupporter>;
type EntityArrayResponseType = HttpResponse<ISupporter[]>;

@Injectable({ providedIn: 'root' })
export class SupporterService {
  public resourceUrl = SERVER_API_URL + 'api/supporters';

  constructor(protected http: HttpClient) {}

  create(supporter: ISupporter): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(supporter);
    return this.http
      .post<ISupporter>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(supporter: ISupporter): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(supporter);
    return this.http
      .put<ISupporter>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ISupporter>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ISupporter[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(supporter: ISupporter): ISupporter {
    const copy: ISupporter = Object.assign({}, supporter, {
      createdDate: supporter.createdDate != null && supporter.createdDate.isValid() ? supporter.createdDate.toJSON() : null
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.createdDate = res.body.createdDate != null ? moment(res.body.createdDate) : null;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((supporter: ISupporter) => {
        supporter.createdDate = supporter.createdDate != null ? moment(supporter.createdDate) : null;
      });
    }
    return res;
  }
}
