import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { WebTestModule } from '../../../test.module';
import { CommodityUpdateComponent } from 'app/entities/commodity/commodity-update.component';
import { CommodityService } from 'app/entities/commodity/commodity.service';
import { Commodity } from 'app/shared/model/commodity.model';

describe('Component Tests', () => {
  describe('Commodity Management Update Component', () => {
    let comp: CommodityUpdateComponent;
    let fixture: ComponentFixture<CommodityUpdateComponent>;
    let service: CommodityService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [WebTestModule],
        declarations: [CommodityUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(CommodityUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CommodityUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CommodityService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Commodity(123);
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
        const entity = new Commodity();
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
