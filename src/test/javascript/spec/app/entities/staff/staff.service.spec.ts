import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { StaffService } from 'app/entities/staff/staff.service';
import { IStaff, Staff } from 'app/shared/model/staff.model';
import { TypeOfStaff } from 'app/shared/model/enumerations/type-of-staff.model';

describe('Service Tests', () => {
  describe('Staff Service', () => {
    let injector: TestBed;
    let service: StaffService;
    let httpMock: HttpTestingController;
    let elemDefault: IStaff;
    let expectedResult;
    let currentDate: moment.Moment;
    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = {};
      injector = getTestBed();
      service = injector.get(StaffService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new Staff(
        0,
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        TypeOfStaff.STAFF,
        0,
        0,
        'image/png',
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

      it('should create a Staff', () => {
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
          .create(new Staff(null))
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));
        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: expected });
      });

      it('should update a Staff', () => {
        const returnedFromService = Object.assign(
          {
            salesforceUID: 'BBBBBB',
            firstName: 'BBBBBB',
            secondName: 'BBBBBB',
            affiliate: 'BBBBBB',
            typeOfStaff: 'BBBBBB',
            locationCoordinateX: 1,
            locationCoordinateY: 1,
            staffPicture: 'BBBBBB',
            position: 'BBBBBB',
            description: 'BBBBBB',
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

      it('should return a list of Staff', () => {
        const returnedFromService = Object.assign(
          {
            salesforceUID: 'BBBBBB',
            firstName: 'BBBBBB',
            secondName: 'BBBBBB',
            affiliate: 'BBBBBB',
            typeOfStaff: 'BBBBBB',
            locationCoordinateX: 1,
            locationCoordinateY: 1,
            staffPicture: 'BBBBBB',
            position: 'BBBBBB',
            description: 'BBBBBB',
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

      it('should delete a Staff', () => {
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
