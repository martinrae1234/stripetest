import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import './vendor';
import { WebSharedModule } from 'app/shared/shared.module';
import { WebCoreModule } from 'app/core/core.module';
import { WebAppRoutingModule } from './app-routing.module';
import { WebHomeModule } from './home/home.module';
import { WebStaticPagesModule } from './staticPages/staticPages.module';
import { WebEntityModule } from './entities/entity.module';
import { JhiMainComponent } from './layouts/main/main.component';
import { NavbarComponent } from './layouts/navbar/navbar.component';
import { FooterComponent } from './layouts/footer/footer.component';
import { PageRibbonComponent } from './layouts/profiles/page-ribbon.component';
import { ActiveMenuDirective } from './layouts/navbar/active-menu.directive';
import { ErrorComponent } from './layouts/error/error.component';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';

import { WebThanksModule } from './thanks/thanks.module';
import { NgbDatepickerConfig } from '@ng-bootstrap/ng-bootstrap';
// import { NgJhipsterModule } from 'ng-jhipster';
import * as moment from 'moment';
import { NgxStripeModule } from 'ngx-stripe';
import { WebPaymentModule } from './payments/payments.module';

@NgModule({
  imports: [
    BrowserModule,
    WebSharedModule,
    WebCoreModule,
    WebHomeModule,
    WebPaymentModule,
    WebStaticPagesModule,
    WebThanksModule,
    WebEntityModule,
    WebAppRoutingModule,
    NgbModule,
    // NgJhipsterModule.forRoot({
    // alertAsToast: false,
    // alertTimeout: 5000
    // }),
    NgxStripeModule.forRoot('pk_test_rkNJwqRikhCoV2Jws1ry3Chi')
  ],
  declarations: [JhiMainComponent, NavbarComponent, ErrorComponent, PageRibbonComponent, ActiveMenuDirective, FooterComponent],
  bootstrap: [JhiMainComponent]
})
export class WebAppModule {
  constructor(private dpConfig: NgbDatepickerConfig) {
    this.dpConfig.minDate = { year: moment().year() - 100, month: 1, day: 1 };
  }
}
