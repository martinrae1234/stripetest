import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { ISupporter } from 'app/shared/model/supporter.model';

@Component({
  selector: 'jhi-supporter-detail',
  templateUrl: './supporter-detail.component.html'
})
export class SupporterDetailComponent implements OnInit {
  supporter: ISupporter;

  constructor(protected dataUtils: JhiDataUtils, protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ supporter }) => {
      this.supporter = supporter;
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
