import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Supporter } from 'app/shared/model/supporter.model';
import { SupporterService } from './supporter.service';
import { SupporterComponent } from './supporter.component';
import { SupporterDetailComponent } from './supporter-detail.component';
import { SupporterUpdateComponent } from './supporter-update.component';
import { SupporterDeletePopupComponent } from './supporter-delete-dialog.component';
import { ISupporter } from 'app/shared/model/supporter.model';
import { SupporterScreenComponent } from './supporter-screen.component';

@Injectable({ providedIn: 'root' })
export class SupporterResolve implements Resolve<ISupporter> {
  constructor(private service: SupporterService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ISupporter> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<Supporter>) => response.ok),
        map((supporter: HttpResponse<Supporter>) => supporter.body)
      );
    }
    return of(new Supporter());
  }
}

export const supporterRoute: Routes = [
  {
    path: '',
    component: SupporterComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'webApp.supporter.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'addsupporter',
    component: SupporterScreenComponent,
    resolve: {
      supporter: SupporterResolve
    },
    data: {
      authorities: [],
      pageTitle: 'webApp.supporter.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: SupporterDetailComponent,
    resolve: {
      supporter: SupporterResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'webApp.supporter.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: SupporterUpdateComponent,
    resolve: {
      supporter: SupporterResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'webApp.supporter.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: SupporterUpdateComponent,
    resolve: {
      supporter: SupporterResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'webApp.supporter.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const supporterPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: SupporterDeletePopupComponent,
    resolve: {
      supporter: SupporterResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'webApp.supporter.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
