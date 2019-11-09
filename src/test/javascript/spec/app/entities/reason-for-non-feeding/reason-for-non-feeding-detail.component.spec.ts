import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { WebTestModule } from '../../../test.module';
import { ReasonForNonFeedingDetailComponent } from 'app/entities/reason-for-non-feeding/reason-for-non-feeding-detail.component';
import { ReasonForNonFeeding } from 'app/shared/model/reason-for-non-feeding.model';

describe('Component Tests', () => {
  describe('ReasonForNonFeeding Management Detail Component', () => {
    let comp: ReasonForNonFeedingDetailComponent;
    let fixture: ComponentFixture<ReasonForNonFeedingDetailComponent>;
    const route = ({ data: of({ reasonForNonFeeding: new ReasonForNonFeeding(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [WebTestModule],
        declarations: [ReasonForNonFeedingDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(ReasonForNonFeedingDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ReasonForNonFeedingDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.reasonForNonFeeding).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
