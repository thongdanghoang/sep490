import {NgModule, Optional, SkipSelf} from '@angular/core';
import {throwIfAlreadyLoaded} from './module-import-guard';
import {CommonModule} from '@angular/common';
import {ApplicationService} from './services/application.service';
import {MessageService} from 'primeng/api';
import {DialogService} from 'primeng/dynamicdialog';

@NgModule({
  declarations: [],
  imports: [CommonModule],
  providers: [ApplicationService, MessageService, DialogService]
})
export class CoreModule {
  constructor(@Optional() @SkipSelf() parentModule: CoreModule) {
    throwIfAlreadyLoaded(parentModule, 'CoreModule');
  }
}
