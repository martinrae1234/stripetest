import { Component, OnInit, OnDestroy, ViewChild } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiDataUtils } from 'ng-jhipster';
import { NgbCarousel, NgbSlideEvent, NgbSlideEventSource } from '@ng-bootstrap/ng-bootstrap';

import { INews } from 'app/shared/model/news.model';
import { AccountService } from 'app/core/auth/account.service';
import { NewsService } from './news.service';

@Component({
  selector: 'jhi-news-home',
  templateUrl: './news-home.component.html',
  styleUrls: ['news-home.scss']
})
export class NewsHomeComponent implements OnInit, OnDestroy {
  news: INews[];
  currentAccount: any;
  eventSubscriber: Subscription;
  paused = false;
  unpauseOnArrow = false;
  pauseOnIndicator = false;
  pauseOnHover = false;

@ViewChild('carousel', {static:true}) carousel: NgbCarousel;


  constructor(
    protected newsService: NewsService,
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}


togglePaused(){
  if(this.paused) {
    this.carousel.cycle();
  } else {
    this.carousel.pause();
  }
  this.paused = !this.paused;
}

  onSlide(slideEvent: NgbSlideEvent) {
    if (this.unpauseOnArrow && slideEvent.paused &&
      (slideEvent.source === NgbSlideEventSource.ARROW_LEFT || slideEvent.source === NgbSlideEventSource.ARROW_RIGHT)) {
      this.togglePaused();
    }
    if (this.pauseOnIndicator && !slideEvent.paused && slideEvent.source === NgbSlideEventSource.INDICATOR) {
      this.togglePaused();
    }
  }


  loadAll() {
    this.newsService
      .query()
      .pipe(
        filter((res: HttpResponse<INews[]>) => res.ok),
        map((res: HttpResponse<INews[]>) => res.body)
      )
      .subscribe((res: INews[]) => {
        this.news = res;
      });
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().subscribe(account => {
      this.currentAccount = account;
    });
    this.registerChangeInNews();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: INews) {
    return item.id;
  }

  byteSize(field) {
    return this.dataUtils.byteSize(field);
  }

  openFile(contentType, field) {
    return this.dataUtils.openFile(contentType, field);
  }

  registerChangeInNews() {
    this.eventSubscriber = this.eventManager.subscribe('newsListModification', response => this.loadAll());
  }
}







