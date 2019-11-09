import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { ReasonForNonFeedingService } from 'app/entities/reason-for-non-feeding/reason-for-non-feeding.service';
import { IReasonForNonFeeding, ReasonForNonFeeding } from 'app/shared/model/reason-for-non-feeding.model';

describe('Service Tests', () => {
  describe('ReasonForNonFeeding Service', () => {
    let injector: TestBed;
    let service: ReasonForNonFeedingService;
    let httpMock: HttpTestingController;
    let elemDefault: IReasonForNonFeeding;
    let expectedResult;
    let currentDate: moment.Moment;
    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = {};
      injector = getTestBed();
      service = injector.get(ReasonForNonFeedingService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new ReasonForNonFeeding(0, currentDate, 'AAAAAAA', 0, currentDate);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            dateOfNonFeeding: currentDate.format(DATE_TIME_FORMAT),
            createdDate: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );
        service
          .find(123)
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: elemDefault });
      });

      it('should create a ReasonForNonFeeding', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            dateOfNonFeeding: currentDate.format(DATE_TIME_FORMAT),
            createdDate: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            dateOfNonFeeding: currentDate,
            createdDate: currentDate
          },
          returnedFromService
        );
        service
          .create(new ReasonForNonFeeding(null))
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));
        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: expected });
      });

      it('should update a ReasonForNonFeeding', () => {
        const returnedFromService = Object.assign(
          {
            dateOfNonFeeding: currentDate.format(DATE_TIME_FORMAT),
            reasonForNonFeeding: 'BBBBBB',
            createdByUserId: 1,
            createdDate: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            dateOfNonFeeding: currentDate,
            createdDate: currentDate
          },
          returnedFromService
        );
        service
          .update(expected)
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));
        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: expected });
      });

      it('should return a list of ReasonForNonFeeding', () => {
        const returnedFromService = Object.assign(
          {
            dateOfNonFeeding: currentDate.format(DATE_TIME_FORMAT),
            reasonForNonFeeding: 'BBBBBB',
            createdByUserId: 1,
            createdDate: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            dateOfNonFeeding: currentDate,
            createdDate: currentDate
          },
          returnedFromService
        );
        service
          .query(expected)
          .pipe(
            take(1),
            map(resp => resp.body)
          )
          .subscribe(body => (expectedResult = body));
        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a ReasonForNonFeeding', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
