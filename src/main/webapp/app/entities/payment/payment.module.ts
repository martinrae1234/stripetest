import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { WebSharedModule } from 'app/shared/shared.module';
import { PaymentComponent } from './payment.component';
import { paymentPopupRoute, paymentRoute } from './payment.route';
import { PaymentDetailComponent } from './payment-detail.component';
import { PaymentUpdateComponent } from './payment-update.component';
import { PaymentDeleteDialogComponent } from './payment-delete-dialog.component';
import { PaymentDeletePopupComponent } from './payment-delete-dialog.component';

const ENTITY_STATES = [...paymentRoute, ...paymentPopupRoute];

@NgModule({
  imports: [WebSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    PaymentComponent,
    PaymentDetailComponent,
    PaymentUpdateComponent,
    PaymentDeleteDialogComponent,
    PaymentDeletePopupComponent
  ],
  entryComponents: [PaymentComponent, PaymentUpdateComponent, PaymentDeleteDialogComponent, PaymentDeletePopupComponent],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class WebPaymentModule {}
