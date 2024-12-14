import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';

import {BookstoreRoutingModule} from './bookstore-routing.module';
import {BookstoreComponent} from './bookstore.component';

@NgModule({
  declarations: [BookstoreComponent],
  imports: [CommonModule, BookstoreRoutingModule]
})
export class BookstoreModule {}
