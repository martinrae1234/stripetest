import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { AffiliateService } from 'app/entities/affiliate/affiliate.service';
import { IAffiliate, Affiliate } from 'app/shared/model/affiliate.model';
import { Currency } from 'app/shared/model/enumerations/currency.model';

describe('Service Tests', () => {
  describe('Affiliate Service', () => {
    let injector: TestBed;
    let service: AffiliateService;
    let httpMock: HttpTestingController;
    let elemDefault: IAffiliate;
    let expectedResult;
    let currentDate: moment.Moment;
    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = {};
      injector = getTestBed();
      service = injector.get(AffiliateService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new Affiliate(
        0,
        'AAAAAAA',
        'AAAAAAA',
        Currency.GBP,
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        0,
        0,
        'AAAAAAA',
        false,
        false,
        false,
        false,
        false,
        false,
        false,
        0,
        currentDate
      );
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
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

      it('should create a Affiliate', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            createdDate: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            createdDate: currentDate
          },
          returnedFromService
        );
        service
          .create(new Affiliate(null))
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));
        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: expected });
      });

      it('should update a Affiliate', () => {
        const returnedFromService = Object.assign(
          {
            salesforceUID: 'BBBBBB',
            affiliateName: 'BBBBBB',
            currency: 'BBBBBB',
            streetAddress: 'BBBBBB',
            city: 'BBBBBB',
            county: 'BBBBBB',
            postcode: 'BBBBBB',
            country: 'BBBBBB',
            email: 'BBBBBB',
            phoneNumber: 'BBBBBB',
            locationCoordinateX: 1,
            locationCoordinateY: 1,
            defaultLanguage: 'BBBBBB',
            cardPayment: true,
            singleCardPayment: true,
            recurringCardPayment: true,
            directDebit: true,
            giftAid: true,
            generalFundraising: true,
            schoolFundraising: true,
            createdByUserId: 1,
            createdDate: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );

        const expected = Object.assign(
          {
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

      it('should return a list of Affiliate', () => {
        const returnedFromService = Object.assign(
          {
            salesforceUID: 'BBBBBB',
            affiliateName: 'BBBBBB',
            currency: 'BBBBBB',
            streetAddress: 'BBBBBB',
            city: 'BBBBBB',
            county: 'BBBBBB',
            postcode: 'BBBBBB',
            country: 'BBBBBB',
            email: 'BBBBBB',
            phoneNumber: 'BBBBBB',
            locationCoordinateX: 1,
            locationCoordinateY: 1,
            defaultLanguage: 'BBBBBB',
            cardPayment: true,
            singleCardPayment: true,
            recurringCardPayment: true,
            directDebit: true,
            giftAid: true,
            generalFundraising: true,
            schoolFundraising: true,
            createdByUserId: 1,
            createdDate: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );
        const expected = Object.assign(
          {
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

      it('should delete a Affiliate', () => {
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
