import {CommonModule, DatePipe} from '@angular/common';
import {HTTP_INTERCEPTORS, HttpClientModule} from '@angular/common/http';
import {NgModule} from '@angular/core';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {BrowserModule} from '@angular/platform-browser';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {RouterModule} from '@angular/router';
import {AutoFocusModule} from 'primeng/autofocus';
import {ButtonModule} from 'primeng/button';
import {FloatLabelModule} from 'primeng/floatlabel';
import {InputSwitchModule} from 'primeng/inputswitch';
import {InputTextModule} from 'primeng/inputtext';
import {PasswordModule} from 'primeng/password';
import {RippleModule} from 'primeng/ripple';
import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {CoreModule} from './modules/core/core.module';
import {AuthInterceptor, AuthModule, LogLevel} from 'angular-auth-oidc-client';
import { ForbiddenComponent } from './components/forbidden/forbidden.component';
import { UnauthorizedComponent } from './components/unauthorized/unauthorized.component';
import { NotFoundComponent } from './components/not-found/not-found.component';

const primeNgModules = [
  AutoFocusModule,
  ButtonModule,
  FloatLabelModule,
  InputTextModule,
  PasswordModule,
  RippleModule,
  InputSwitchModule
];

const coreModules = [
  CommonModule,
  HttpClientModule,
  RouterModule,
  FormsModule,
  ReactiveFormsModule,
  DatePipe
];

const oidcClientConfig = AuthModule.forRoot({
  config: {
    authority: 'http://localhost:8080',
    redirectUrl: window.location.origin,
    postLogoutRedirectUri: window.location.origin,
    clientId: 'oidc-client',
    scope: 'openid', // 'openid profile ' + your scopes
    responseType: 'code',
    silentRenew: false,
    useRefreshToken: false,
    logLevel: LogLevel.Debug,
    secureRoutes: ['http://localhost:8081'],
  }
});

@NgModule({
  declarations: [
    AppComponent,
    ForbiddenComponent,
    UnauthorizedComponent,
    NotFoundComponent
  ],
  imports: [
    ...primeNgModules,
    ...coreModules,
    BrowserModule,
    BrowserAnimationsModule,
    AppRoutingModule,
    CoreModule,
    oidcClientConfig
  ],
  providers: [
    {provide: HTTP_INTERCEPTORS, useClass: AuthInterceptor, multi: true}
  ],
  bootstrap: [AppComponent]
})
export class AppModule {
}
