import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { WebTestModule } from '../../../test.module';
import { CommodityDetailComponent } from 'app/entities/commodity/commodity-detail.component';
import { Commodity } from 'app/shared/model/commodity.model';

describe('Component Tests', () => {
  describe('Commodity Management Detail Component', () => {
    let comp: CommodityDetailComponent;
    let fixture: ComponentFixture<CommodityDetailComponent>;
    const route = ({ data: of({ commodity: new Commodity(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [WebTestModule],
        declarations: [CommodityDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(CommodityDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(CommodityDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.commodity).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
