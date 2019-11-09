import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { IFurtherInfo } from 'app/shared/model/further-info.model';

@Component({
  selector: 'jhi-further-info-detail',
  templateUrl: './further-info-detail.component.html',
  styleUrls: ['further-info-detail.scss']
})
export class FurtherInfoDetailComponent implements OnInit {
  furtherInfo: IFurtherInfo;

  constructor(protected dataUtils: JhiDataUtils, protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ furtherInfo }) => {
      this.furtherInfo = furtherInfo;
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
