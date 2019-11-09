import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IReasonForNonFeeding } from 'app/shared/model/reason-for-non-feeding.model';

type EntityResponseType = HttpResponse<IReasonForNonFeeding>;
type EntityArrayResponseType = HttpResponse<IReasonForNonFeeding[]>;

@Injectable({ providedIn: 'root' })
export class ReasonForNonFeedingService {
  public resourceUrl = SERVER_API_URL + 'api/reason-for-non-feedings';

  constructor(protected http: HttpClient) {}

  create(reasonForNonFeeding: IReasonForNonFeeding): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(reasonForNonFeeding);
    return this.http
      .post<IReasonForNonFeeding>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(reasonForNonFeeding: IReasonForNonFeeding): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(reasonForNonFeeding);
    return this.http
      .put<IReasonForNonFeeding>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IReasonForNonFeeding>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IReasonForNonFeeding[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(reasonForNonFeeding: IReasonForNonFeeding): IReasonForNonFeeding {
    const copy: IReasonForNonFeeding = Object.assign({}, reasonForNonFeeding, {
      dateOfNonFeeding:
        reasonForNonFeeding.dateOfNonFeeding != null && reasonForNonFeeding.dateOfNonFeeding.isValid()
          ? reasonForNonFeeding.dateOfNonFeeding.toJSON()
          : null,
      createdDate:
        reasonForNonFeeding.createdDate != null && reasonForNonFeeding.createdDate.isValid()
          ? reasonForNonFeeding.createdDate.toJSON()
          : null
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.dateOfNonFeeding = res.body.dateOfNonFeeding != null ? moment(res.body.dateOfNonFeeding) : null;
      res.body.createdDate = res.body.createdDate != null ? moment(res.body.createdDate) : null;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((reasonForNonFeeding: IReasonForNonFeeding) => {
        reasonForNonFeeding.dateOfNonFeeding =
          reasonForNonFeeding.dateOfNonFeeding != null ? moment(reasonForNonFeeding.dateOfNonFeeding) : null;
        reasonForNonFeeding.createdDate = reasonForNonFeeding.createdDate != null ? moment(reasonForNonFeeding.createdDate) : null;
      });
    }
    return res;
  }
}
