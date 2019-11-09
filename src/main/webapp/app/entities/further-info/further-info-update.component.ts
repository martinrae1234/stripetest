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
import { IFurtherInfo, FurtherInfo } from 'app/shared/model/further-info.model';
import { FurtherInfoService } from './further-info.service';

@Component({
  selector: 'jhi-further-info-update',
  templateUrl: './further-info-update.component.html'
})
export class FurtherInfoUpdateComponent implements OnInit {
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    activeFurtherInfo: [null, [Validators.required]],
    bannerPicture: [null, [Validators.required]],
    bannerPictureContentType: [],
    bannerName: [null, [Validators.required, Validators.maxLength(40)]],
    headerOne: [null, [Validators.required]],
    descriptionOne: [null, [Validators.required]],
    pictureOne: [null, [Validators.required]],
    pictureOneContentType: [],
    descriptionTwo: [],
    pictureTwo: [],
    pictureTwoContentType: [],
    descriptionThree: [],
    pictureThree: [],
    pictureThreeContentType: [],
    bottomPicture: [null, [Validators.required]],
    bottomPictureContentType: [],
    bottomDescription: [null, [Validators.required]],
    buttonText: [],
    buttonURL: [],
    createdByUserId: [],
    createdDate: []
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected jhiAlertService: JhiAlertService,
    protected furtherInfoService: FurtherInfoService,
    protected elementRef: ElementRef,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ furtherInfo }) => {
      this.updateForm(furtherInfo);
    });
  }

  updateForm(furtherInfo: IFurtherInfo) {
    this.editForm.patchValue({
      id: furtherInfo.id,
      activeFurtherInfo: furtherInfo.activeFurtherInfo,
      bannerPicture: furtherInfo.bannerPicture,
      bannerPictureContentType: furtherInfo.bannerPictureContentType,
      bannerName: furtherInfo.bannerName,
      headerOne: furtherInfo.headerOne,
      descriptionOne: furtherInfo.descriptionOne,
      pictureOne: furtherInfo.pictureOne,
      pictureOneContentType: furtherInfo.pictureOneContentType,
      descriptionTwo: furtherInfo.descriptionTwo,
      pictureTwo: furtherInfo.pictureTwo,
      pictureTwoContentType: furtherInfo.pictureTwoContentType,
      descriptionThree: furtherInfo.descriptionThree,
      pictureThree: furtherInfo.pictureThree,
      pictureThreeContentType: furtherInfo.pictureThreeContentType,
      bottomPicture: furtherInfo.bottomPicture,
      bottomPictureContentType: furtherInfo.bottomPictureContentType,
      bottomDescription: furtherInfo.bottomDescription,
      buttonText: furtherInfo.buttonText,
      buttonURL: furtherInfo.buttonURL,
      createdByUserId: furtherInfo.createdByUserId,
      createdDate: furtherInfo.createdDate != null ? furtherInfo.createdDate.format(DATE_TIME_FORMAT) : null
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
    const furtherInfo = this.createFromForm();
    if (furtherInfo.id !== undefined) {
      this.subscribeToSaveResponse(this.furtherInfoService.update(furtherInfo));
    } else {
      this.subscribeToSaveResponse(this.furtherInfoService.create(furtherInfo));
    }
  }

  private createFromForm(): IFurtherInfo {
    return {
      ...new FurtherInfo(),
      id: this.editForm.get(['id']).value,
      activeFurtherInfo: this.editForm.get(['activeFurtherInfo']).value,
      bannerPictureContentType: this.editForm.get(['bannerPictureContentType']).value,
      bannerPicture: this.editForm.get(['bannerPicture']).value,
      bannerName: this.editForm.get(['bannerName']).value,
      headerOne: this.editForm.get(['headerOne']).value,
      descriptionOne: this.editForm.get(['descriptionOne']).value,
      pictureOneContentType: this.editForm.get(['pictureOneContentType']).value,
      pictureOne: this.editForm.get(['pictureOne']).value,
      descriptionTwo: this.editForm.get(['descriptionTwo']).value,
      pictureTwoContentType: this.editForm.get(['pictureTwoContentType']).value,
      pictureTwo: this.editForm.get(['pictureTwo']).value,
      descriptionThree: this.editForm.get(['descriptionThree']).value,
      pictureThreeContentType: this.editForm.get(['pictureThreeContentType']).value,
      pictureThree: this.editForm.get(['pictureThree']).value,
      bottomPictureContentType: this.editForm.get(['bottomPictureContentType']).value,
      bottomPicture: this.editForm.get(['bottomPicture']).value,
      bottomDescription: this.editForm.get(['bottomDescription']).value,
      buttonText: this.editForm.get(['buttonText']).value,
      buttonURL: this.editForm.get(['buttonURL']).value,
      createdByUserId: this.editForm.get(['createdByUserId']).value,
      createdDate:
        this.editForm.get(['createdDate']).value != null ? moment(this.editForm.get(['createdDate']).value, DATE_TIME_FORMAT) : undefined
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IFurtherInfo>>) {
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
