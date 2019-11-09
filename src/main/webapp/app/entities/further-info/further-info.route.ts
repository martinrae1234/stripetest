import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { FurtherInfo } from 'app/shared/model/further-info.model';
import { FurtherInfoService } from './further-info.service';
import { FurtherInfoComponent } from './further-info.component';
import { FurtherInfoDetailComponent } from './further-info-detail.component';
import { FurtherInfoUpdateComponent } from './further-info-update.component';
import { FurtherInfoHomeComponent } from './further-info-home.component';
import { FurtherInfoDeletePopupComponent } from './further-info-delete-dialog.component';
import { IFurtherInfo } from 'app/shared/model/further-info.model';

@Injectable({ providedIn: 'root' })
export class FurtherInfoResolve implements Resolve<IFurtherInfo> {
  constructor(private service: FurtherInfoService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IFurtherInfo> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<FurtherInfo>) => response.ok),
        map((furtherInfo: HttpResponse<FurtherInfo>) => furtherInfo.body)
      );
    }
    return of(new FurtherInfo());
  }
}

export const furtherInfoRoute: Routes = [
  {
    path: 'further-info',
    component: FurtherInfoComponent,
    data: {
      authorities: [],
      pageTitle: 'FurtherInfos'
    },
  },
   {
    path: 'further-info-view',
    component: FurtherInfoHomeComponent,
    data: {
      authorities: [],
    },
  },
  {
    path: ':id/view',
    component: FurtherInfoDetailComponent,
    resolve: {
      furtherInfo: FurtherInfoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'FurtherInfos'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: FurtherInfoUpdateComponent,
    resolve: {
      furtherInfo: FurtherInfoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'FurtherInfos'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: FurtherInfoUpdateComponent,
    resolve: {
      furtherInfo: FurtherInfoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'FurtherInfos'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const furtherInfoPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: FurtherInfoDeletePopupComponent,
    resolve: {
      furtherInfo: FurtherInfoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'FurtherInfos'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
