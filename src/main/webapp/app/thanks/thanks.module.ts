import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { WebSharedModule } from 'app/shared/shared.module';
import { THANKS_ROUTE } from './thanks.route';
import { WebThanksComponent } from './thanks.component';

@NgModule({
  imports: [WebSharedModule, RouterModule.forChild([THANKS_ROUTE])],
  declarations: [WebThanksComponent]
})
export class WebThanksModule {}
