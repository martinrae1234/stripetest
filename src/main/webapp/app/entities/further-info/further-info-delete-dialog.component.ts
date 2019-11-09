import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IFurtherInfo } from 'app/shared/model/further-info.model';
import { FurtherInfoService } from './further-info.service';

@Component({
  selector: 'jhi-further-info-delete-dialog',
  templateUrl: './further-info-delete-dialog.component.html'
})
export class FurtherInfoDeleteDialogComponent {
  furtherInfo: IFurtherInfo;

  constructor(
    protected furtherInfoService: FurtherInfoService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.furtherInfoService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'furtherInfoListModification',
        content: 'Deleted an furtherInfo'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-further-info-delete-popup',
  template: ''
})
export class FurtherInfoDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ furtherInfo }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(FurtherInfoDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.furtherInfo = furtherInfo;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/further-info', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/further-info', { outlets: { popup: null } }]);
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
