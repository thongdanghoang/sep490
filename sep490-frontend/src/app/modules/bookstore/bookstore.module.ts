import {NgOptimizedImage} from '@angular/common';
import {NgModule} from '@angular/core';
import {SharedModule} from '../shared/shared.module';

import {BookstoreRoutingModule} from './bookstore-routing.module';
import {BookstoreComponent} from './bookstore.component';

@NgModule({
  declarations: [BookstoreComponent],
  imports: [SharedModule, BookstoreRoutingModule, NgOptimizedImage],
})
export class BookstoreModule {}
