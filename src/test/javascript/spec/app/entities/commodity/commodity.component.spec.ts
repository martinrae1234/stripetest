import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { WebTestModule } from '../../../test.module';
import { CommodityComponent } from 'app/entities/commodity/commodity.component';
import { CommodityService } from 'app/entities/commodity/commodity.service';
import { Commodity } from 'app/shared/model/commodity.model';

describe('Component Tests', () => {
  describe('Commodity Management Component', () => {
    let comp: CommodityComponent;
    let fixture: ComponentFixture<CommodityComponent>;
    let service: CommodityService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [WebTestModule],
        declarations: [CommodityComponent],
        providers: []
      })
        .overrideTemplate(CommodityComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CommodityComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CommodityService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Commodity(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.commodities[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
