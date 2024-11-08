import {NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {ButtonModule} from 'primeng/button';
import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';

const primeNgModules = [ButtonModule];

@NgModule({
  declarations: [
    AppComponent
  ],
  imports: [
    ...primeNgModules,
    BrowserModule,
    BrowserAnimationsModule,
    AppRoutingModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule {
}
