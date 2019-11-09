import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { IAffiliate, Affiliate } from 'app/shared/model/affiliate.model';
import { AffiliateService } from './affiliate.service';

@Component({
  selector: 'jhi-affiliate-update',
  templateUrl: './affiliate-update.component.html'
})
export class AffiliateUpdateComponent implements OnInit {
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    salesforceUID: [],
    affiliateName: [],
    currency: [],
    streetAddress: [null, [Validators.required]],
    city: [null, [Validators.required]],
    county: [null, [Validators.required]],
    postcode: [null, [Validators.required]],
    country: [null, [Validators.required]],
    email: [null, [Validators.pattern('^[^@s]+@[^@s]+.[^@s]+$')]],
    phoneNumber: [],
    locationCoordinateX: [null, [Validators.required]],
    locationCoordinateY: [null, [Validators.required]],
    defaultLanguage: [],
    cardPayment: [],
    singleCardPayment: [],
    recurringCardPayment: [],
    directDebit: [],
    giftAid: [],
    generalFundraising: [],
    schoolFundraising: [],
    createdByUserId: [],
    createdDate: []
  });

  constructor(protected affiliateService: AffiliateService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ affiliate }) => {
      this.updateForm(affiliate);
    });
  }

  updateForm(affiliate: IAffiliate) {
    this.editForm.patchValue({
      id: affiliate.id,
      salesforceUID: affiliate.salesforceUID,
      affiliateName: affiliate.affiliateName,
      currency: affiliate.currency,
      streetAddress: affiliate.streetAddress,
      city: affiliate.city,
      county: affiliate.county,
      postcode: affiliate.postcode,
      country: affiliate.country,
      email: affiliate.email,
      phoneNumber: affiliate.phoneNumber,
      locationCoordinateX: affiliate.locationCoordinateX,
      locationCoordinateY: affiliate.locationCoordinateY,
      defaultLanguage: affiliate.defaultLanguage,
      cardPayment: affiliate.cardPayment,
      singleCardPayment: affiliate.singleCardPayment,
      recurringCardPayment: affiliate.recurringCardPayment,
      directDebit: affiliate.directDebit,
      giftAid: affiliate.giftAid,
      generalFundraising: affiliate.generalFundraising,
      schoolFundraising: affiliate.schoolFundraising,
      createdByUserId: affiliate.createdByUserId,
      createdDate: affiliate.createdDate != null ? affiliate.createdDate.format(DATE_TIME_FORMAT) : null
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const affiliate = this.createFromForm();
    if (affiliate.id !== undefined) {
      this.subscribeToSaveResponse(this.affiliateService.update(affiliate));
    } else {
      this.subscribeToSaveResponse(this.affiliateService.create(affiliate));
    }
  }

  private createFromForm(): IAffiliate {
    return {
      ...new Affiliate(),
      id: this.editForm.get(['id']).value,
      salesforceUID: this.editForm.get(['salesforceUID']).value,
      affiliateName: this.editForm.get(['affiliateName']).value,
      currency: this.editForm.get(['currency']).value,
      streetAddress: this.editForm.get(['streetAddress']).value,
      city: this.editForm.get(['city']).value,
      county: this.editForm.get(['county']).value,
      postcode: this.editForm.get(['postcode']).value,
      country: this.editForm.get(['country']).value,
      email: this.editForm.get(['email']).value,
      phoneNumber: this.editForm.get(['phoneNumber']).value,
      locationCoordinateX: this.editForm.get(['locationCoordinateX']).value,
      locationCoordinateY: this.editForm.get(['locationCoordinateY']).value,
      defaultLanguage: this.editForm.get(['defaultLanguage']).value,
      cardPayment: this.editForm.get(['cardPayment']).value,
      singleCardPayment: this.editForm.get(['singleCardPayment']).value,
      recurringCardPayment: this.editForm.get(['recurringCardPayment']).value,
      directDebit: this.editForm.get(['directDebit']).value,
      giftAid: this.editForm.get(['giftAid']).value,
      generalFundraising: this.editForm.get(['generalFundraising']).value,
      schoolFundraising: this.editForm.get(['schoolFundraising']).value,
      createdByUserId: this.editForm.get(['createdByUserId']).value,
      createdDate:
        this.editForm.get(['createdDate']).value != null ? moment(this.editForm.get(['createdDate']).value, DATE_TIME_FORMAT) : undefined
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAffiliate>>) {
    result.subscribe(() => this.onSaveSuccess(), () => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
}
