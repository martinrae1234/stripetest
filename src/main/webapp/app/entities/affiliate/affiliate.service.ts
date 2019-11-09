import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IAffiliate } from 'app/shared/model/affiliate.model';

type EntityResponseType = HttpResponse<IAffiliate>;
type EntityArrayResponseType = HttpResponse<IAffiliate[]>;

@Injectable({ providedIn: 'root' })
export class AffiliateService {
  public resourceUrl = SERVER_API_URL + 'api/affiliates';

  constructor(protected http: HttpClient) {}

  create(affiliate: IAffiliate): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(affiliate);
    return this.http
      .post<IAffiliate>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(affiliate: IAffiliate): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(affiliate);
    return this.http
      .put<IAffiliate>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IAffiliate>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IAffiliate[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(affiliate: IAffiliate): IAffiliate {
    const copy: IAffiliate = Object.assign({}, affiliate, {
      createdDate: affiliate.createdDate != null && affiliate.createdDate.isValid() ? affiliate.createdDate.toJSON() : null
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
      res.body.forEach((affiliate: IAffiliate) => {
        affiliate.createdDate = affiliate.createdDate != null ? moment(affiliate.createdDate) : null;
      });
    }
    return res;
  }
}
