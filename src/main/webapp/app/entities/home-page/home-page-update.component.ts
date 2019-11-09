import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { IHomePage, HomePage } from 'app/shared/model/home-page.model';
import { HomePageService } from './home-page.service';

@Component({
  selector: 'jhi-home-page-update',
  templateUrl: './home-page-update.component.html'
})
export class HomePageUpdateComponent implements OnInit {
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    costOfFeedingAChild: [],
    typeOfProject: []
  });

  constructor(protected homePageService: HomePageService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ homePage }) => {
      this.updateForm(homePage);
    });
  }

  updateForm(homePage: IHomePage) {
    this.editForm.patchValue({
      id: homePage.id,
      costOfFeedingAChild: homePage.costOfFeedingAChild,
      typeOfProject: homePage.typeOfProject
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const homePage = this.createFromForm();
    if (homePage.id !== undefined) {
      this.subscribeToSaveResponse(this.homePageService.update(homePage));
    } else {
      this.subscribeToSaveResponse(this.homePageService.create(homePage));
    }
  }

  private createFromForm(): IHomePage {
    return {
      ...new HomePage(),
      id: this.editForm.get(['id']).value,
      costOfFeedingAChild: this.editForm.get(['costOfFeedingAChild']).value,
      typeOfProject: this.editForm.get(['typeOfProject']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IHomePage>>) {
    result.subscribe(() => this.onSaveSuccess(), () => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
}
