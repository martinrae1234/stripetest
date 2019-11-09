import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { WebSharedModule } from 'app/shared/shared.module';
import { StaffComponent } from './staff.component';
import { StaffDetailComponent } from './staff-detail.component';
import { StaffUpdateComponent } from './staff-update.component';
import { StaffDeletePopupComponent, StaffDeleteDialogComponent } from './staff-delete-dialog.component';
import { staffRoute, staffPopupRoute } from './staff.route';

const ENTITY_STATES = [...staffRoute, ...staffPopupRoute];

@NgModule({
  imports: [WebSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [StaffComponent, StaffDetailComponent, StaffUpdateComponent, StaffDeleteDialogComponent, StaffDeletePopupComponent],
  entryComponents: [StaffDeleteDialogComponent]
})
export class WebStaffModule {}
