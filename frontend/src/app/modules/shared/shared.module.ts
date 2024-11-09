import {NgModule} from '@angular/core';
import {CommonModule, DatePipe} from '@angular/common';
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

const primeNgModules = [
  AutoFocusModule,
  ButtonModule,
  FloatLabelModule,
  InputTextModule,
  InputSwitchModule,
  MenubarModule,
  TabMenuModule,
  PasswordModule,
  RippleModule,
];

const coreModules = [
  CommonModule,
  RouterModule,
  HttpClientModule,
  FormsModule,
  ReactiveFormsModule,
  DatePipe
];

@NgModule({
  declarations: [],
  imports: [
    ...coreModules,
    ...primeNgModules
  ],
  exports: [
    ...coreModules,
    ...primeNgModules
  ]
})
export class SharedModule {
}
