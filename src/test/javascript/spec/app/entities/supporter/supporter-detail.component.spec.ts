import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { WebTestModule } from '../../../test.module';
import { SupporterDetailComponent } from 'app/entities/supporter/supporter-detail.component';
import { Supporter } from 'app/shared/model/supporter.model';

describe('Component Tests', () => {
  describe('Supporter Management Detail Component', () => {
    let comp: SupporterDetailComponent;
    let fixture: ComponentFixture<SupporterDetailComponent>;
    const route = ({ data: of({ supporter: new Supporter(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [WebTestModule],
        declarations: [SupporterDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(SupporterDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(SupporterDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.supporter).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
