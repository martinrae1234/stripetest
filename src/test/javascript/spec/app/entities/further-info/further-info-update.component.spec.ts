import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { WebTestModule } from '../../../test.module';
import { FurtherInfoUpdateComponent } from 'app/entities/further-info/further-info-update.component';
import { FurtherInfoService } from 'app/entities/further-info/further-info.service';
import { FurtherInfo } from 'app/shared/model/further-info.model';

describe('Component Tests', () => {
  describe('FurtherInfo Management Update Component', () => {
    let comp: FurtherInfoUpdateComponent;
    let fixture: ComponentFixture<FurtherInfoUpdateComponent>;
    let service: FurtherInfoService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [WebTestModule],
        declarations: [FurtherInfoUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(FurtherInfoUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(FurtherInfoUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(FurtherInfoService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new FurtherInfo(123);
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
        const entity = new FurtherInfo();
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
