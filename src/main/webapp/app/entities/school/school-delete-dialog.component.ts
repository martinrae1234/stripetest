import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ISchool } from 'app/shared/model/school.model';
import { SchoolService } from './school.service';

@Component({
  selector: 'jhi-school-delete-dialog',
  templateUrl: './school-delete-dialog.component.html'
})
export class SchoolDeleteDialogComponent {
  school: ISchool;

  constructor(protected schoolService: SchoolService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.schoolService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'schoolListModification',
        content: 'Deleted an school'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-school-delete-popup',
  template: ''
})
export class SchoolDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ school }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(SchoolDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.school = school;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/school', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/school', { outlets: { popup: null } }]);
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
