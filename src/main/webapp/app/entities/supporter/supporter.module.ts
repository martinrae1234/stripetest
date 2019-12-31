import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { WebSharedModule } from 'app/shared/shared.module';
import { SupporterComponent } from './supporter.component';
import { SupporterDetailComponent } from './supporter-detail.component';
import { SupporterUpdateComponent } from './supporter-update.component';
import { SupporterDeletePopupComponent, SupporterDeleteDialogComponent } from './supporter-delete-dialog.component';
import { supporterRoute, supporterPopupRoute } from './supporter.route';
import { SupporterScreenComponent } from './supporter-screen.component';

const ENTITY_STATES = [...supporterRoute, ...supporterPopupRoute];

@NgModule({
  imports: [WebSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    SupporterComponent,
    SupporterDetailComponent,
    SupporterUpdateComponent,
    SupporterDeleteDialogComponent,
    SupporterDeletePopupComponent,
    SupporterScreenComponent
  ],
  entryComponents: [SupporterDeleteDialogComponent]
})
export class WebSupporterModule {}
