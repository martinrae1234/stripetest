import { Component, OnInit, ElementRef } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService, JhiDataUtils } from 'ng-jhipster';
import { ISchool, School } from 'app/shared/model/school.model';
import { SchoolService } from './school.service';

@Component({
  selector: 'jhi-school-update',
  templateUrl: './school-update.component.html'
})
export class SchoolUpdateComponent implements OnInit {
  isSaving: boolean;
  dateOfLastStockCheckDp: any;

  editForm = this.fb.group({
    id: [],
    salesforceUID: [],
    legacySchoolID: [],
    schoolName: [null, [Validators.required]],
    sponsored: [],
    attendanceTotal: [],
    attendanceBoys: [null, [Validators.required]],
    attendanceGirls: [null, [Validators.required]],
    enrolmentTotal: [null, [Validators.required]],
    locationCoordinateX: [null, [Validators.required]],
    locationCoordinateY: [null, [Validators.required]],
    imageBanner: [],
    imageBannerContentType: [],
    textBanner: [],
    imageOne: [],
    imageOneContentType: [],
    imageTwo: [],
    imageTwoContentType: [],
    imageThree: [],
    imageThreeContentType: [],
    imageFour: [],
    imageFourContentType: [],
    dateOfLastStockCheck: [],
    createdByUserId: [],
    createdDate: []
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected jhiAlertService: JhiAlertService,
    protected schoolService: SchoolService,
    protected elementRef: ElementRef,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ school }) => {
      this.updateForm(school);
    });
  }

  updateForm(school: ISchool) {
    this.editForm.patchValue({
      id: school.id,
      salesforceUID: school.salesforceUID,
      legacySchoolID: school.legacySchoolID,
      schoolName: school.schoolName,
      sponsored: school.sponsored,
      attendanceTotal: school.attendanceTotal,
      attendanceBoys: school.attendanceBoys,
      attendanceGirls: school.attendanceGirls,
      enrolmentTotal: school.enrolmentTotal,
      locationCoordinateX: school.locationCoordinateX,
      locationCoordinateY: school.locationCoordinateY,
      imageBanner: school.imageBanner,
      imageBannerContentType: school.imageBannerContentType,
      textBanner: school.textBanner,
      imageOne: school.imageOne,
      imageOneContentType: school.imageOneContentType,
      imageTwo: school.imageTwo,
      imageTwoContentType: school.imageTwoContentType,
      imageThree: school.imageThree,
      imageThreeContentType: school.imageThreeContentType,
      imageFour: school.imageFour,
      imageFourContentType: school.imageFourContentType,
      dateOfLastStockCheck: school.dateOfLastStockCheck,
      createdByUserId: school.createdByUserId,
      createdDate: school.createdDate != null ? school.createdDate.format(DATE_TIME_FORMAT) : null
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
    const school = this.createFromForm();
    if (school.id !== undefined) {
      this.subscribeToSaveResponse(this.schoolService.update(school));
    } else {
      this.subscribeToSaveResponse(this.schoolService.create(school));
    }
  }

  private createFromForm(): ISchool {
    return {
      ...new School(),
      id: this.editForm.get(['id']).value,
      salesforceUID: this.editForm.get(['salesforceUID']).value,
      legacySchoolID: this.editForm.get(['legacySchoolID']).value,
      schoolName: this.editForm.get(['schoolName']).value,
      sponsored: this.editForm.get(['sponsored']).value,
      attendanceTotal: this.editForm.get(['attendanceTotal']).value,
      attendanceBoys: this.editForm.get(['attendanceBoys']).value,
      attendanceGirls: this.editForm.get(['attendanceGirls']).value,
      enrolmentTotal: this.editForm.get(['enrolmentTotal']).value,
      locationCoordinateX: this.editForm.get(['locationCoordinateX']).value,
      locationCoordinateY: this.editForm.get(['locationCoordinateY']).value,
      imageBannerContentType: this.editForm.get(['imageBannerContentType']).value,
      imageBanner: this.editForm.get(['imageBanner']).value,
      textBanner: this.editForm.get(['textBanner']).value,
      imageOneContentType: this.editForm.get(['imageOneContentType']).value,
      imageOne: this.editForm.get(['imageOne']).value,
      imageTwoContentType: this.editForm.get(['imageTwoContentType']).value,
      imageTwo: this.editForm.get(['imageTwo']).value,
      imageThreeContentType: this.editForm.get(['imageThreeContentType']).value,
      imageThree: this.editForm.get(['imageThree']).value,
      imageFourContentType: this.editForm.get(['imageFourContentType']).value,
      imageFour: this.editForm.get(['imageFour']).value,
      dateOfLastStockCheck: this.editForm.get(['dateOfLastStockCheck']).value,
      createdByUserId: this.editForm.get(['createdByUserId']).value,
      createdDate:
        this.editForm.get(['createdDate']).value != null ? moment(this.editForm.get(['createdDate']).value, DATE_TIME_FORMAT) : undefined
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISchool>>) {
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
}
