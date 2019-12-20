import { Route } from '@angular/router';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';

import { PaymentsComponent } from './';

export const PAYMENTS_ROUTE: Route = {
  path: 'payments',
  component: PaymentsComponent,
  data: {
    authorities: [],
    pageTitle: 'Payment'
  },
  canActivate: [UserRouteAccessService]
};
