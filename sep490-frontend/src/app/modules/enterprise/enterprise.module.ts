import {NgModule} from '@angular/core';

import {EnterpriseRoutingModule} from './enterprise-routing.module';
import {EnterpriseComponent} from './enterprise.component';
import {SharedModule} from '../shared/shared.module';
import {MarkerService} from './services/marker.service';
import {PopupService} from './services/popup.service';
import {RegionService} from './services/region.service';


@NgModule({
  declarations: [
    EnterpriseComponent
  ],
  imports: [
    SharedModule,
    EnterpriseRoutingModule
  ],
  providers: [MarkerService, PopupService, RegionService]
})
export class EnterpriseModule {
}
