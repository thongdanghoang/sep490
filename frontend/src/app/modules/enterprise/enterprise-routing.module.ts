import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {EnterpriseComponent} from './enterprise.component';

const routes: Routes = [{ path: '', component: EnterpriseComponent }];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class EnterpriseRoutingModule { }
