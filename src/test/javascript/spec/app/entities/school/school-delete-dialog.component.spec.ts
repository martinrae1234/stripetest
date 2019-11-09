import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { WebTestModule } from '../../../test.module';
import { SchoolDeleteDialogComponent } from 'app/entities/school/school-delete-dialog.component';
import { SchoolService } from 'app/entities/school/school.service';

describe('Component Tests', () => {
  describe('School Management Delete Component', () => {
    let comp: SchoolDeleteDialogComponent;
    let fixture: ComponentFixture<SchoolDeleteDialogComponent>;
    let service: SchoolService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [WebTestModule],
        declarations: [SchoolDeleteDialogComponent]
      })
        .overrideTemplate(SchoolDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(SchoolDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(SchoolService);
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
