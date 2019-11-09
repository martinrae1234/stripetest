import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { WebSharedModule } from 'app/shared/shared.module';
import { HomePageComponent } from './home-page.component';
import { HomePageDetailComponent } from './home-page-detail.component';
import { HomePageUpdateComponent } from './home-page-update.component';
import { HomePageDeletePopupComponent, HomePageDeleteDialogComponent } from './home-page-delete-dialog.component';
import { homePageRoute, homePagePopupRoute } from './home-page.route';

const ENTITY_STATES = [...homePageRoute, ...homePagePopupRoute];

@NgModule({
  imports: [WebSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    HomePageComponent,
    HomePageDetailComponent,
    HomePageUpdateComponent,
    HomePageDeleteDialogComponent,
    HomePageDeletePopupComponent
  ],
  entryComponents: [HomePageDeleteDialogComponent]
})
export class WebHomePageModule {}
