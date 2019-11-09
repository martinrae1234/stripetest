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
import { INews, News } from 'app/shared/model/news.model';
import { NewsService } from './news.service';

@Component({
  selector: 'jhi-news-update',
  templateUrl: './news-update.component.html'
})
export class NewsUpdateComponent implements OnInit {
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    activeMessage: [null, [Validators.required]],
    bannerPicture: [null, [Validators.required]],
    bannerPictureContentType: [],
    dateOfNewsCreation: [],
    bannerName: [null, [Validators.required]],
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
    protected newsService: NewsService,
    protected elementRef: ElementRef,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ news }) => {
      this.updateForm(news);
    });
  }

  updateForm(news: INews) {
    this.editForm.patchValue({
      id: news.id,
      activeMessage: news.activeMessage,
      bannerPicture: news.bannerPicture,
      bannerPictureContentType: news.bannerPictureContentType,
      dateOfNewsCreation: news.dateOfNewsCreation != null ? news.dateOfNewsCreation.format(DATE_TIME_FORMAT) : null,
      bannerName: news.bannerName,
      descriptionOne: news.descriptionOne,
      pictureOne: news.pictureOne,
      pictureOneContentType: news.pictureOneContentType,
      descriptionTwo: news.descriptionTwo,
      pictureTwo: news.pictureTwo,
      pictureTwoContentType: news.pictureTwoContentType,
      descriptionThree: news.descriptionThree,
      pictureThree: news.pictureThree,
      pictureThreeContentType: news.pictureThreeContentType,
      bottomPicture: news.bottomPicture,
      bottomPictureContentType: news.bottomPictureContentType,
      bottomDescription: news.bottomDescription,
      buttonText: news.buttonText,
      buttonURL: news.buttonURL,
      createdByUserId: news.createdByUserId,
      createdDate: news.createdDate != null ? news.createdDate.format(DATE_TIME_FORMAT) : null
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
    const news = this.createFromForm();
    if (news.id !== undefined) {
      this.subscribeToSaveResponse(this.newsService.update(news));
    } else {
      this.subscribeToSaveResponse(this.newsService.create(news));
    }
  }

  private createFromForm(): INews {
    return {
      ...new News(),
      id: this.editForm.get(['id']).value,
      activeMessage: this.editForm.get(['activeMessage']).value,
      bannerPictureContentType: this.editForm.get(['bannerPictureContentType']).value,
      bannerPicture: this.editForm.get(['bannerPicture']).value,
      dateOfNewsCreation:
        this.editForm.get(['dateOfNewsCreation']).value != null
          ? moment(this.editForm.get(['dateOfNewsCreation']).value, DATE_TIME_FORMAT)
          : undefined,
      bannerName: this.editForm.get(['bannerName']).value,
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

  protected subscribeToSaveResponse(result: Observable<HttpResponse<INews>>) {
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
