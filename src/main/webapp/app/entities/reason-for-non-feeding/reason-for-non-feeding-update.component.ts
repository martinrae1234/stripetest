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
import { IReasonForNonFeeding, ReasonForNonFeeding } from 'app/shared/model/reason-for-non-feeding.model';
import { ReasonForNonFeedingService } from './reason-for-non-feeding.service';
import { ISchool } from 'app/shared/model/school.model';
import { SchoolService } from 'app/entities/school/school.service';

@Component({
  selector: 'jhi-reason-for-non-feeding-update',
  templateUrl: './reason-for-non-feeding-update.component.html'
})
export class ReasonForNonFeedingUpdateComponent implements OnInit {
  isSaving: boolean;

  schools: ISchool[];

  editForm = this.fb.group({
    id: [],
    dateOfNonFeeding: [],
    reasonForNonFeeding: [],
    createdByUserId: [],
    createdDate: [],
    schoolNotFed: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected reasonForNonFeedingService: ReasonForNonFeedingService,
    protected schoolService: SchoolService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ reasonForNonFeeding }) => {
      this.updateForm(reasonForNonFeeding);
    });
    this.schoolService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<ISchool[]>) => mayBeOk.ok),
        map((response: HttpResponse<ISchool[]>) => response.body)
      )
      .subscribe((res: ISchool[]) => (this.schools = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(reasonForNonFeeding: IReasonForNonFeeding) {
    this.editForm.patchValue({
      id: reasonForNonFeeding.id,
      dateOfNonFeeding: reasonForNonFeeding.dateOfNonFeeding != null ? reasonForNonFeeding.dateOfNonFeeding.format(DATE_TIME_FORMAT) : null,
      reasonForNonFeeding: reasonForNonFeeding.reasonForNonFeeding,
      createdByUserId: reasonForNonFeeding.createdByUserId,
      createdDate: reasonForNonFeeding.createdDate != null ? reasonForNonFeeding.createdDate.format(DATE_TIME_FORMAT) : null,
      schoolNotFed: reasonForNonFeeding.schoolNotFed
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const reasonForNonFeeding = this.createFromForm();
    if (reasonForNonFeeding.id !== undefined) {
      this.subscribeToSaveResponse(this.reasonForNonFeedingService.update(reasonForNonFeeding));
    } else {
      this.subscribeToSaveResponse(this.reasonForNonFeedingService.create(reasonForNonFeeding));
    }
  }

  private createFromForm(): IReasonForNonFeeding {
    return {
      ...new ReasonForNonFeeding(),
      id: this.editForm.get(['id']).value,
      dateOfNonFeeding:
        this.editForm.get(['dateOfNonFeeding']).value != null
          ? moment(this.editForm.get(['dateOfNonFeeding']).value, DATE_TIME_FORMAT)
          : undefined,
      reasonForNonFeeding: this.editForm.get(['reasonForNonFeeding']).value,
      createdByUserId: this.editForm.get(['createdByUserId']).value,
      createdDate:
        this.editForm.get(['createdDate']).value != null ? moment(this.editForm.get(['createdDate']).value, DATE_TIME_FORMAT) : undefined,
      schoolNotFed: this.editForm.get(['schoolNotFed']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IReasonForNonFeeding>>) {
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
