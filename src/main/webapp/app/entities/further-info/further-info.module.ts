import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { WebSharedModule } from 'app/shared/shared.module';
import { FurtherInfoComponent } from './further-info.component';
import { FurtherInfoDetailComponent } from './further-info-detail.component';
import { FurtherInfoUpdateComponent } from './further-info-update.component';
import { FurtherInfoDeletePopupComponent, FurtherInfoDeleteDialogComponent } from './further-info-delete-dialog.component';
import { furtherInfoRoute, furtherInfoPopupRoute } from './further-info.route';
import { FurtherInfoHomeComponent } from './further-info-home.component';

const ENTITY_STATES = [...furtherInfoRoute, ...furtherInfoPopupRoute];

@NgModule({
  imports: [WebSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    FurtherInfoComponent,
    FurtherInfoDetailComponent,
    FurtherInfoUpdateComponent,
    FurtherInfoHomeComponent,
    FurtherInfoDeleteDialogComponent,
    FurtherInfoDeletePopupComponent
  ],
  exports: [FurtherInfoComponent, FurtherInfoHomeComponent],
  entryComponents: [FurtherInfoDeleteDialogComponent]
})
export class WebFurtherInfoModule {}