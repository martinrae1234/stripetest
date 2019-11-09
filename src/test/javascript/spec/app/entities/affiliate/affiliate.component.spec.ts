import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { WebTestModule } from '../../../test.module';
import { AffiliateComponent } from 'app/entities/affiliate/affiliate.component';
import { AffiliateService } from 'app/entities/affiliate/affiliate.service';
import { Affiliate } from 'app/shared/model/affiliate.model';

describe('Component Tests', () => {
  describe('Affiliate Management Component', () => {
    let comp: AffiliateComponent;
    let fixture: ComponentFixture<AffiliateComponent>;
    let service: AffiliateService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [WebTestModule],
        declarations: [AffiliateComponent],
        providers: []
      })
        .overrideTemplate(AffiliateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(AffiliateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AffiliateService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Affiliate(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.affiliates[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
