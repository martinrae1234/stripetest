import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { filter, map } from 'rxjs/operators';
import { JhiEventManager } from 'ng-jhipster';

import { ICommodity } from 'app/shared/model/commodity.model';
import { AccountService } from 'app/core/auth/account.service';
import { CommodityService } from './commodity.service';

@Component({
  selector: 'jhi-commodity',
  templateUrl: './commodity.component.html'
})
export class CommodityComponent implements OnInit, OnDestroy {
  commodities: ICommodity[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected commodityService: CommodityService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.commodityService
      .query()
      .pipe(
        filter((res: HttpResponse<ICommodity[]>) => res.ok),
        map((res: HttpResponse<ICommodity[]>) => res.body)
      )
      .subscribe((res: ICommodity[]) => {
        this.commodities = res;
      });
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().subscribe(account => {
      this.currentAccount = account;
    });
    this.registerChangeInCommodities();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: ICommodity) {
    return item.id;
  }

  registerChangeInCommodities() {
    this.eventSubscriber = this.eventManager.subscribe('commodityListModification', response => this.loadAll());
  }
}
