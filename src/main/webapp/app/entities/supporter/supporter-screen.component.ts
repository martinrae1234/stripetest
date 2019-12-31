import { Component, OnInit, ElementRef } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService, JhiDataUtils } from 'ng-jhipster';
import { ISupporter, Supporter } from 'app/shared/model/supporter.model';
import { SupporterService } from './supporter.service';
import { IUser } from 'app/core/user/user.model';
import { UserService } from 'app/core/user/user.service';
import { IAffiliate } from 'app/shared/model/affiliate.model';
import { AffiliateService } from 'app/entities/affiliate/affiliate.service';

@Component({
  selector: 'jhi-supporter-screen',
  templateUrl: './supporter-screen.component.html',
  styleUrls: ['supporter-screen.scss']
})
export class SupporterScreenComponent implements OnInit {
  isSaving: boolean;

  users: IUser[];

  affiliates: IAffiliate[];

  editForm = this.fb.group({
    id: [],
    salesforceUID: [],
    ageCategory: [],
    supporterSalutation: [],
    firstName: [null, [Validators.required]],
    secondName: [null, [Validators.required]],
    email: [null, [Validators.pattern('^[^@s]+@[^@s]+.[^@s]+$')]],
    streetAddress: [null, [Validators.required]],
    city: [null, [Validators.required]],
    county: [null, [Validators.required]],
    postcode: [null, [Validators.required]],
    country: [null, [Validators.required]],
    supporterPicture: [],
    supporterPictureContentType: [],
    contactableByEmail: [null, [Validators.required]],
    contactableByPost: [null, [Validators.required]],
    locationCoordinateX: [],
    locationCoordinateY: [],
    facebookUrl: [],
    instagramUrl: [],
    twitterUrl: [],
    createdByUserId: [],
    createdDate: [],
    user: [],
    supporterOfAffiliate: []
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected jhiAlertService: JhiAlertService,
    protected supporterService: SupporterService,
    protected userService: UserService,
    protected affiliateService: AffiliateService,
    protected elementRef: ElementRef,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder,
    private router: Router
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ supporter }) => {
      this.updateForm(supporter);
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

  updateForm(supporter: ISupporter) {
    this.editForm.patchValue({
      id: supporter.id,
      salesforceUID: supporter.salesforceUID,
      ageCategory: supporter.ageCategory,
      supporterSalutation: supporter.supporterSalutation,
      firstName: supporter.firstName,
      secondName: supporter.secondName,
      email: supporter.email,
      streetAddress: supporter.streetAddress,
      city: supporter.city,
      county: supporter.county,
      postcode: supporter.postcode,
      country: supporter.country,
      supporterPicture: supporter.supporterPicture,
      supporterPictureContentType: supporter.supporterPictureContentType,
      contactableByEmail: supporter.contactableByEmail,
      contactableByPost: supporter.contactableByPost,
      locationCoordinateX: supporter.locationCoordinateX,
      locationCoordinateY: supporter.locationCoordinateY,
      facebookUrl: supporter.facebookUrl,
      instagramUrl: supporter.instagramUrl,
      twitterUrl: supporter.twitterUrl,
      createdByUserId: supporter.createdByUserId,
      createdDate: supporter.createdDate != null ? supporter.createdDate.format(DATE_TIME_FORMAT) : null,
      user: supporter.user,
      supporterOfAffiliate: supporter.supporterOfAffiliate
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
      // () => console.log('blob added'), // success
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
    const supporter = this.createFromForm();
    if (supporter.id !== undefined) {
      this.subscribeToSaveResponse(this.supporterService.update(supporter));
    } else {
      this.subscribeToSaveResponse(this.supporterService.create(supporter));
    }
  }

  private createFromForm(): ISupporter {
    return {
      ...new Supporter(),
      id: this.editForm.get(['id']).value,
      salesforceUID: this.editForm.get(['salesforceUID']).value,
      ageCategory: this.editForm.get(['ageCategory']).value,
      supporterSalutation: this.editForm.get(['supporterSalutation']).value,
      firstName: this.editForm.get(['firstName']).value,
      secondName: this.editForm.get(['secondName']).value,
      email: this.editForm.get(['email']).value,
      streetAddress: this.editForm.get(['streetAddress']).value,
      city: this.editForm.get(['city']).value,
      county: this.editForm.get(['county']).value,
      postcode: this.editForm.get(['postcode']).value,
      country: this.editForm.get(['country']).value,
      supporterPictureContentType: this.editForm.get(['supporterPictureContentType']).value,
      supporterPicture: this.editForm.get(['supporterPicture']).value,
      contactableByEmail: this.editForm.get(['contactableByEmail']).value,
      contactableByPost: this.editForm.get(['contactableByPost']).value,
      locationCoordinateX: this.editForm.get(['locationCoordinateX']).value,
      locationCoordinateY: this.editForm.get(['locationCoordinateY']).value,
      facebookUrl: this.editForm.get(['facebookUrl']).value,
      instagramUrl: this.editForm.get(['instagramUrl']).value,
      twitterUrl: this.editForm.get(['twitterUrl']).value,
      createdByUserId: this.editForm.get(['createdByUserId']).value,
      createdDate:
        this.editForm.get(['createdDate']).value != null ? moment(this.editForm.get(['createdDate']).value, DATE_TIME_FORMAT) : undefined,
      user: this.editForm.get(['user']).value,
      supporterOfAffiliate: this.editForm.get(['supporterOfAffiliate']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISupporter>>) {
    result.subscribe(() => this.onSaveSuccess(), () => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.router.navigate(['/payments']);
    // this.previousState();
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
