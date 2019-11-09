import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { WebSharedModule } from 'app/shared/shared.module';
import { AffiliateComponent } from './affiliate.component';
import { AffiliateDetailComponent } from './affiliate-detail.component';
import { AffiliateUpdateComponent } from './affiliate-update.component';
import { AffiliateDeletePopupComponent, AffiliateDeleteDialogComponent } from './affiliate-delete-dialog.component';
import { affiliateRoute, affiliatePopupRoute } from './affiliate.route';

const ENTITY_STATES = [...affiliateRoute, ...affiliatePopupRoute];

@NgModule({
  imports: [WebSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    AffiliateComponent,
    AffiliateDetailComponent,
    AffiliateUpdateComponent,
    AffiliateDeleteDialogComponent,
    AffiliateDeletePopupComponent
  ],
  entryComponents: [AffiliateDeleteDialogComponent]
})
export class WebAffiliateModule {}
