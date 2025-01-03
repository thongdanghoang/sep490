import {CommonModule, DatePipe, NgOptimizedImage} from '@angular/common';
import {HttpClientModule} from '@angular/common/http';
import {NgModule} from '@angular/core';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {RouterModule} from '@angular/router';
import {TranslateModule} from '@ngx-translate/core';
import {AutoFocusModule} from 'primeng/autofocus';
import {ButtonModule} from 'primeng/button';
import {DialogModule} from 'primeng/dialog';
import {DynamicDialog} from 'primeng/dynamicdialog';
import {FloatLabelModule} from 'primeng/floatlabel';
import {InputSwitchModule} from 'primeng/inputswitch';
import {InputTextModule} from 'primeng/inputtext';
import {MenubarModule} from 'primeng/menubar';
import {PasswordModule} from 'primeng/password';
import {RippleModule} from 'primeng/ripple';
import {TableModule} from 'primeng/table';
import {TabMenuModule} from 'primeng/tabmenu';
import {ToastModule} from 'primeng/toast';
import {ConfirmDialogComponent} from './components/dialog/confirm-dialog/confirm-dialog.component';
import {ModalProvider} from './services/modal-provider';
import {AvatarModule} from 'primeng/avatar';
import {MenuModule} from 'primeng/menu';
import {BadgeModule} from 'primeng/badge';
import {Select} from 'primeng/select';
import {DrawerModule} from 'primeng/drawer';
import {TranslateParamsPipe} from './pipes/translate-params.pipe';

const primeNgModules = [
  AutoFocusModule,
  AvatarModule,
  BadgeModule,
  ButtonModule,
  DialogModule,
  DrawerModule,
  DynamicDialog,
  FloatLabelModule,
  InputTextModule,
  InputSwitchModule,
  MenuModule,
  MenubarModule,
  Select,
  TableModule,
  TabMenuModule,
  ToastModule,
  PasswordModule,
  RippleModule
];

const commons = [
  CommonModule,
  RouterModule,
  HttpClientModule,
  FormsModule,
  ReactiveFormsModule,
  NgOptimizedImage,
  TranslateModule
];

@NgModule({
  declarations: [ConfirmDialogComponent, TranslateParamsPipe],
  imports: [...commons, ...primeNgModules],
  exports: [...commons, ...primeNgModules],
  providers: [DatePipe, ModalProvider]
})
export class SharedModule {}
