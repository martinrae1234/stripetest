import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICommodity } from 'app/shared/model/commodity.model';

@Component({
  selector: 'jhi-commodity-detail',
  templateUrl: './commodity-detail.component.html'
})
export class CommodityDetailComponent implements OnInit {
  commodity: ICommodity;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ commodity }) => {
      this.commodity = commodity;
    });
  }

  previousState() {
    window.history.back();
  }
}
