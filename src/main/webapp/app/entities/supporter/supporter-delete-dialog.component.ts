import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ISupporter } from 'app/shared/model/supporter.model';
import { SupporterService } from './supporter.service';

@Component({
  selector: 'jhi-supporter-delete-dialog',
  templateUrl: './supporter-delete-dialog.component.html'
})
export class SupporterDeleteDialogComponent {
  supporter: ISupporter;

  constructor(protected supporterService: SupporterService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.supporterService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'supporterListModification',
        content: 'Deleted an supporter'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-supporter-delete-popup',
  template: ''
})
export class SupporterDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ supporter }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(SupporterDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.supporter = supporter;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/supporter', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/supporter', { outlets: { popup: null } }]);
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
