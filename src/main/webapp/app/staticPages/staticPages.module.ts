import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { WebSharedModule } from 'app/shared/shared.module';
import { STATICPAGES_ROUTE } from './staticPages.route';
import { TermsOfUseComponent } from './terms-of-use.component';


@NgModule({
  imports: [ WebSharedModule, RouterModule.forChild([STATICPAGES_ROUTE])],
  declarations: [TermsOfUseComponent]
})
export class WebStaticPagesModule {}
