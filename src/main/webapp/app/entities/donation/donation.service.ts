import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IDonation } from 'app/shared/model/donation.model';

type EntityResponseType = HttpResponse<IDonation>;
type EntityArrayResponseType = HttpResponse<IDonation[]>;

@Injectable({ providedIn: 'root' })
export class DonationService {
  public resourceUrl = SERVER_API_URL + 'api/donations';

  constructor(protected http: HttpClient) {}

  create(donation: IDonation): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(donation);
    return this.http
      .post<IDonation>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(donation: IDonation): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(donation);
    return this.http
      .put<IDonation>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IDonation>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IDonation[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(donation: IDonation): IDonation {
    const copy: IDonation = Object.assign({}, donation, {
      createdDate: donation.createdDate != null && donation.createdDate.isValid() ? donation.createdDate.toJSON() : null
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
      res.body.forEach((donation: IDonation) => {
        donation.createdDate = donation.createdDate != null ? moment(donation.createdDate) : null;
      });
    }
    return res;
  }
}
