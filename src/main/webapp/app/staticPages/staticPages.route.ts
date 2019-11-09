import { Route } from '@angular/router';
import { TermsOfUseComponent } from './terms-of-use.component';

export const STATICPAGES_ROUTE: Route = {
  path: 'terms',
  component: TermsOfUseComponent,
  data: {
    authorities: [],
    pageTitle: 'home.terms-of-use'
  }
};

