import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { News } from 'app/shared/model/news.model';
import { NewsService } from './news.service';
import { NewsComponent } from './news.component';
import { NewsHomeComponent } from './news-home.component';
import { NewsDetailComponent } from './news-detail.component';
import { NewsUpdateComponent } from './news-update.component';
import { NewsDeletePopupComponent } from './news-delete-dialog.component';
import { INews } from 'app/shared/model/news.model';

@Injectable({ providedIn: 'root' })
export class NewsResolve implements Resolve<INews> {
  constructor(private service: NewsService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<INews> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<News>) => response.ok),
        map((news: HttpResponse<News>) => news.body)
      );
    }
    return of(new News());
  }
}

export const newsRoute: Routes = [
  {
    path: 'news',
    component: NewsComponent,
    data: {
      authorities: []
    }
  },
  {
    path: 'news-view',
    component: NewsHomeComponent,
    data: {
      authorities: []
    }
  },
  {
    path: ':id/view',
    component: NewsDetailComponent,
    resolve: {
      news: NewsResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'News'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: NewsUpdateComponent,
    resolve: {
      news: NewsResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'News'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: NewsUpdateComponent,
    resolve: {
      news: NewsResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'News'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const newsPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: NewsDeletePopupComponent,
    resolve: {
      news: NewsResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'News'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
