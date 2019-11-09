import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ICommodity } from 'app/shared/model/commodity.model';
import { CommodityService } from './commodity.service';

@Component({
  selector: 'jhi-commodity-delete-dialog',
  templateUrl: './commodity-delete-dialog.component.html'
})
export class CommodityDeleteDialogComponent {
  commodity: ICommodity;

  constructor(protected commodityService: CommodityService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.commodityService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'commodityListModification',
        content: 'Deleted an commodity'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-commodity-delete-popup',
  template: ''
})
export class CommodityDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ commodity }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(CommodityDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.commodity = commodity;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/commodity', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/commodity', { outlets: { popup: null } }]);
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
