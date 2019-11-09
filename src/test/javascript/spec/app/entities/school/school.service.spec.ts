import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_FORMAT, DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { SchoolService } from 'app/entities/school/school.service';
import { ISchool, School } from 'app/shared/model/school.model';

describe('Service Tests', () => {
  describe('School Service', () => {
    let injector: TestBed;
    let service: SchoolService;
    let httpMock: HttpTestingController;
    let elemDefault: ISchool;
    let expectedResult;
    let currentDate: moment.Moment;
    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = {};
      injector = getTestBed();
      service = injector.get(SchoolService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new School(
        0,
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        false,
        0,
        0,
        0,
        0,
        0,
        0,
        'image/png',
        'AAAAAAA',
        'AAAAAAA',
        'image/png',
        'AAAAAAA',
        'image/png',
        'AAAAAAA',
        'image/png',
        'AAAAAAA',
        'image/png',
        'AAAAAAA',
        currentDate,
        0,
        currentDate
      );
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            dateOfLastStockCheck: currentDate.format(DATE_FORMAT),
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

      it('should create a School', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            dateOfLastStockCheck: currentDate.format(DATE_FORMAT),
            createdDate: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            dateOfLastStockCheck: currentDate,
            createdDate: currentDate
          },
          returnedFromService
        );
        service
          .create(new School(null))
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));
        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: expected });
      });

      it('should update a School', () => {
        const returnedFromService = Object.assign(
          {
            salesforceUID: 'BBBBBB',
            legacySchoolID: 'BBBBBB',
            schoolName: 'BBBBBB',
            sponsored: true,
            attendanceTotal: 1,
            attendanceBoys: 1,
            attendanceGirls: 1,
            enrolmentTotal: 1,
            locationCoordinateX: 1,
            locationCoordinateY: 1,
            imageBanner: 'BBBBBB',
            textBanner: 'BBBBBB',
            imageOne: 'BBBBBB',
            imageTwo: 'BBBBBB',
            imageThree: 'BBBBBB',
            imageFour: 'BBBBBB',
            dateOfLastStockCheck: currentDate.format(DATE_FORMAT),
            createdByUserId: 1,
            createdDate: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            dateOfLastStockCheck: currentDate,
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

      it('should return a list of School', () => {
        const returnedFromService = Object.assign(
          {
            salesforceUID: 'BBBBBB',
            legacySchoolID: 'BBBBBB',
            schoolName: 'BBBBBB',
            sponsored: true,
            attendanceTotal: 1,
            attendanceBoys: 1,
            attendanceGirls: 1,
            enrolmentTotal: 1,
            locationCoordinateX: 1,
            locationCoordinateY: 1,
            imageBanner: 'BBBBBB',
            textBanner: 'BBBBBB',
            imageOne: 'BBBBBB',
            imageTwo: 'BBBBBB',
            imageThree: 'BBBBBB',
            imageFour: 'BBBBBB',
            dateOfLastStockCheck: currentDate.format(DATE_FORMAT),
            createdByUserId: 1,
            createdDate: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            dateOfLastStockCheck: currentDate,
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

      it('should delete a School', () => {
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
