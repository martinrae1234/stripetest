import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';
import { ICommodity, Commodity } from 'app/shared/model/commodity.model';
import { CommodityService } from './commodity.service';
import { ISchool } from 'app/shared/model/school.model';
import { SchoolService } from 'app/entities/school/school.service';

@Component({
  selector: 'jhi-commodity-update',
  templateUrl: './commodity-update.component.html'
})
export class CommodityUpdateComponent implements OnInit {
  isSaving: boolean;

  schools: ISchool[];
  dateOfLastStockCheckDp: any;

  editForm = this.fb.group({
    id: [],
    dateOfLastStockCheck: [],
    name: [],
    amount: [],
    perishable: [],
    amountExpirableInNext3months: [],
    createdByUserId: [],
    createdDate: [],
    commodityForSchool: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected commodityService: CommodityService,
    protected schoolService: SchoolService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ commodity }) => {
      this.updateForm(commodity);
    });
    this.schoolService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<ISchool[]>) => mayBeOk.ok),
        map((response: HttpResponse<ISchool[]>) => response.body)
      )
      .subscribe((res: ISchool[]) => (this.schools = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(commodity: ICommodity) {
    this.editForm.patchValue({
      id: commodity.id,
      dateOfLastStockCheck: commodity.dateOfLastStockCheck,
      name: commodity.name,
      amount: commodity.amount,
      perishable: commodity.perishable,
      amountExpirableInNext3months: commodity.amountExpirableInNext3months,
      createdByUserId: commodity.createdByUserId,
      createdDate: commodity.createdDate != null ? commodity.createdDate.format(DATE_TIME_FORMAT) : null,
      commodityForSchool: commodity.commodityForSchool
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const commodity = this.createFromForm();
    if (commodity.id !== undefined) {
      this.subscribeToSaveResponse(this.commodityService.update(commodity));
    } else {
      this.subscribeToSaveResponse(this.commodityService.create(commodity));
    }
  }

  private createFromForm(): ICommodity {
    return {
      ...new Commodity(),
      id: this.editForm.get(['id']).value,
      dateOfLastStockCheck: this.editForm.get(['dateOfLastStockCheck']).value,
      name: this.editForm.get(['name']).value,
      amount: this.editForm.get(['amount']).value,
      perishable: this.editForm.get(['perishable']).value,
      amountExpirableInNext3months: this.editForm.get(['amountExpirableInNext3months']).value,
      createdByUserId: this.editForm.get(['createdByUserId']).value,
      createdDate:
        this.editForm.get(['createdDate']).value != null ? moment(this.editForm.get(['createdDate']).value, DATE_TIME_FORMAT) : undefined,
      commodityForSchool: this.editForm.get(['commodityForSchool']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICommodity>>) {
    result.subscribe(() => this.onSaveSuccess(), () => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }

  trackSchoolById(index: number, item: ISchool) {
    return item.id;
  }
}
