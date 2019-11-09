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
import { IEvent, Event } from 'app/shared/model/event.model';
import { EventService } from './event.service';
import { IProject } from 'app/shared/model/project.model';
import { ProjectService } from 'app/entities/project/project.service';

@Component({
  selector: 'jhi-event-update',
  templateUrl: './event-update.component.html'
})
export class EventUpdateComponent implements OnInit {
  isSaving: boolean;

  projects: IProject[];

  editForm = this.fb.group({
    id: [],
    salesforceUID: [],
    eventName: [],
    eventDate: [],
    eventDescription: [],
    eventBanner: [],
    eventBannerContentType: [],
    eventPictureOne: [],
    eventPictureOneContentType: [],
    eventPrictureTwo: [],
    eventPrictureTwoContentType: [],
    locationCoordinateX: [null, [Validators.required]],
    locationCoordinateY: [null, [Validators.required]],
    attendees: [],
    maximumAttendees: [],
    createdByUserId: [],
    createdDate: [],
    project: []
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected jhiAlertService: JhiAlertService,
    protected eventService: EventService,
    protected projectService: ProjectService,
    protected elementRef: ElementRef,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ event }) => {
      this.updateForm(event);
    });
    this.projectService
      .query({ filter: 'event-is-null' })
      .pipe(
        filter((mayBeOk: HttpResponse<IProject[]>) => mayBeOk.ok),
        map((response: HttpResponse<IProject[]>) => response.body)
      )
      .subscribe(
        (res: IProject[]) => {
          if (!this.editForm.get('project').value || !this.editForm.get('project').value.id) {
            this.projects = res;
          } else {
            this.projectService
              .find(this.editForm.get('project').value.id)
              .pipe(
                filter((subResMayBeOk: HttpResponse<IProject>) => subResMayBeOk.ok),
                map((subResponse: HttpResponse<IProject>) => subResponse.body)
              )
              .subscribe(
                (subRes: IProject) => (this.projects = [subRes].concat(res)),
                (subRes: HttpErrorResponse) => this.onError(subRes.message)
              );
          }
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  updateForm(event: IEvent) {
    this.editForm.patchValue({
      id: event.id,
      salesforceUID: event.salesforceUID,
      eventName: event.eventName,
      eventDate: event.eventDate != null ? event.eventDate.format(DATE_TIME_FORMAT) : null,
      eventDescription: event.eventDescription,
      eventBanner: event.eventBanner,
      eventBannerContentType: event.eventBannerContentType,
      eventPictureOne: event.eventPictureOne,
      eventPictureOneContentType: event.eventPictureOneContentType,
      eventPrictureTwo: event.eventPrictureTwo,
      eventPrictureTwoContentType: event.eventPrictureTwoContentType,
      locationCoordinateX: event.locationCoordinateX,
      locationCoordinateY: event.locationCoordinateY,
      attendees: event.attendees,
      maximumAttendees: event.maximumAttendees,
      createdByUserId: event.createdByUserId,
      createdDate: event.createdDate != null ? event.createdDate.format(DATE_TIME_FORMAT) : null,
      project: event.project
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
    const event = this.createFromForm();
    if (event.id !== undefined) {
      this.subscribeToSaveResponse(this.eventService.update(event));
    } else {
      this.subscribeToSaveResponse(this.eventService.create(event));
    }
  }

  private createFromForm(): IEvent {
    return {
      ...new Event(),
      id: this.editForm.get(['id']).value,
      salesforceUID: this.editForm.get(['salesforceUID']).value,
      eventName: this.editForm.get(['eventName']).value,
      eventDate:
        this.editForm.get(['eventDate']).value != null ? moment(this.editForm.get(['eventDate']).value, DATE_TIME_FORMAT) : undefined,
      eventDescription: this.editForm.get(['eventDescription']).value,
      eventBannerContentType: this.editForm.get(['eventBannerContentType']).value,
      eventBanner: this.editForm.get(['eventBanner']).value,
      eventPictureOneContentType: this.editForm.get(['eventPictureOneContentType']).value,
      eventPictureOne: this.editForm.get(['eventPictureOne']).value,
      eventPrictureTwoContentType: this.editForm.get(['eventPrictureTwoContentType']).value,
      eventPrictureTwo: this.editForm.get(['eventPrictureTwo']).value,
      locationCoordinateX: this.editForm.get(['locationCoordinateX']).value,
      locationCoordinateY: this.editForm.get(['locationCoordinateY']).value,
      attendees: this.editForm.get(['attendees']).value,
      maximumAttendees: this.editForm.get(['maximumAttendees']).value,
      createdByUserId: this.editForm.get(['createdByUserId']).value,
      createdDate:
        this.editForm.get(['createdDate']).value != null ? moment(this.editForm.get(['createdDate']).value, DATE_TIME_FORMAT) : undefined,
      project: this.editForm.get(['project']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IEvent>>) {
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

  trackProjectById(index: number, item: IProject) {
    return item.id;
  }
}
