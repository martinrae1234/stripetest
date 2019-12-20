import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { WebSharedModule } from 'app/shared/shared.module';
import { DonationComponent } from './donation.component';
import { DonationDetailComponent } from './donation-detail.component';
import { DonationUpdateComponent } from './donation-update.component';
import { DonationDeletePopupComponent, DonationDeleteDialogComponent } from './donation-delete-dialog.component';
import { donationRoute, donationPopupRoute } from './donation.route';
import { DonationScreenComponent } from './donation-screen.component';

const ENTITY_STATES = [...donationRoute, ...donationPopupRoute];

@NgModule({
  imports: [WebSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    DonationComponent,
    DonationScreenComponent,
    DonationDetailComponent,
    DonationUpdateComponent,
    DonationDeleteDialogComponent,
    DonationDeletePopupComponent,
    DonationScreenComponent
  ],
  entryComponents: [DonationDeleteDialogComponent]
})
export class WebDonationModule {}
