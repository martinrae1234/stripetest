import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { WebTestModule } from '../../../test.module';
import { NewsComponent } from 'app/entities/news/news.component';
import { NewsService } from 'app/entities/news/news.service';
import { News } from 'app/shared/model/news.model';

describe('Component Tests', () => {
  describe('News Management Component', () => {
    let comp: NewsComponent;
    let fixture: ComponentFixture<NewsComponent>;
    let service: NewsService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [WebTestModule],
        declarations: [NewsComponent],
        providers: []
      })
        .overrideTemplate(NewsComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(NewsComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(NewsService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new News(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.news[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
