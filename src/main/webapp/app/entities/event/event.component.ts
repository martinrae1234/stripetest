import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiDataUtils } from 'ng-jhipster';

import { IEvent } from 'app/shared/model/event.model';
import { AccountService } from 'app/core/auth/account.service';
import { EventService } from './event.service';

@Component({
  selector: 'jhi-event',
  templateUrl: './event.component.html'
})
export class EventComponent implements OnInit, OnDestroy {
  events: IEvent[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected eventService: EventService,
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.eventService
      .query()
      .pipe(
        filter((res: HttpResponse<IEvent[]>) => res.ok),
        map((res: HttpResponse<IEvent[]>) => res.body)
      )
      .subscribe((res: IEvent[]) => {
        this.events = res;
      });
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().subscribe(account => {
      this.currentAccount = account;
    });
    this.registerChangeInEvents();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IEvent) {
    return item.id;
  }

  byteSize(field) {
    return this.dataUtils.byteSize(field);
  }

  openFile(contentType, field) {
    return this.dataUtils.openFile(contentType, field);
  }

  registerChangeInEvents() {
    this.eventSubscriber = this.eventManager.subscribe('eventListModification', response => this.loadAll());
  }
}
