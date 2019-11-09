import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IReasonForNonFeeding } from 'app/shared/model/reason-for-non-feeding.model';

@Component({
  selector: 'jhi-reason-for-non-feeding-detail',
  templateUrl: './reason-for-non-feeding-detail.component.html'
})
export class ReasonForNonFeedingDetailComponent implements OnInit {
  reasonForNonFeeding: IReasonForNonFeeding;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ reasonForNonFeeding }) => {
      this.reasonForNonFeeding = reasonForNonFeeding;
    });
  }

  previousState() {
    window.history.back();
  }
}
