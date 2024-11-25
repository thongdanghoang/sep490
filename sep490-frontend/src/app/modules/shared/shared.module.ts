import {CommonModule, DatePipe} from '@angular/common';
import {HttpClientModule} from '@angular/common/http';
import {NgModule} from '@angular/core';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {RouterModule} from '@angular/router';
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
import {TabMenuModule} from 'primeng/tabmenu';
import {ToastModule} from 'primeng/toast';
import {ConfirmDialogComponent} from './components/dialog/confirm-dialog/confirm-dialog.component';
import {ModalProvider} from './services/modal-provider';
import {TranslateModule} from '@ngx-translate/core';
import {ErrorMessagesDirective} from './directives/error-messages.directive';
import {ErrorDirective} from './directives/error.directive';
import {TranslateParamsPipe} from './pipes/translate-params.pipe';

const primeNgModules = [
  AutoFocusModule,
  ButtonModule,
  DialogModule,
  DynamicDialog,
  FloatLabelModule,
  InputTextModule,
  InputSwitchModule,
  MenubarModule,
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
  TranslateModule
];

@NgModule({
  declarations: [
    ConfirmDialogComponent,
    ErrorMessagesDirective,
    ErrorDirective,
    TranslateParamsPipe
  ],
  imports: [...commons, ...primeNgModules],
  exports: [
    ...commons,
    ...primeNgModules,
    ConfirmDialogComponent,
    ErrorMessagesDirective,
    ErrorDirective,
    TranslateParamsPipe
  ],
  providers: [DatePipe, ModalProvider]
})
export class SharedModule {}
