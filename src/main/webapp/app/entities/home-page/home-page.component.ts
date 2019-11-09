import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { filter, map } from 'rxjs/operators';
import { JhiEventManager } from 'ng-jhipster';

import { IHomePage } from 'app/shared/model/home-page.model';
import { AccountService } from 'app/core/auth/account.service';
import { HomePageService } from './home-page.service';

@Component({
  selector: 'jhi-home-page',
  templateUrl: './home-page.component.html'
})
export class HomePageComponent implements OnInit, OnDestroy {
  homePages: IHomePage[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected homePageService: HomePageService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.homePageService
      .query()
      .pipe(
        filter((res: HttpResponse<IHomePage[]>) => res.ok),
        map((res: HttpResponse<IHomePage[]>) => res.body)
      )
      .subscribe((res: IHomePage[]) => {
        this.homePages = res;
      });
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().subscribe(account => {
      this.currentAccount = account;
    });
    this.registerChangeInHomePages();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IHomePage) {
    return item.id;
  }

  registerChangeInHomePages() {
    this.eventSubscriber = this.eventManager.subscribe('homePageListModification', response => this.loadAll());
  }
}
