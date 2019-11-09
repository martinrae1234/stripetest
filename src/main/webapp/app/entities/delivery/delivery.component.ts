import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiDataUtils } from 'ng-jhipster';

import { IDelivery } from 'app/shared/model/delivery.model';
import { AccountService } from 'app/core/auth/account.service';
import { DeliveryService } from './delivery.service';

@Component({
  selector: 'jhi-delivery',
  templateUrl: './delivery.component.html'
})
export class DeliveryComponent implements OnInit, OnDestroy {
  deliveries: IDelivery[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected deliveryService: DeliveryService,
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.deliveryService
      .query()
      .pipe(
        filter((res: HttpResponse<IDelivery[]>) => res.ok),
        map((res: HttpResponse<IDelivery[]>) => res.body)
      )
      .subscribe((res: IDelivery[]) => {
        this.deliveries = res;
      });
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().subscribe(account => {
      this.currentAccount = account;
    });
    this.registerChangeInDeliveries();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IDelivery) {
    return item.id;
  }

  byteSize(field) {
    return this.dataUtils.byteSize(field);
  }

  openFile(contentType, field) {
    return this.dataUtils.openFile(contentType, field);
  }

  registerChangeInDeliveries() {
    this.eventSubscriber = this.eventManager.subscribe('deliveryListModification', response => this.loadAll());
  }
}
