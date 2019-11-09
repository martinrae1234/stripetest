import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { WebSharedModule } from 'app/shared/shared.module';
import { HOME_ROUTE } from './home.route';
import { HomeComponent } from './home.component';
import { WebMessageModule } from 'app/entities/message/message.module';
import { WebNewsModule } from 'app/entities/news/news.module';
import { WebFurtherInfoModule } from 'app/entities/further-info/further-info.module';

@NgModule({
  imports: [WebFurtherInfoModule, WebNewsModule, WebMessageModule, WebSharedModule, RouterModule.forChild([HOME_ROUTE])],
  declarations: [HomeComponent]
})
export class WebHomeModule {}
