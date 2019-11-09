import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { WebSharedModule } from 'app/shared/shared.module';
import { SchoolComponent } from './school.component';
import { SchoolDetailComponent } from './school-detail.component';
import { SchoolUpdateComponent } from './school-update.component';
import { SchoolDeletePopupComponent, SchoolDeleteDialogComponent } from './school-delete-dialog.component';
import { schoolRoute, schoolPopupRoute } from './school.route';

const ENTITY_STATES = [...schoolRoute, ...schoolPopupRoute];

@NgModule({
  imports: [WebSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [SchoolComponent, SchoolDetailComponent, SchoolUpdateComponent, SchoolDeleteDialogComponent, SchoolDeletePopupComponent],
  entryComponents: [SchoolDeleteDialogComponent]
})
export class WebSchoolModule {}
