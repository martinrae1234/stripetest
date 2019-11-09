import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { ReasonForNonFeeding } from 'app/shared/model/reason-for-non-feeding.model';
import { ReasonForNonFeedingService } from './reason-for-non-feeding.service';
import { ReasonForNonFeedingComponent } from './reason-for-non-feeding.component';
import { ReasonForNonFeedingDetailComponent } from './reason-for-non-feeding-detail.component';
import { ReasonForNonFeedingUpdateComponent } from './reason-for-non-feeding-update.component';
import { ReasonForNonFeedingDeletePopupComponent } from './reason-for-non-feeding-delete-dialog.component';
import { IReasonForNonFeeding } from 'app/shared/model/reason-for-non-feeding.model';

@Injectable({ providedIn: 'root' })
export class ReasonForNonFeedingResolve implements Resolve<IReasonForNonFeeding> {
  constructor(private service: ReasonForNonFeedingService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IReasonForNonFeeding> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<ReasonForNonFeeding>) => response.ok),
        map((reasonForNonFeeding: HttpResponse<ReasonForNonFeeding>) => reasonForNonFeeding.body)
      );
    }
    return of(new ReasonForNonFeeding());
  }
}

export const reasonForNonFeedingRoute: Routes = [
  {
    path: '',
    component: ReasonForNonFeedingComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'webApp.reasonForNonFeeding.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: ReasonForNonFeedingDetailComponent,
    resolve: {
      reasonForNonFeeding: ReasonForNonFeedingResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'webApp.reasonForNonFeeding.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: ReasonForNonFeedingUpdateComponent,
    resolve: {
      reasonForNonFeeding: ReasonForNonFeedingResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'webApp.reasonForNonFeeding.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: ReasonForNonFeedingUpdateComponent,
    resolve: {
      reasonForNonFeeding: ReasonForNonFeedingResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'webApp.reasonForNonFeeding.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const reasonForNonFeedingPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: ReasonForNonFeedingDeletePopupComponent,
    resolve: {
      reasonForNonFeeding: ReasonForNonFeedingResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'webApp.reasonForNonFeeding.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
