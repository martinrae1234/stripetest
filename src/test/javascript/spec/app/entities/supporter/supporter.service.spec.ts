import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { SupporterService } from 'app/entities/supporter/supporter.service';
import { ISupporter, Supporter } from 'app/shared/model/supporter.model';
import { SupporterSalutation } from 'app/shared/model/enumerations/supporter-salutation.model';

describe('Service Tests', () => {
  describe('Supporter Service', () => {
    let injector: TestBed;
    let service: SupporterService;
    let httpMock: HttpTestingController;
    let elemDefault: ISupporter;
    let expectedResult;
    let currentDate: moment.Moment;
    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = {};
      injector = getTestBed();
      service = injector.get(SupporterService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new Supporter(
        0,
        'AAAAAAA',
        false,
        SupporterSalutation.MR,
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'image/png',
        'AAAAAAA',
        false,
        false,
        0,
        0,
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
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

      it('should create a Supporter', () => {
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
          .create(new Supporter(null))
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));
        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: expected });
      });

      it('should update a Supporter', () => {
        const returnedFromService = Object.assign(
          {
            salesforceUID: 'BBBBBB',
            ageCategory: true,
            supporterSalutation: 'BBBBBB',
            firstName: 'BBBBBB',
            secondName: 'BBBBBB',
            email: 'BBBBBB',
            streetAddress: 'BBBBBB',
            city: 'BBBBBB',
            county: 'BBBBBB',
            postcode: 'BBBBBB',
            country: 'BBBBBB',
            supporterPicture: 'BBBBBB',
            contactableByEmail: true,
            contactableByPost: true,
            locationCoordinateX: 1,
            locationCoordinateY: 1,
            facebookUrl: 'BBBBBB',
            instagramUrl: 'BBBBBB',
            twitterUrl: 'BBBBBB',
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

      it('should return a list of Supporter', () => {
        const returnedFromService = Object.assign(
          {
            salesforceUID: 'BBBBBB',
            ageCategory: true,
            supporterSalutation: 'BBBBBB',
            firstName: 'BBBBBB',
            secondName: 'BBBBBB',
            email: 'BBBBBB',
            streetAddress: 'BBBBBB',
            city: 'BBBBBB',
            county: 'BBBBBB',
            postcode: 'BBBBBB',
            country: 'BBBBBB',
            supporterPicture: 'BBBBBB',
            contactableByEmail: true,
            contactableByPost: true,
            locationCoordinateX: 1,
            locationCoordinateY: 1,
            facebookUrl: 'BBBBBB',
            instagramUrl: 'BBBBBB',
            twitterUrl: 'BBBBBB',
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

      it('should delete a Supporter', () => {
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
