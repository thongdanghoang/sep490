import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {BookstoreComponent} from './bookstore.component';

const routes: Routes = [{path: '', component: BookstoreComponent}];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class BookstoreRoutingModule {}
