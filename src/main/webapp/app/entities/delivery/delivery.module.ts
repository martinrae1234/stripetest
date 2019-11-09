import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { WebSharedModule } from 'app/shared/shared.module';
import { DeliveryComponent } from './delivery.component';
import { DeliveryDetailComponent } from './delivery-detail.component';
import { DeliveryUpdateComponent } from './delivery-update.component';
import { DeliveryDeletePopupComponent, DeliveryDeleteDialogComponent } from './delivery-delete-dialog.component';
import { deliveryRoute, deliveryPopupRoute } from './delivery.route';

const ENTITY_STATES = [...deliveryRoute, ...deliveryPopupRoute];

@NgModule({
  imports: [WebSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    DeliveryComponent,
    DeliveryDetailComponent,
    DeliveryUpdateComponent,
    DeliveryDeleteDialogComponent,
    DeliveryDeletePopupComponent
  ],
  entryComponents: [DeliveryDeleteDialogComponent]
})
export class WebDeliveryModule {}
