import { Route } from '@angular/router';
import { WebThanksComponent } from './thanks.component';

export const THANKS_ROUTE: Route = {
  path: 'thanks',
  component: WebThanksComponent,
  data: {
    authorities: [],
    pageTitle: 'home.thanks'
  }
};
