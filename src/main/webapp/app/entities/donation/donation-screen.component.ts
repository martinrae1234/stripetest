import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';
import { IDonation, Donation } from 'app/shared/model/donation.model';
import { DonationService } from './donation.service';
import { ISupporter } from 'app/shared/model/supporter.model';
import { SupporterService } from 'app/entities/supporter/supporter.service';
import { IProject } from 'app/shared/model/project.model';
import { ProjectService } from 'app/entities/project/project.service';

@Component({
  selector: 'jhi-donation-screen',
  templateUrl: './donation-screen.component.html',
  styleUrls: ['donation-screen.scss']
})
export class DonationScreenComponent implements OnInit {
  isSaving: boolean;

  supporters: ISupporter[];

  projects: IProject[];

  amountValue = 13.9;

  editForm = this.fb.group({
    id: [],
    salesforceUID: [],
    currency: [],
    amount: [null, [Validators.required]],
    paymentMethod: [],
    frequency: [null, [Validators.required]],
    ageCategory: [],
    giftAidable: [null, [Validators.required]],
    giftAidMessage: [],
    accountHolderName: [],
    accountNumber: [],
    sortcode: [],
    collectionDate: [],
    isAccountHolder: [],
    cardType: [],
    cardNumber: [],
    expiryMonth: [],
    expiryYear: [],
    cardSecurityCode: [],
    createdByUserId: [],
    createdDate: [],
    donationToSupporter: [],
    donatesToProject: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected donationService: DonationService,
    protected supporterService: SupporterService,
    protected projectService: ProjectService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder,
    private router: Router
  ) {}

  get amount() {
    return this.amountValue;
  }

  set amount(value) {
    this.amountValue = value;
  }

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ donation }) => {
      this.updateForm(donation);
    });
    this.supporterService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<ISupporter[]>) => mayBeOk.ok),
        map((response: HttpResponse<ISupporter[]>) => response.body)
      )
      .subscribe((res: ISupporter[]) => (this.supporters = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.projectService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IProject[]>) => mayBeOk.ok),
        map((response: HttpResponse<IProject[]>) => response.body)
      )
      .subscribe((res: IProject[]) => (this.projects = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(donation: IDonation) {
    this.editForm.patchValue({
      id: donation.id,
      salesforceUID: donation.salesforceUID,
      currency: donation.currency,
      amount: donation.amount,
      paymentMethod: donation.paymentMethod,
      frequency: donation.frequency,
      ageCategory: donation.ageCategory,
      giftAidable: donation.giftAidable,
      giftAidMessage: donation.giftAidMessage,
      accountHolderName: donation.accountHolderName,
      accountNumber: donation.accountNumber,
      sortcode: donation.sortcode,
      collectionDate: donation.collectionDate,
      isAccountHolder: donation.isAccountHolder,
      cardType: donation.cardType,
      cardNumber: donation.cardNumber,
      expiryMonth: donation.expiryMonth,
      expiryYear: donation.expiryYear,
      cardSecurityCode: donation.cardSecurityCode,
      createdByUserId: donation.createdByUserId,
      createdDate: donation.createdDate != null ? donation.createdDate.format(DATE_TIME_FORMAT) : null,
      donationToSupporter: donation.donationToSupporter,
      donatesToProject: donation.donatesToProject
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const donation = this.createFromForm();
    if (donation.id !== undefined) {
      this.subscribeToSaveResponse(this.donationService.update(donation));
    } else {
      this.subscribeToSaveResponse(this.donationService.create(donation));
    }
  }

  private createFromForm(): IDonation {
    return {
      ...new Donation(),
      id: this.editForm.get(['id']).value,
      salesforceUID: this.editForm.get(['salesforceUID']).value,
      currency: this.editForm.get(['currency']).value,
      amount: this.editForm.get(['amount']).value,
      paymentMethod: this.editForm.get(['paymentMethod']).value,
      frequency: this.editForm.get(['frequency']).value,
      ageCategory: this.editForm.get(['ageCategory']).value,
      giftAidable: this.editForm.get(['giftAidable']).value,
      giftAidMessage: this.editForm.get(['giftAidMessage']).value,
      accountHolderName: this.editForm.get(['accountHolderName']).value,
      accountNumber: this.editForm.get(['accountNumber']).value,
      sortcode: this.editForm.get(['sortcode']).value,
      collectionDate: this.editForm.get(['collectionDate']).value,
      isAccountHolder: this.editForm.get(['isAccountHolder']).value,
      cardType: this.editForm.get(['cardType']).value,
      cardNumber: this.editForm.get(['cardNumber']).value,
      expiryMonth: this.editForm.get(['expiryMonth']).value,
      expiryYear: this.editForm.get(['expiryYear']).value,
      cardSecurityCode: this.editForm.get(['cardSecurityCode']).value,
      createdByUserId: this.editForm.get(['createdByUserId']).value,
      createdDate:
        this.editForm.get(['createdDate']).value != null ? moment(this.editForm.get(['createdDate']).value, DATE_TIME_FORMAT) : undefined,
      donationToSupporter: this.editForm.get(['donationToSupporter']).value,
      donatesToProject: this.editForm.get(['donatesToProject']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDonation>>) {
    result.subscribe(() => this.onSaveSuccess(), () => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    // this.previousState();
    this.router.navigate(['/supporter/addsupporter']);
    // this.router.navigate(['/payments']);
  }

  protected onSaveError() {
    this.isSaving = false;
  }
  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }

  trackSupporterById(index: number, item: ISupporter) {
    return item.id;
  }

  trackProjectById(index: number, item: IProject) {
    return item.id;
  }
}
