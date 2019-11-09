import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { WebTestModule } from '../../../test.module';
import { FurtherInfoDeleteDialogComponent } from 'app/entities/further-info/further-info-delete-dialog.component';
import { FurtherInfoService } from 'app/entities/further-info/further-info.service';

describe('Component Tests', () => {
  describe('FurtherInfo Management Delete Component', () => {
    let comp: FurtherInfoDeleteDialogComponent;
    let fixture: ComponentFixture<FurtherInfoDeleteDialogComponent>;
    let service: FurtherInfoService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [WebTestModule],
        declarations: [FurtherInfoDeleteDialogComponent]
      })
        .overrideTemplate(FurtherInfoDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(FurtherInfoDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(FurtherInfoService);
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
