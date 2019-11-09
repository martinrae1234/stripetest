import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { INews } from 'app/shared/model/news.model';

@Component({
  selector: 'jhi-news-detail',
  templateUrl: './news-detail.component.html',
  styleUrls: ['news-detail.scss']
})
export class NewsDetailComponent implements OnInit {
  news: INews;

  constructor(protected dataUtils: JhiDataUtils, protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ news }) => {
      this.news = news;
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
