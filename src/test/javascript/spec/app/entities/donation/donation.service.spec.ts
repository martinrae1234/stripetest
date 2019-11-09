import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { DonationService } from 'app/entities/donation/donation.service';
import { IDonation, Donation } from 'app/shared/model/donation.model';
import { Currency } from 'app/shared/model/enumerations/currency.model';
import { PaymentMethod } from 'app/shared/model/enumerations/payment-method.model';
import { Frequency } from 'app/shared/model/enumerations/frequency.model';
import { CollectionDate } from 'app/shared/model/enumerations/collection-date.model';
import { CardType } from 'app/shared/model/enumerations/card-type.model';

describe('Service Tests', () => {
  describe('Donation Service', () => {
    let injector: TestBed;
    let service: DonationService;
    let httpMock: HttpTestingController;
    let elemDefault: IDonation;
    let expectedResult;
    let currentDate: moment.Moment;
    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = {};
      injector = getTestBed();
      service = injector.get(DonationService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new Donation(
        0,
        'AAAAAAA',
        Currency.GBP,
        0,
        PaymentMethod.DIRECTDEBIT,
        Frequency.SINGLE,
        false,
        false,
        'AAAAAAA',
        'AAAAAAA',
        0,
        0,
        CollectionDate.FIRST,
        false,
        CardType.VISA,
        0,
        0,
        0,
        0,
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

      it('should create a Donation', () => {
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
          .create(new Donation(null))
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));
        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: expected });
      });

      it('should update a Donation', () => {
        const returnedFromService = Object.assign(
          {
            salesforceUID: 'BBBBBB',
            currency: 'BBBBBB',
            amount: 1,
            paymentMethod: 'BBBBBB',
            frequency: 'BBBBBB',
            ageCategory: true,
            giftAidable: true,
            giftAidMessage: 'BBBBBB',
            accountHolderName: 'BBBBBB',
            accountNumber: 1,
            sortcode: 1,
            collectionDate: 'BBBBBB',
            isAccountHolder: true,
            cardType: 'BBBBBB',
            cardNumber: 1,
            expiryMonth: 1,
            expiryYear: 1,
            cardSecurityCode: 1,
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

      it('should return a list of Donation', () => {
        const returnedFromService = Object.assign(
          {
            salesforceUID: 'BBBBBB',
            currency: 'BBBBBB',
            amount: 1,
            paymentMethod: 'BBBBBB',
            frequency: 'BBBBBB',
            ageCategory: true,
            giftAidable: true,
            giftAidMessage: 'BBBBBB',
            accountHolderName: 'BBBBBB',
            accountNumber: 1,
            sortcode: 1,
            collectionDate: 'BBBBBB',
            isAccountHolder: true,
            cardType: 'BBBBBB',
            cardNumber: 1,
            expiryMonth: 1,
            expiryYear: 1,
            cardSecurityCode: 1,
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

      it('should delete a Donation', () => {
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
