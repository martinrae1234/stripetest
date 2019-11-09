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
import { IMessage, Message } from 'app/shared/model/message.model';
import { MessageService } from './message.service';

@Component({
  selector: 'jhi-message-update',
  templateUrl: './message-update.component.html'
})
export class MessageUpdateComponent implements OnInit {
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    activeMessage: [null, [Validators.required]],
    bannerPicture: [null, [Validators.required]],
    bannerPictureContentType: [],
    bannerName: [null, [Validators.required]],
    headingOne: [null, [Validators.required, Validators.maxLength(40)]],
    descriptionOne: [null, [Validators.required]],
    quotePicture: [],
    quotePictureContentType: [],
    quoteContent: [],
    headerTwo: [],
    descriptionTwo: [],
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
    protected messageService: MessageService,
    protected elementRef: ElementRef,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ message }) => {
      this.updateForm(message);
    });
  }

  updateForm(message: IMessage) {
    this.editForm.patchValue({
      id: message.id,
      activeMessage: message.activeMessage,
      bannerPicture: message.bannerPicture,
      bannerPictureContentType: message.bannerPictureContentType,
      bannerName: message.bannerName,
      headingOne: message.headingOne,
      descriptionOne: message.descriptionOne,
      quotePicture: message.quotePicture,
      quotePictureContentType: message.quotePictureContentType,
      quoteContent: message.quoteContent,
      headerTwo: message.headerTwo,
      descriptionTwo: message.descriptionTwo,
      bottomPicture: message.bottomPicture,
      bottomPictureContentType: message.bottomPictureContentType,
      bottomDescription: message.bottomDescription,
      buttonText: message.buttonText,
      buttonURL: message.buttonURL,
      createdByUserId: message.createdByUserId,
      createdDate: message.createdDate != null ? message.createdDate.format(DATE_TIME_FORMAT) : null
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
    const message = this.createFromForm();
    if (message.id !== undefined) {
      this.subscribeToSaveResponse(this.messageService.update(message));
    } else {
      this.subscribeToSaveResponse(this.messageService.create(message));
    }
  }

  private createFromForm(): IMessage {
    return {
      ...new Message(),
      id: this.editForm.get(['id']).value,
      activeMessage: this.editForm.get(['activeMessage']).value,
      bannerPictureContentType: this.editForm.get(['bannerPictureContentType']).value,
      bannerPicture: this.editForm.get(['bannerPicture']).value,
      bannerName: this.editForm.get(['bannerName']).value,
      headingOne: this.editForm.get(['headingOne']).value,
      descriptionOne: this.editForm.get(['descriptionOne']).value,
      quotePictureContentType: this.editForm.get(['quotePictureContentType']).value,
      quotePicture: this.editForm.get(['quotePicture']).value,
      quoteContent: this.editForm.get(['quoteContent']).value,
      headerTwo: this.editForm.get(['headerTwo']).value,
      descriptionTwo: this.editForm.get(['descriptionTwo']).value,
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

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IMessage>>) {
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
