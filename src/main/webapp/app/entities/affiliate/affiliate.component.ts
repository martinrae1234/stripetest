import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { filter, map } from 'rxjs/operators';
import { JhiEventManager } from 'ng-jhipster';

import { IAffiliate } from 'app/shared/model/affiliate.model';
import { AccountService } from 'app/core/auth/account.service';
import { AffiliateService } from './affiliate.service';

@Component({
  selector: 'jhi-affiliate',
  templateUrl: './affiliate.component.html'
})
export class AffiliateComponent implements OnInit, OnDestroy {
  affiliates: IAffiliate[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected affiliateService: AffiliateService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.affiliateService
      .query()
      .pipe(
        filter((res: HttpResponse<IAffiliate[]>) => res.ok),
        map((res: HttpResponse<IAffiliate[]>) => res.body)
      )
      .subscribe((res: IAffiliate[]) => {
        this.affiliates = res;
      });
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().subscribe(account => {
      this.currentAccount = account;
    });
    this.registerChangeInAffiliates();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IAffiliate) {
    return item.id;
  }

  registerChangeInAffiliates() {
    this.eventSubscriber = this.eventManager.subscribe('affiliateListModification', response => this.loadAll());
  }
}
