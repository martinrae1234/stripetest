import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { WebSharedModule } from '../shared/shared.module';
import { PAYMENTS_ROUTE, PaymentsComponent } from './';

@NgModule({
  imports: [WebSharedModule, RouterModule.forChild([PAYMENTS_ROUTE]), FormsModule, ReactiveFormsModule],
  declarations: [PaymentsComponent],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class WebPaymentModule {}
// JHipster Stripe Module will add new line here
