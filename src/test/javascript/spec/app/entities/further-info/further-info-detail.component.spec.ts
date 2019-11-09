import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { WebTestModule } from '../../../test.module';
import { FurtherInfoDetailComponent } from 'app/entities/further-info/further-info-detail.component';
import { FurtherInfo } from 'app/shared/model/further-info.model';

describe('Component Tests', () => {
  describe('FurtherInfo Management Detail Component', () => {
    let comp: FurtherInfoDetailComponent;
    let fixture: ComponentFixture<FurtherInfoDetailComponent>;
    const route = ({ data: of({ furtherInfo: new FurtherInfo(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [WebTestModule],
        declarations: [FurtherInfoDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(FurtherInfoDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(FurtherInfoDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.furtherInfo).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
