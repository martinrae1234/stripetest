import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Commodity } from 'app/shared/model/commodity.model';
import { CommodityService } from './commodity.service';
import { CommodityComponent } from './commodity.component';
import { CommodityDetailComponent } from './commodity-detail.component';
import { CommodityUpdateComponent } from './commodity-update.component';
import { CommodityDeletePopupComponent } from './commodity-delete-dialog.component';
import { ICommodity } from 'app/shared/model/commodity.model';

@Injectable({ providedIn: 'root' })
export class CommodityResolve implements Resolve<ICommodity> {
  constructor(private service: CommodityService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ICommodity> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<Commodity>) => response.ok),
        map((commodity: HttpResponse<Commodity>) => commodity.body)
      );
    }
    return of(new Commodity());
  }
}

export const commodityRoute: Routes = [
  {
    path: '',
    component: CommodityComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'webApp.commodity.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: CommodityDetailComponent,
    resolve: {
      commodity: CommodityResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'webApp.commodity.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: CommodityUpdateComponent,
    resolve: {
      commodity: CommodityResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'webApp.commodity.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: CommodityUpdateComponent,
    resolve: {
      commodity: CommodityResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'webApp.commodity.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const commodityPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: CommodityDeletePopupComponent,
    resolve: {
      commodity: CommodityResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'webApp.commodity.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
