import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IDonation } from 'app/shared/model/donation.model';
import { DonationService } from './donation.service';

@Component({
  selector: 'jhi-donation-delete-dialog',
  templateUrl: './donation-delete-dialog.component.html'
})
export class DonationDeleteDialogComponent {
  donation: IDonation;

  constructor(protected donationService: DonationService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.donationService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'donationListModification',
        content: 'Deleted an donation'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-donation-delete-popup',
  template: ''
})
export class DonationDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ donation }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(DonationDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.donation = donation;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/donation', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/donation', { outlets: { popup: null } }]);
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
