import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ICommodity } from 'app/shared/model/commodity.model';

type EntityResponseType = HttpResponse<ICommodity>;
type EntityArrayResponseType = HttpResponse<ICommodity[]>;

@Injectable({ providedIn: 'root' })
export class CommodityService {
  public resourceUrl = SERVER_API_URL + 'api/commodities';

  constructor(protected http: HttpClient) {}

  create(commodity: ICommodity): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(commodity);
    return this.http
      .post<ICommodity>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(commodity: ICommodity): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(commodity);
    return this.http
      .put<ICommodity>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ICommodity>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ICommodity[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(commodity: ICommodity): ICommodity {
    const copy: ICommodity = Object.assign({}, commodity, {
      dateOfLastStockCheck:
        commodity.dateOfLastStockCheck != null && commodity.dateOfLastStockCheck.isValid()
          ? commodity.dateOfLastStockCheck.format(DATE_FORMAT)
          : null,
      createdDate: commodity.createdDate != null && commodity.createdDate.isValid() ? commodity.createdDate.toJSON() : null
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.dateOfLastStockCheck = res.body.dateOfLastStockCheck != null ? moment(res.body.dateOfLastStockCheck) : null;
      res.body.createdDate = res.body.createdDate != null ? moment(res.body.createdDate) : null;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((commodity: ICommodity) => {
        commodity.dateOfLastStockCheck = commodity.dateOfLastStockCheck != null ? moment(commodity.dateOfLastStockCheck) : null;
        commodity.createdDate = commodity.createdDate != null ? moment(commodity.createdDate) : null;
      });
    }
    return res;
  }
}
