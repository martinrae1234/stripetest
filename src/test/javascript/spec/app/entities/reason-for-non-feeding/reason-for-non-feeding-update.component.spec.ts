import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { WebTestModule } from '../../../test.module';
import { ReasonForNonFeedingUpdateComponent } from 'app/entities/reason-for-non-feeding/reason-for-non-feeding-update.component';
import { ReasonForNonFeedingService } from 'app/entities/reason-for-non-feeding/reason-for-non-feeding.service';
import { ReasonForNonFeeding } from 'app/shared/model/reason-for-non-feeding.model';

describe('Component Tests', () => {
  describe('ReasonForNonFeeding Management Update Component', () => {
    let comp: ReasonForNonFeedingUpdateComponent;
    let fixture: ComponentFixture<ReasonForNonFeedingUpdateComponent>;
    let service: ReasonForNonFeedingService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [WebTestModule],
        declarations: [ReasonForNonFeedingUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(ReasonForNonFeedingUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ReasonForNonFeedingUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ReasonForNonFeedingService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new ReasonForNonFeeding(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new ReasonForNonFeeding();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
