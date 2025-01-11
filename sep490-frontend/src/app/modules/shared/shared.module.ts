import {CommonModule, DatePipe, NgOptimizedImage} from '@angular/common';
import {HttpClientModule} from '@angular/common/http';
import {NgModule} from '@angular/core';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {RouterModule} from '@angular/router';
import {TranslateModule} from '@ngx-translate/core';
import {AutoFocusModule} from 'primeng/autofocus';
import {AvatarModule} from 'primeng/avatar';
import {BadgeModule} from 'primeng/badge';
import {ButtonModule} from 'primeng/button';
import {DialogModule} from 'primeng/dialog';
import {DrawerModule} from 'primeng/drawer';
import {DynamicDialog} from 'primeng/dynamicdialog';
import {FloatLabelModule} from 'primeng/floatlabel';
import {IconField} from 'primeng/iconfield';
import {InputIcon} from 'primeng/inputicon';
import {InputSwitchModule} from 'primeng/inputswitch';
import {InputTextModule} from 'primeng/inputtext';
import {MenuModule} from 'primeng/menu';
import {MenubarModule} from 'primeng/menubar';
import {PaginatorModule} from 'primeng/paginator';
import {PasswordModule} from 'primeng/password';
import {RippleModule} from 'primeng/ripple';
import {Select} from 'primeng/select';
import {TableModule} from 'primeng/table';
import {TabMenuModule} from 'primeng/tabmenu';
import {ToastModule} from 'primeng/toast';
import {ConfirmDialogComponent} from './components/dialog/confirm-dialog/confirm-dialog.component';
import {TableTemplateComponent} from './components/table-template/table-template.component';
import {TranslateParamsPipe} from './pipes/translate-params.pipe';
import {ModalProvider} from './services/modal-provider';

const primeNgModules = [
  AutoFocusModule,
  AvatarModule,
  BadgeModule,
  ButtonModule,
  DialogModule,
  DrawerModule,
  DynamicDialog,
  FloatLabelModule,
  IconField,
  InputIcon,
  InputTextModule,
  InputSwitchModule,
  MenuModule,
  MenubarModule,
  Select,
  TableModule,
  TabMenuModule,
  ToastModule,
  PaginatorModule,
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
  declarations: [
    ConfirmDialogComponent,
    TranslateParamsPipe,
    TableTemplateComponent
  ],
  imports: [...commons, ...primeNgModules],
  exports: [
    ...commons,
    ...primeNgModules,
    ConfirmDialogComponent,
    TranslateParamsPipe,
    TableTemplateComponent
  ],
  providers: [DatePipe, ModalProvider]
})
export class SharedModule {}
