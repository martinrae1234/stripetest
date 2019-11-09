import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { WebTestModule } from '../../../test.module';
import { ReasonForNonFeedingComponent } from 'app/entities/reason-for-non-feeding/reason-for-non-feeding.component';
import { ReasonForNonFeedingService } from 'app/entities/reason-for-non-feeding/reason-for-non-feeding.service';
import { ReasonForNonFeeding } from 'app/shared/model/reason-for-non-feeding.model';

describe('Component Tests', () => {
  describe('ReasonForNonFeeding Management Component', () => {
    let comp: ReasonForNonFeedingComponent;
    let fixture: ComponentFixture<ReasonForNonFeedingComponent>;
    let service: ReasonForNonFeedingService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [WebTestModule],
        declarations: [ReasonForNonFeedingComponent],
        providers: []
      })
        .overrideTemplate(ReasonForNonFeedingComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ReasonForNonFeedingComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ReasonForNonFeedingService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new ReasonForNonFeeding(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.reasonForNonFeedings[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
