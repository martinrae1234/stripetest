import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { IDelivery } from 'app/shared/model/delivery.model';

@Component({
  selector: 'jhi-delivery-detail',
  templateUrl: './delivery-detail.component.html'
})
export class DeliveryDetailComponent implements OnInit {
  delivery: IDelivery;

  constructor(protected dataUtils: JhiDataUtils, protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ delivery }) => {
      this.delivery = delivery;
    });
  }

  byteSize(field) {
    return this.dataUtils.byteSize(field);
  }

  openFile(contentType, field) {
    return this.dataUtils.openFile(contentType, field);
  }
  previousState() {
    window.history.back();
  }
}
