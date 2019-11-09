import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { School } from 'app/shared/model/school.model';
import { SchoolService } from './school.service';
import { SchoolComponent } from './school.component';
import { SchoolDetailComponent } from './school-detail.component';
import { SchoolUpdateComponent } from './school-update.component';
import { SchoolDeletePopupComponent } from './school-delete-dialog.component';
import { ISchool } from 'app/shared/model/school.model';

@Injectable({ providedIn: 'root' })
export class SchoolResolve implements Resolve<ISchool> {
  constructor(private service: SchoolService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ISchool> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<School>) => response.ok),
        map((school: HttpResponse<School>) => school.body)
      );
    }
    return of(new School());
  }
}

export const schoolRoute: Routes = [
  {
    path: '',
    component: SchoolComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'webApp.school.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: SchoolDetailComponent,
    resolve: {
      school: SchoolResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'webApp.school.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: SchoolUpdateComponent,
    resolve: {
      school: SchoolResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'webApp.school.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: SchoolUpdateComponent,
    resolve: {
      school: SchoolResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'webApp.school.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const schoolPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: SchoolDeletePopupComponent,
    resolve: {
      school: SchoolResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'webApp.school.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
