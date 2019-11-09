import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { filter, map } from 'rxjs/operators';
import { JhiEventManager } from 'ng-jhipster';

import { IReasonForNonFeeding } from 'app/shared/model/reason-for-non-feeding.model';
import { AccountService } from 'app/core/auth/account.service';
import { ReasonForNonFeedingService } from './reason-for-non-feeding.service';

@Component({
  selector: 'jhi-reason-for-non-feeding',
  templateUrl: './reason-for-non-feeding.component.html'
})
export class ReasonForNonFeedingComponent implements OnInit, OnDestroy {
  reasonForNonFeedings: IReasonForNonFeeding[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected reasonForNonFeedingService: ReasonForNonFeedingService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.reasonForNonFeedingService
      .query()
      .pipe(
        filter((res: HttpResponse<IReasonForNonFeeding[]>) => res.ok),
        map((res: HttpResponse<IReasonForNonFeeding[]>) => res.body)
      )
      .subscribe((res: IReasonForNonFeeding[]) => {
        this.reasonForNonFeedings = res;
      });
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().subscribe(account => {
      this.currentAccount = account;
    });
    this.registerChangeInReasonForNonFeedings();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IReasonForNonFeeding) {
    return item.id;
  }

  registerChangeInReasonForNonFeedings() {
    this.eventSubscriber = this.eventManager.subscribe('reasonForNonFeedingListModification', response => this.loadAll());
  }
}
