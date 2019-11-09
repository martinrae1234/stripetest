import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { WebTestModule } from '../../../test.module';
import { HomePageDeleteDialogComponent } from 'app/entities/home-page/home-page-delete-dialog.component';
import { HomePageService } from 'app/entities/home-page/home-page.service';

describe('Component Tests', () => {
  describe('HomePage Management Delete Component', () => {
    let comp: HomePageDeleteDialogComponent;
    let fixture: ComponentFixture<HomePageDeleteDialogComponent>;
    let service: HomePageService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [WebTestModule],
        declarations: [HomePageDeleteDialogComponent]
      })
        .overrideTemplate(HomePageDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(HomePageDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(HomePageService);
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
