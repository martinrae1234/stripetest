import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'home-page',
        loadChildren: () => import('./home-page/home-page.module').then(m => m.WebHomePageModule)
      },
      {
        path: 'message',
        loadChildren: () => import('./message/message.module').then(m => m.WebMessageModule)
      },
      {
        path: 'news',
        loadChildren: () => import('./news/news.module').then(m => m.WebNewsModule)
      },
      {
        path: 'further-info',
        loadChildren: () => import('./further-info/further-info.module').then(m => m.WebFurtherInfoModule)
      },
      {
        path: 'donation',
        loadChildren: () => import('./donation/donation.module').then(m => m.WebDonationModule)
      },
      {
        path: 'supporter',
        loadChildren: () => import('./supporter/supporter.module').then(m => m.WebSupporterModule)
      },
      {
        path: 'staff',
        loadChildren: () => import('./staff/staff.module').then(m => m.WebStaffModule)
      },
      {
        path: 'affiliate',
        loadChildren: () => import('./affiliate/affiliate.module').then(m => m.WebAffiliateModule)
      },
      {
        path: 'project',
        loadChildren: () => import('./project/project.module').then(m => m.WebProjectModule)
      },
      {
        path: 'event',
        loadChildren: () => import('./event/event.module').then(m => m.WebEventModule)
      },
      {
        path: 'school',
        loadChildren: () => import('./school/school.module').then(m => m.WebSchoolModule)
      },
      {
        path: 'commodity',
        loadChildren: () => import('./commodity/commodity.module').then(m => m.WebCommodityModule)
      },
      {
        path: 'reason-for-non-feeding',
        loadChildren: () => import('./reason-for-non-feeding/reason-for-non-feeding.module').then(m => m.WebReasonForNonFeedingModule)
      },
      {
        path: 'delivery',
        loadChildren: () => import('./delivery/delivery.module').then(m => m.WebDeliveryModule)
      }
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ])
  ]
})
export class WebEntityModule {}
