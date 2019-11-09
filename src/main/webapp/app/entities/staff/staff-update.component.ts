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
import { IStaff, Staff } from 'app/shared/model/staff.model';
import { StaffService } from './staff.service';
import { IUser } from 'app/core/user/user.model';
import { UserService } from 'app/core/user/user.service';
import { IAffiliate } from 'app/shared/model/affiliate.model';
import { AffiliateService } from 'app/entities/affiliate/affiliate.service';

@Component({
  selector: 'jhi-staff-update',
  templateUrl: './staff-update.component.html'
})
export class StaffUpdateComponent implements OnInit {
  isSaving: boolean;

  users: IUser[];

  affiliates: IAffiliate[];

  editForm = this.fb.group({
    id: [],
    salesforceUID: [],
    firstName: [null, [Validators.required]],
    secondName: [null, [Validators.required]],
    affiliate: [],
    typeOfStaff: [],
    locationCoordinateX: [null, [Validators.required]],
    locationCoordinateY: [null, [Validators.required]],
    staffPicture: [null, [Validators.required]],
    staffPictureContentType: [],
    position: [null, [Validators.required]],
    description: [null, [Validators.required]],
    createdByUserId: [],
    createdDate: [],
    user: [],
    staffOfAffiliate: []
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected jhiAlertService: JhiAlertService,
    protected staffService: StaffService,
    protected userService: UserService,
    protected affiliateService: AffiliateService,
    protected elementRef: ElementRef,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ staff }) => {
      this.updateForm(staff);
    });
    this.userService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IUser[]>) => mayBeOk.ok),
        map((response: HttpResponse<IUser[]>) => response.body)
      )
      .subscribe((res: IUser[]) => (this.users = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.affiliateService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IAffiliate[]>) => mayBeOk.ok),
        map((response: HttpResponse<IAffiliate[]>) => response.body)
      )
      .subscribe((res: IAffiliate[]) => (this.affiliates = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(staff: IStaff) {
    this.editForm.patchValue({
      id: staff.id,
      salesforceUID: staff.salesforceUID,
      firstName: staff.firstName,
      secondName: staff.secondName,
      affiliate: staff.affiliate,
      typeOfStaff: staff.typeOfStaff,
      locationCoordinateX: staff.locationCoordinateX,
      locationCoordinateY: staff.locationCoordinateY,
      staffPicture: staff.staffPicture,
      staffPictureContentType: staff.staffPictureContentType,
      position: staff.position,
      description: staff.description,
      createdByUserId: staff.createdByUserId,
      createdDate: staff.createdDate != null ? staff.createdDate.format(DATE_TIME_FORMAT) : null,
      user: staff.user,
      staffOfAffiliate: staff.staffOfAffiliate
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
    const staff = this.createFromForm();
    if (staff.id !== undefined) {
      this.subscribeToSaveResponse(this.staffService.update(staff));
    } else {
      this.subscribeToSaveResponse(this.staffService.create(staff));
    }
  }

  private createFromForm(): IStaff {
    return {
      ...new Staff(),
      id: this.editForm.get(['id']).value,
      salesforceUID: this.editForm.get(['salesforceUID']).value,
      firstName: this.editForm.get(['firstName']).value,
      secondName: this.editForm.get(['secondName']).value,
      affiliate: this.editForm.get(['affiliate']).value,
      typeOfStaff: this.editForm.get(['typeOfStaff']).value,
      locationCoordinateX: this.editForm.get(['locationCoordinateX']).value,
      locationCoordinateY: this.editForm.get(['locationCoordinateY']).value,
      staffPictureContentType: this.editForm.get(['staffPictureContentType']).value,
      staffPicture: this.editForm.get(['staffPicture']).value,
      position: this.editForm.get(['position']).value,
      description: this.editForm.get(['description']).value,
      createdByUserId: this.editForm.get(['createdByUserId']).value,
      createdDate:
        this.editForm.get(['createdDate']).value != null ? moment(this.editForm.get(['createdDate']).value, DATE_TIME_FORMAT) : undefined,
      user: this.editForm.get(['user']).value,
      staffOfAffiliate: this.editForm.get(['staffOfAffiliate']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IStaff>>) {
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

  trackUserById(index: number, item: IUser) {
    return item.id;
  }

  trackAffiliateById(index: number, item: IAffiliate) {
    return item.id;
  }
}
