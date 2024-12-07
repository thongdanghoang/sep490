import {NgModule, Optional, SkipSelf} from '@angular/core';
import {throwIfAlreadyLoaded} from './module-import-guard';
import {CommonModule} from '@angular/common';
import {ThemeService} from './services/theme.service';
import {ApplicationService} from './services/application.service';
import {MessageService} from 'primeng/api';
import {DialogService} from 'primeng/dynamicdialog';

@NgModule({
  declarations: [],
  imports: [CommonModule],
  providers: [ApplicationService, ThemeService, MessageService, DialogService]
})
export class CoreModule {
  constructor(@Optional() @SkipSelf() parentModule: CoreModule) {
    throwIfAlreadyLoaded(parentModule, 'CoreModule');
  }
}
