import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import './vendor';
import { WebSharedModule } from 'app/shared/shared.module';
import { WebCoreModule } from 'app/core/core.module';
import { WebAppRoutingModule } from './app-routing.module';
import { WebHomeModule } from './home/home.module';
import { WebStaticPagesModule } from './staticPages/staticPages.module';
import { WebEntityModule } from './entities/entity.module';
// jhipster-needle-angular-add-module-import JHipster will add new module here
import { JhiMainComponent } from './layouts/main/main.component';
import { NavbarComponent } from './layouts/navbar/navbar.component';
import { FooterComponent } from './layouts/footer/footer.component';
import { PageRibbonComponent } from './layouts/profiles/page-ribbon.component';
import { ActiveMenuDirective } from './layouts/navbar/active-menu.directive';
import { ErrorComponent } from './layouts/error/error.component';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';

@NgModule({
  imports: [
    BrowserModule,
    WebSharedModule,
    WebCoreModule,
    WebHomeModule,
    WebStaticPagesModule,
    // jhipster-needle-angular-add-module JHipster will add new module here
    WebEntityModule,
    WebAppRoutingModule,
    NgbModule
  ],
  declarations: [JhiMainComponent, NavbarComponent, ErrorComponent, PageRibbonComponent, ActiveMenuDirective, FooterComponent],
  bootstrap: [JhiMainComponent]
})
export class WebAppModule {}
