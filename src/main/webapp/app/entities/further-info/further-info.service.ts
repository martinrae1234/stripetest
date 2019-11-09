import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IFurtherInfo } from 'app/shared/model/further-info.model';

type EntityResponseType = HttpResponse<IFurtherInfo>;
type EntityArrayResponseType = HttpResponse<IFurtherInfo[]>;

@Injectable({ providedIn: 'root' })
export class FurtherInfoService {
  public resourceUrl = SERVER_API_URL + 'api/further-infos';

  constructor(protected http: HttpClient) {}

  create(furtherInfo: IFurtherInfo): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(furtherInfo);
    return this.http
      .post<IFurtherInfo>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(furtherInfo: IFurtherInfo): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(furtherInfo);
    return this.http
      .put<IFurtherInfo>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IFurtherInfo>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IFurtherInfo[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(furtherInfo: IFurtherInfo): IFurtherInfo {
    const copy: IFurtherInfo = Object.assign({}, furtherInfo, {
      createdDate: furtherInfo.createdDate != null && furtherInfo.createdDate.isValid() ? furtherInfo.createdDate.toJSON() : null
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
      res.body.forEach((furtherInfo: IFurtherInfo) => {
        furtherInfo.createdDate = furtherInfo.createdDate != null ? moment(furtherInfo.createdDate) : null;
      });
    }
    return res;
  }
}
