import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IReasonForNonFeeding } from 'app/shared/model/reason-for-non-feeding.model';
import { ReasonForNonFeedingService } from './reason-for-non-feeding.service';

@Component({
  selector: 'jhi-reason-for-non-feeding-delete-dialog',
  templateUrl: './reason-for-non-feeding-delete-dialog.component.html'
})
export class ReasonForNonFeedingDeleteDialogComponent {
  reasonForNonFeeding: IReasonForNonFeeding;

  constructor(
    protected reasonForNonFeedingService: ReasonForNonFeedingService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.reasonForNonFeedingService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'reasonForNonFeedingListModification',
        content: 'Deleted an reasonForNonFeeding'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-reason-for-non-feeding-delete-popup',
  template: ''
})
export class ReasonForNonFeedingDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ reasonForNonFeeding }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(ReasonForNonFeedingDeleteDialogComponent as Component, {
          size: 'lg',
          backdrop: 'static'
        });
        this.ngbModalRef.componentInstance.reasonForNonFeeding = reasonForNonFeeding;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/reason-for-non-feeding', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/reason-for-non-feeding', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          }
        );
      }, 0);
    });
  }

  ngOnDestroy() {
    this.ngbModalRef = null;
  }
}
