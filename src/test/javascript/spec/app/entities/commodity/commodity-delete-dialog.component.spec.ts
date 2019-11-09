import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { WebTestModule } from '../../../test.module';
import { CommodityDeleteDialogComponent } from 'app/entities/commodity/commodity-delete-dialog.component';
import { CommodityService } from 'app/entities/commodity/commodity.service';

describe('Component Tests', () => {
  describe('Commodity Management Delete Component', () => {
    let comp: CommodityDeleteDialogComponent;
    let fixture: ComponentFixture<CommodityDeleteDialogComponent>;
    let service: CommodityService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [WebTestModule],
        declarations: [CommodityDeleteDialogComponent]
      })
        .overrideTemplate(CommodityDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(CommodityDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CommodityService);
      mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
      mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
    });

    describe('confirmDelete', () => {
      it('Should call delete service on confirmDelete', inject(
        [],
        fakeAsync(() => {
          // GIVEN
          spyOn(service, 'delete').and.returnValue(of({}));

          // WHEN
          comp.confirmDelete(123);
          tick();

          // THEN
          expect(service.delete).toHaveBeenCalledWith(123);
          expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
          expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
        })
      ));
    });
  });
});
