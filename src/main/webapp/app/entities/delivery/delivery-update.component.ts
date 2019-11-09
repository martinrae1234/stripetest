import { Component, OnInit, ElementRef } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService, JhiDataUtils } from 'ng-jhipster';
import { IDelivery, Delivery } from 'app/shared/model/delivery.model';
import { DeliveryService } from './delivery.service';
import { ISchool } from 'app/shared/model/school.model';
import { SchoolService } from 'app/entities/school/school.service';

@Component({
  selector: 'jhi-delivery-update',
  templateUrl: './delivery-update.component.html'
})
export class DeliveryUpdateComponent implements OnInit {
  isSaving: boolean;

  schools: ISchool[];
  deliveryDateDp: any;

  editForm = this.fb.group({
    id: [],
    deliveryDate: [],
    amountDeliveredRice: [],
    amountDeliveredFlour: [],
    deliveryNoteImage: [],
    deliveryNoteImageContentType: [],
    createdByUserId: [],
    createdDate: [],
    deliveryForSchool: []
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected jhiAlertService: JhiAlertService,
    protected deliveryService: DeliveryService,
    protected schoolService: SchoolService,
    protected elementRef: ElementRef,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ delivery }) => {
      this.updateForm(delivery);
    });
    this.schoolService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<ISchool[]>) => mayBeOk.ok),
        map((response: HttpResponse<ISchool[]>) => response.body)
      )
      .subscribe((res: ISchool[]) => (this.schools = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(delivery: IDelivery) {
    this.editForm.patchValue({
      id: delivery.id,
      deliveryDate: delivery.deliveryDate,
      amountDeliveredRice: delivery.amountDeliveredRice,
      amountDeliveredFlour: delivery.amountDeliveredFlour,
      deliveryNoteImage: delivery.deliveryNoteImage,
      deliveryNoteImageContentType: delivery.deliveryNoteImageContentType,
      createdByUserId: delivery.createdByUserId,
      createdDate: delivery.createdDate != null ? delivery.createdDate.format(DATE_TIME_FORMAT) : null,
      deliveryForSchool: delivery.deliveryForSchool
    });
  }

  byteSize(field) {
    return this.dataUtils.byteSize(field);
  }

  openFile(contentType, field) {
    return this.dataUtils.openFile(contentType, field);
  }

  setFileData(event, field: string, isImage) {
    return new Promise((resolve, reject) => {
      if (event && event.target && event.target.files && event.target.files[0]) {
        const file: File = event.target.files[0];
        if (isImage && !file.type.startsWith('image/')) {
          reject(`File was expected to be an image but was found to be ${file.type}`);
        } else {
          const filedContentType: string = field + 'ContentType';
          this.dataUtils.toBase64(file, base64Data => {
            this.editForm.patchValue({
              [field]: base64Data,
              [filedContentType]: file.type
            });
          });
        }
      } else {
        reject(`Base64 data was not set as file could not be extracted from passed parameter: ${event}`);
      }
    }).then(
      // eslint-disable-next-line no-console
      () => console.log('blob added'), // success
      this.onError
    );
  }

  clearInputImage(field: string, fieldContentType: string, idInput: string) {
    this.editForm.patchValue({
      [field]: null,
      [fieldContentType]: null
    });
    if (this.elementRef && idInput && this.elementRef.nativeElement.querySelector('#' + idInput)) {
      this.elementRef.nativeElement.querySelector('#' + idInput).value = null;
    }
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const delivery = this.createFromForm();
    if (delivery.id !== undefined) {
      this.subscribeToSaveResponse(this.deliveryService.update(delivery));
    } else {
      this.subscribeToSaveResponse(this.deliveryService.create(delivery));
    }
  }

  private createFromForm(): IDelivery {
    return {
      ...new Delivery(),
      id: this.editForm.get(['id']).value,
      deliveryDate: this.editForm.get(['deliveryDate']).value,
      amountDeliveredRice: this.editForm.get(['amountDeliveredRice']).value,
      amountDeliveredFlour: this.editForm.get(['amountDeliveredFlour']).value,
      deliveryNoteImageContentType: this.editForm.get(['deliveryNoteImageContentType']).value,
      deliveryNoteImage: this.editForm.get(['deliveryNoteImage']).value,
      createdByUserId: this.editForm.get(['createdByUserId']).value,
      createdDate:
        this.editForm.get(['createdDate']).value != null ? moment(this.editForm.get(['createdDate']).value, DATE_TIME_FORMAT) : undefined,
      deliveryForSchool: this.editForm.get(['deliveryForSchool']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDelivery>>) {
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
