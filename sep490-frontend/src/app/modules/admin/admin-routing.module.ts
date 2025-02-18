import {RouterModule, Routes} from '@angular/router';
import {AppRoutingConstants} from '../../app-routing.constant';

import {NgModule} from '@angular/core';
import {AdminComponent} from './admin.component';
import {PackageCreditComponent} from './components/package-credit/package-credit.component';

const routes: Routes = [
  {
    path: '',
    component: AdminComponent,
    children: [
      {
        path: AppRoutingConstants.PACKAGE_CREDIT_PATH,
        component: PackageCreditComponent
      }
    ]
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class AdminRoutingModule {}
