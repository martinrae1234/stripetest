import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { WebTestModule } from '../../../test.module';
import { SupporterDeleteDialogComponent } from 'app/entities/supporter/supporter-delete-dialog.component';
import { SupporterService } from 'app/entities/supporter/supporter.service';

describe('Component Tests', () => {
  describe('Supporter Management Delete Component', () => {
    let comp: SupporterDeleteDialogComponent;
    let fixture: ComponentFixture<SupporterDeleteDialogComponent>;
    let service: SupporterService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [WebTestModule],
        declarations: [SupporterDeleteDialogComponent]
      })
        .overrideTemplate(SupporterDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(SupporterDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(SupporterService);
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
