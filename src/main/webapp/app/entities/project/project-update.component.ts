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
import { IProject, Project } from 'app/shared/model/project.model';
import { ProjectService } from './project.service';
import { ISupporter } from 'app/shared/model/supporter.model';
import { SupporterService } from 'app/entities/supporter/supporter.service';
import { ISchool } from 'app/shared/model/school.model';
import { SchoolService } from 'app/entities/school/school.service';

@Component({
  selector: 'jhi-project-update',
  templateUrl: './project-update.component.html'
})
export class ProjectUpdateComponent implements OnInit {
  isSaving: boolean;

  supporters: ISupporter[];

  schools: ISchool[];
  sponsorshipStartDp: any;
  sponsorshipEndDateDp: any;

  editForm = this.fb.group({
    id: [],
    salesforceUID: [],
    typeOfProject: [null, [Validators.required]],
    fundraisingTarget: [null, [Validators.required]],
    ageCategory: [null, [Validators.required]],
    projectName: [null, [Validators.required]],
    projectDescription: [],
    projectImage: [],
    projectImageContentType: [],
    sponsorshipStart: [],
    sponsorshipEndDate: [],
    createdByUserId: [],
    createdDate: [],
    projectOfsupporter: [],
    projectForSchool: []
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected jhiAlertService: JhiAlertService,
    protected projectService: ProjectService,
    protected supporterService: SupporterService,
    protected schoolService: SchoolService,
    protected elementRef: ElementRef,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ project }) => {
      this.updateForm(project);
    });
    this.supporterService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<ISupporter[]>) => mayBeOk.ok),
        map((response: HttpResponse<ISupporter[]>) => response.body)
      )
      .subscribe((res: ISupporter[]) => (this.supporters = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.schoolService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<ISchool[]>) => mayBeOk.ok),
        map((response: HttpResponse<ISchool[]>) => response.body)
      )
      .subscribe((res: ISchool[]) => (this.schools = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(project: IProject) {
    this.editForm.patchValue({
      id: project.id,
      salesforceUID: project.salesforceUID,
      typeOfProject: project.typeOfProject,
      fundraisingTarget: project.fundraisingTarget,
      ageCategory: project.ageCategory,
      projectName: project.projectName,
      projectDescription: project.projectDescription,
      projectImage: project.projectImage,
      projectImageContentType: project.projectImageContentType,
      sponsorshipStart: project.sponsorshipStart,
      sponsorshipEndDate: project.sponsorshipEndDate,
      createdByUserId: project.createdByUserId,
      createdDate: project.createdDate != null ? project.createdDate.format(DATE_TIME_FORMAT) : null,
      projectOfsupporter: project.projectOfsupporter,
      projectForSchool: project.projectForSchool
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
    const project = this.createFromForm();
    if (project.id !== undefined) {
      this.subscribeToSaveResponse(this.projectService.update(project));
    } else {
      this.subscribeToSaveResponse(this.projectService.create(project));
    }
  }

  private createFromForm(): IProject {
    return {
      ...new Project(),
      id: this.editForm.get(['id']).value,
      salesforceUID: this.editForm.get(['salesforceUID']).value,
      typeOfProject: this.editForm.get(['typeOfProject']).value,
      fundraisingTarget: this.editForm.get(['fundraisingTarget']).value,
      ageCategory: this.editForm.get(['ageCategory']).value,
      projectName: this.editForm.get(['projectName']).value,
      projectDescription: this.editForm.get(['projectDescription']).value,
      projectImageContentType: this.editForm.get(['projectImageContentType']).value,
      projectImage: this.editForm.get(['projectImage']).value,
      sponsorshipStart: this.editForm.get(['sponsorshipStart']).value,
      sponsorshipEndDate: this.editForm.get(['sponsorshipEndDate']).value,
      createdByUserId: this.editForm.get(['createdByUserId']).value,
      createdDate:
        this.editForm.get(['createdDate']).value != null ? moment(this.editForm.get(['createdDate']).value, DATE_TIME_FORMAT) : undefined,
      projectOfsupporter: this.editForm.get(['projectOfsupporter']).value,
      projectForSchool: this.editForm.get(['projectForSchool']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IProject>>) {
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

  trackSupporterById(index: number, item: ISupporter) {
    return item.id;
  }

  trackSchoolById(index: number, item: ISchool) {
    return item.id;
  }
}
