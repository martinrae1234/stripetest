import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiDataUtils } from 'ng-jhipster';

import { IMessage } from 'app/shared/model/message.model';
import { AccountService } from 'app/core/auth/account.service';
import { MessageService } from './message.service';

@Component({
  selector: 'jhi-message-home',
  templateUrl: './message-home.component.html',
  styleUrls: ['message-home.scss']
})
export class MessageHomeComponent implements OnInit, OnDestroy {
  messages: IMessage[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected messageService: MessageService,
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.messageService
      .query()
      .pipe(
        filter((res: HttpResponse<IMessage[]>) => res.ok),
        map((res: HttpResponse<IMessage[]>) => res.body)
      )
      .subscribe((res: IMessage[]) => {
        this.messages = res;
      });
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().subscribe(account => {
      this.currentAccount = account;
    });
    this.registerChangeInMessages();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IMessage) {
    return item.id;
  }

  byteSize(field) {
    return this.dataUtils.byteSize(field);
  }

  openFile(contentType, field) {
    return this.dataUtils.openFile(contentType, field);
  }

  registerChangeInMessages() {
    this.eventSubscriber = this.eventManager.subscribe('messageListModification', response => this.loadAll());
  }
}
