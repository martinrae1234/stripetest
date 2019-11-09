import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiDataUtils } from 'ng-jhipster';

import { IFurtherInfo } from 'app/shared/model/further-info.model';
import { AccountService } from 'app/core/auth/account.service';
import { FurtherInfoService } from './further-info.service';

@Component({
  selector: 'jhi-further-info',
  templateUrl: './further-info.component.html'
})
export class FurtherInfoComponent implements OnInit, OnDestroy {
  furtherInfos: IFurtherInfo[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected furtherInfoService: FurtherInfoService,
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.furtherInfoService
      .query()
      .pipe(
        filter((res: HttpResponse<IFurtherInfo[]>) => res.ok),
        map((res: HttpResponse<IFurtherInfo[]>) => res.body)
      )
      .subscribe((res: IFurtherInfo[]) => {
        this.furtherInfos = res;
      });
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().subscribe(account => {
      this.currentAccount = account;
    });
    this.registerChangeInFurtherInfos();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IFurtherInfo) {
    return item.id;
  }

  byteSize(field) {
    return this.dataUtils.byteSize(field);
  }

  openFile(contentType, field) {
    return this.dataUtils.openFile(contentType, field);
  }

  registerChangeInFurtherInfos() {
    this.eventSubscriber = this.eventManager.subscribe('furtherInfoListModification', response => this.loadAll());
  }
}
