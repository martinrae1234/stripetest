import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { WebSharedModule } from 'app/shared/shared.module';
import { NewsComponent } from './news.component';
import { NewsDetailComponent } from './news-detail.component';
import { NewsUpdateComponent } from './news-update.component';
import { NewsDeletePopupComponent, NewsDeleteDialogComponent } from './news-delete-dialog.component';
import { newsRoute, newsPopupRoute } from './news.route';
import { NewsHomeComponent } from './news-home.component';

const ENTITY_STATES = [...newsRoute, ...newsPopupRoute];

@NgModule({
  imports: [WebSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [NewsComponent, NewsHomeComponent, NewsDetailComponent, NewsUpdateComponent, NewsDeleteDialogComponent, NewsDeletePopupComponent],
  exports: [NewsComponent, NewsHomeComponent],
  entryComponents: [NewsDeleteDialogComponent]
})
export class WebNewsModule {}
