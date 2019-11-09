import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { NewsService } from 'app/entities/news/news.service';
import { INews, News } from 'app/shared/model/news.model';

describe('Service Tests', () => {
  describe('News Service', () => {
    let injector: TestBed;
    let service: NewsService;
    let httpMock: HttpTestingController;
    let elemDefault: INews;
    let expectedResult;
    let currentDate: moment.Moment;
    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = {};
      injector = getTestBed();
      service = injector.get(NewsService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new News(
        0,
        false,
        'image/png',
        'AAAAAAA',
        currentDate,
        'AAAAAAA',
        'AAAAAAA',
        'image/png',
        'AAAAAAA',
        'AAAAAAA',
        'image/png',
        'AAAAAAA',
        'AAAAAAA',
        'image/png',
        'AAAAAAA',
        'image/png',
        'AAAAAAA',
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
            dateOfNewsCreation: currentDate.format(DATE_TIME_FORMAT),
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

      it('should create a News', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            dateOfNewsCreation: currentDate.format(DATE_TIME_FORMAT),
            createdDate: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            dateOfNewsCreation: currentDate,
            createdDate: currentDate
          },
          returnedFromService
        );
        service
          .create(new News(null))
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));
        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: expected });
      });

      it('should update a News', () => {
        const returnedFromService = Object.assign(
          {
            activeMessage: true,
            bannerPicture: 'BBBBBB',
            dateOfNewsCreation: currentDate.format(DATE_TIME_FORMAT),
            bannerName: 'BBBBBB',
            descriptionOne: 'BBBBBB',
            pictureOne: 'BBBBBB',
            descriptionTwo: 'BBBBBB',
            pictureTwo: 'BBBBBB',
            descriptionThree: 'BBBBBB',
            pictureThree: 'BBBBBB',
            bottomPicture: 'BBBBBB',
            bottomDescription: 'BBBBBB',
            buttonText: 'BBBBBB',
            buttonURL: 'BBBBBB',
            createdByUserId: 1,
            createdDate: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            dateOfNewsCreation: currentDate,
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

      it('should return a list of News', () => {
        const returnedFromService = Object.assign(
          {
            activeMessage: true,
            bannerPicture: 'BBBBBB',
            dateOfNewsCreation: currentDate.format(DATE_TIME_FORMAT),
            bannerName: 'BBBBBB',
            descriptionOne: 'BBBBBB',
            pictureOne: 'BBBBBB',
            descriptionTwo: 'BBBBBB',
            pictureTwo: 'BBBBBB',
            descriptionThree: 'BBBBBB',
            pictureThree: 'BBBBBB',
            bottomPicture: 'BBBBBB',
            bottomDescription: 'BBBBBB',
            buttonText: 'BBBBBB',
            buttonURL: 'BBBBBB',
            createdByUserId: 1,
            createdDate: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            dateOfNewsCreation: currentDate,
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

      it('should delete a News', () => {
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
