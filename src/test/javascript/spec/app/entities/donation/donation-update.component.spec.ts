import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { WebTestModule } from '../../../test.module';
import { DonationUpdateComponent } from 'app/entities/donation/donation-update.component';
import { DonationService } from 'app/entities/donation/donation.service';
import { Donation } from 'app/shared/model/donation.model';

describe('Component Tests', () => {
  describe('Donation Management Update Component', () => {
    let comp: DonationUpdateComponent;
    let fixture: ComponentFixture<DonationUpdateComponent>;
    let service: DonationService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [WebTestModule],
        declarations: [DonationUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(DonationUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(DonationUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(DonationService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Donation(123);
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
        const entity = new Donation();
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
