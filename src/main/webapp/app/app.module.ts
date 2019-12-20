import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import './vendor';
// import { HTTP_INTERCEPTORS } from '@angular/common/http';
import { NgbDatepickerConfig } from '@ng-bootstrap/ng-bootstrap';
// import { Ng2Webstorage } from 'ngx-webstorage';
import { NgJhipsterModule } from 'ng-jhipster';

// import { AuthInterceptor } from './blocks/interceptor/auth.interceptor';
// import { AuthExpiredInterceptor } from './blocks/interceptor/auth-expired.interceptor';
// import { ErrorHandlerInterceptor } from './blocks/interceptor/errorhandler.interceptor';
// import { NotificationInterceptor } from './blocks/interceptor/notification.interceptor';
import * as moment from 'moment';
import { NgxStripeModule } from 'ngx-stripe';
import { WebSharedModule } from 'app/shared/shared.module';
import { WebCoreModule } from 'app/core/core.module';
import { WebAppRoutingModule } from './app-routing.module';
import { WebHomeModule } from './home/home.module';
import { WebPaymentModule } from './payments/payments.module';
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
import { WebThanksModule } from './thanks/thanks.module';

@NgModule({
  imports: [
    BrowserModule,
    WebSharedModule,
    WebCoreModule,
    WebHomeModule,
    WebPaymentModule,
    WebStaticPagesModule,
    WebThanksModule,
    // jhipster-needle-angular-add-module JHipster will add new module here
    WebEntityModule,
    WebAppRoutingModule,
    NgbModule,
    NgJhipsterModule.forRoot({
      alertAsToast: false,
      alertTimeout: 5000
    }),
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
