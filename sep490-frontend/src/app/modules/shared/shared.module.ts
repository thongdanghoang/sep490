import {NgModule} from '@angular/core';
import {CommonModule, DatePipe} from '@angular/common';
import {TranslateModule} from '@ngx-translate/core';
import {AutoFocusModule} from 'primeng/autofocus';
import {ButtonModule} from 'primeng/button';
import {FloatLabelModule} from 'primeng/floatlabel';
import {InputTextModule} from 'primeng/inputtext';
import {PasswordModule} from 'primeng/password';
import {RippleModule} from 'primeng/ripple';
import {InputSwitchModule} from 'primeng/inputswitch';
import {HttpClientModule} from '@angular/common/http';
import {RouterModule} from '@angular/router';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {MenubarModule} from 'primeng/menubar';
import {TabMenuModule} from 'primeng/tabmenu';
import {DynamicDialogModule} from 'primeng/dynamicdialog';
import {DialogModule} from 'primeng/dialog';
import {ToastModule} from 'primeng/toast';
import {ModalProvider} from './services/modal-provider';
import {ConfirmDialogComponent} from './components/dialog/confirm-dialog/confirm-dialog.component';
import {ErrorMessagesDirective} from './directives/error-messages.directive';
import {ErrorDirective} from './directives/error.directive';
import {TranslateParamsPipe} from './pipes/translate-params.pipe';

const primeNgModules = [
  AutoFocusModule,
  ButtonModule,
  DialogModule,
  DynamicDialogModule,
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
