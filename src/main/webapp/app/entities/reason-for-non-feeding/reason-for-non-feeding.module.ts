import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { WebSharedModule } from 'app/shared/shared.module';
import { ReasonForNonFeedingComponent } from './reason-for-non-feeding.component';
import { ReasonForNonFeedingDetailComponent } from './reason-for-non-feeding-detail.component';
import { ReasonForNonFeedingUpdateComponent } from './reason-for-non-feeding-update.component';
import {
  ReasonForNonFeedingDeletePopupComponent,
  ReasonForNonFeedingDeleteDialogComponent
} from './reason-for-non-feeding-delete-dialog.component';
import { reasonForNonFeedingRoute, reasonForNonFeedingPopupRoute } from './reason-for-non-feeding.route';

const ENTITY_STATES = [...reasonForNonFeedingRoute, ...reasonForNonFeedingPopupRoute];

@NgModule({
  imports: [WebSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    ReasonForNonFeedingComponent,
    ReasonForNonFeedingDetailComponent,
    ReasonForNonFeedingUpdateComponent,
    ReasonForNonFeedingDeleteDialogComponent,
    ReasonForNonFeedingDeletePopupComponent
  ],
  entryComponents: [ReasonForNonFeedingDeleteDialogComponent]
})
export class WebReasonForNonFeedingModule {}
