import {NgModule} from '@angular/core';
import {SharedModule} from '../shared/shared.module';

import {AdminComponent} from './admin.component';
import {AdminRoutingModule} from './admin-routing.module';
import {PackageCreditComponent} from './components/package-credit/package-credit.component';
import {PackageCreditService} from './services/package-credit.service';

@NgModule({
  declarations: [AdminComponent, PackageCreditComponent],
  imports: [SharedModule, AdminRoutingModule],
  providers: [PackageCreditService]
})
export class AdminModule {}
