import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { WebSharedModule } from 'app/shared/shared.module';
import { CommodityComponent } from './commodity.component';
import { CommodityDetailComponent } from './commodity-detail.component';
import { CommodityUpdateComponent } from './commodity-update.component';
import { CommodityDeletePopupComponent, CommodityDeleteDialogComponent } from './commodity-delete-dialog.component';
import { commodityRoute, commodityPopupRoute } from './commodity.route';

const ENTITY_STATES = [...commodityRoute, ...commodityPopupRoute];

@NgModule({
  imports: [WebSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    CommodityComponent,
    CommodityDetailComponent,
    CommodityUpdateComponent,
    CommodityDeleteDialogComponent,
    CommodityDeletePopupComponent
  ],
  entryComponents: [CommodityDeleteDialogComponent]
})
export class WebCommodityModule {}
