import {Component} from '@angular/core';
import {Book} from './models/book';

@Component({
  selector: 'app-bookstore',
  templateUrl: './bookstore.component.html',
  styleUrl: './bookstore.component.css'
})
export class BookstoreComponent {
  bestSellersImages: string[] = [
    'assets/images/burgers.161c4a44.png',
    'assets/images/italian.92245953.png',
    'assets/images/noodles.86634adf.png',
    'assets/images/pancakes.22f42738.png',
    'assets/images/pineapple.203d8247.png',
    'assets/images/umami.c6f148dc.png'
  ];
  cookbooks: Book[] = [
    {
      id: 'zceo3fdn-d93b-11e8-9f8b-f2801f1b9fd1',
      cover:
        'https://d2z6cj5wcte8g7.cloudfront.net/book-covers/Lamb_grilling_guide.png',
      price: 20.99,
      category: 'Cookbooks',
      name: 'Lamb Grilling Guide',
      rating: 4,
      author: 'Jake Jakubowski'
    }
  ];
}
