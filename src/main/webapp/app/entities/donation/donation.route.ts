import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Donation } from 'app/shared/model/donation.model';
import { DonationService } from './donation.service';
import { DonationComponent } from './donation.component';
import { DonationDetailComponent } from './donation-detail.component';
import { DonationUpdateComponent } from './donation-update.component';
import { DonationDeletePopupComponent } from './donation-delete-dialog.component';
import { IDonation } from 'app/shared/model/donation.model';

@Injectable({ providedIn: 'root' })
export class DonationResolve implements Resolve<IDonation> {
  constructor(private service: DonationService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IDonation> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<Donation>) => response.ok),
        map((donation: HttpResponse<Donation>) => donation.body)
      );
    }
    return of(new Donation());
  }
}

export const donationRoute: Routes = [
  {
    path: '',
    component: DonationComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'webApp.donation.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: DonationDetailComponent,
    resolve: {
      donation: DonationResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'webApp.donation.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: DonationUpdateComponent,
    resolve: {
      donation: DonationResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'webApp.donation.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: DonationUpdateComponent,
    resolve: {
      donation: DonationResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'webApp.donation.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const donationPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: DonationDeletePopupComponent,
    resolve: {
      donation: DonationResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'webApp.donation.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
