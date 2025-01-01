import {HttpClient} from '@angular/common/http';
import {Injectable} from '@angular/core';
import {Observable, of} from 'rxjs';
import {Book} from '../models/book';
import {Order, PastOrder} from '../models/order';
import {Recommendation} from '../models/recommendation';

@Injectable({
  providedIn: 'root'
})
export class BookService {
  constructor(private readonly httpClient: HttpClient) {}

  // eslint-disable-next-line @typescript-eslint/no-unused-vars,max-lines-per-function
  public listBook(category: string): Observable<Book[]> {
    // return API.get('books', `/books?category=${category}`, null);
    return of([
      {
        rating: 4,
        category: 'Cookbooks',
        cover:
          'https://d2z6cj5wcte8g7.cloudfront.net/book-covers/Lamb_grilling_guide.png',
        price: 20.99,
        id: 'zceo3fdn-d93b-11e8-9f8b-f2801f1b9fd1',
        name: 'Lamb Grilling Guide',
        author: 'Jake Jakubowski'
      },
      {
        rating: 1,
        category: 'Cookbooks',
        cover: 'https://d2z6cj5wcte8g7.cloudfront.net/book-covers/Dairy.png',
        price: 17.96,
        id: 'rkz1ljyg-d93b-11e8-9f8b-f2801f1b9fd1',
        name: 'Dairy',
        author: 'Laura Nader'
      },
      {
        rating: 3,
        category: 'Cookbooks',
        cover:
          'https://d2z6cj5wcte8g7.cloudfront.net/book-covers/Chasing_umami.png',
        price: 15.98,
        id: 'nuklcm5b-d93b-11e8-9f8b-f2801f1b9fd1',
        name: 'Chasing Umami',
        author: 'Miles Way'
      },
      {
        rating: 4,
        category: 'Cookbooks',
        cover:
          'https://d2z6cj5wcte8g7.cloudfront.net/book-covers/scream_ice_cream.png',
        price: 20.99,
        id: 'bm00o9jj-d93b-11e8-9f8b-f2801f1b9fd1',
        name: 'Scream Ice Cream',
        author: 'Richard Labadie'
      },
      {
        rating: 4,
        category: 'Cookbooks',
        cover: 'https://d2z6cj5wcte8g7.cloudfront.net/book-covers/SOJU.png',
        price: 15.98,
        id: 'ep003yyx-d93b-11e8-9f8b-f2801f1b9fd1',
        name: 'Soju',
        author: 'Rodirck Torp'
      },
      {
        rating: 4,
        category: 'Cookbooks',
        cover:
          'https://d2z6cj5wcte8g7.cloudfront.net/book-covers/the_perfect_roast.png',
        price: 15.99,
        id: '2shdmlp0-d93b-11e8-9f8b-f2801f1b9fd1',
        name: 'The Perfect Roast',
        author: 'Jaylen Anderson'
      },
      {
        rating: 4,
        category: 'Cookbooks',
        cover:
          'https://d2z6cj5wcte8g7.cloudfront.net/book-covers/bakers_dozen.png',
        price: 19.99,
        id: 'nrubyjwh-d93b-11e8-9f8b-f2801f1b9fd1',
        name: 'Bakers Dozen',
        author: 'Wilfredo Davis'
      },
      {
        rating: 5,
        category: 'Cookbooks',
        cover: 'https://d2z6cj5wcte8g7.cloudfront.net/book-covers/matcha.png',
        price: 22.96,
        id: '2k769fhx-d93b-11e8-9f8b-f2801f1b9fd1',
        name: 'Matcha',
        author: 'Stuart Hessel'
      },
      {
        rating: 3,
        category: 'Cookbooks',
        cover:
          'https://d2z6cj5wcte8g7.cloudfront.net/book-covers/Ten_dozen_cupcakes.png',
        price: 15.99,
        id: 'bbih080x-d93b-11e8-9f8b-f2801f1b9fd1',
        name: 'Ten Dozen Cupcakes',
        author: 'Henry Wunsch'
      },
      {
        rating: 5,
        category: 'Cookbooks',
        cover:
          'https://d2z6cj5wcte8g7.cloudfront.net/book-covers/caffe_latte_art.png',
        price: 17.99,
        id: 'iee1rx9x-d93b-11e8-9f8b-f2801f1b9fd1',
        name: 'Caffe Latte Art',
        author: 'Jaylen Anderson'
      },
      {
        rating: 5,
        category: 'Cookbooks',
        cover:
          'https://d2z6cj5wcte8g7.cloudfront.net/book-covers/The_Joys_of_Pancakes.png',
        price: 21.98,
        id: '2vxvmruf-d93b-11e8-9f8b-f2801f1b9fd1',
        name: 'The Joys of Pancakes',
        author: 'Chef Maple'
      },
      {
        rating: 4,
        category: 'Cookbooks',
        cover: 'https://d2z6cj5wcte8g7.cloudfront.net/book-covers/tea.png',
        price: 18.99,
        id: 'sif184ws-d93b-11e8-9f8b-f2801f1b9fd1',
        name: 'Tea',
        author: 'Stuart Hessel'
      },
      {
        rating: 5,
        category: 'Cookbooks',
        cover:
          'https://d2z6cj5wcte8g7.cloudfront.net/book-covers/home_brew_guide.png',
        price: 15.99,
        id: 'yrqzhlal-d93b-11e8-9f8b-f2801f1b9fd1',
        name: 'Home Brew Guide',
        author: 'Jaylen Anderson'
      },
      {
        rating: 5,
        category: 'Cookbooks',
        cover: 'https://d2z6cj5wcte8g7.cloudfront.net/book-covers/spice.png',
        price: 15.98,
        id: '7vfydos1-d93b-11e8-9f8b-f2801f1b9fd1',
        name: 'Spice',
        author: 'Laura Nader'
      },
      {
        rating: 4,
        category: 'Cookbooks',
        cover:
          'https://d2z6cj5wcte8g7.cloudfront.net/book-covers/macaroons.png',
        price: 21.98,
        id: 'rbfjp603-d93b-11e8-9f8b-f2801f1b9fd1',
        name: 'Macaroons',
        author: 'Richard Labadie'
      },
      {
        rating: 3,
        category: 'Cookbooks',
        cover: 'https://d2z6cj5wcte8g7.cloudfront.net/book-covers/fondue.png',
        price: 15.98,
        id: 'cpw3nosp-d93b-11e8-9f8b-f2801f1b9fd1',
        name: 'Fondue',
        author: 'Richard Labadie'
      },
      {
        rating: 2,
        category: 'Cookbooks',
        cover:
          'https://d2z6cj5wcte8g7.cloudfront.net/book-covers/101_burgers.png',
        price: 18.99,
        id: '33s6hqam-d93b-11e8-9f8b-f2801f1b9fd1',
        name: '101 Burgers',
        author: 'Richard Labadie'
      },
      {
        rating: 5,
        category: 'Cookbooks',
        cover:
          'https://d2z6cj5wcte8g7.cloudfront.net/book-covers/Pineapple+dreams.png',
        price: 23.95,
        id: 'g8zuo3j8-d93b-11e8-9f8b-f2801f1b9fd1',
        name: 'Pineapple Dreams',
        author: 'Laura Ray'
      },
      {
        rating: 4,
        category: 'Cookbooks',
        cover:
          'https://d2z6cj5wcte8g7.cloudfront.net/book-covers/butter_%26_flour.png',
        price: 15.98,
        id: '0b7ruzew-d93b-11e8-9f8b-f2801f1b9fd1',
        name: 'Butter & Flour',
        author: 'Stuart Hessel'
      },
      {
        rating: 5,
        category: 'Cookbooks',
        cover:
          'https://d2z6cj5wcte8g7.cloudfront.net/book-covers/SIMPLY_ITALIAN.png',
        price: 19.99,
        id: 'i0bka1vt-d93b-11e8-9f8b-f2801f1b9fd1',
        name: 'Simply Italian',
        author: 'Morissette'
      },
      {
        rating: 5,
        category: 'Cookbooks',
        cover:
          'https://d2z6cj5wcte8g7.cloudfront.net/book-covers/Spaghetti.png',
        price: 20.99,
        id: '084s9grl-d93b-11e8-9f8b-f2801f1b9fd1',
        name: 'Spaghetti',
        author: 'Richard Labadie'
      },
      {
        rating: 4,
        category: 'Cookbooks',
        cover:
          'https://d2z6cj5wcte8g7.cloudfront.net/book-covers/french_press.png',
        price: 18.99,
        id: 'io6l0iyw-d93b-11e8-9f8b-f2801f1b9fd1',
        name: 'French Press',
        author: 'Jaylen Anderson'
      },
      {
        rating: 5,
        category: 'Cookbooks',
        cover:
          'https://d2z6cj5wcte8g7.cloudfront.net/book-covers/say_cheese.png',
        price: 17.96,
        id: 'xt4u83kb-d93b-11e8-9f8b-f2801f1b9fd1',
        name: 'Say Cheese',
        author: 'Richard Labadie'
      },
      {
        rating: 3,
        category: 'Cookbooks',
        cover:
          'https://d2z6cj5wcte8g7.cloudfront.net/book-covers/noodles_everyday.png',
        price: 19.99,
        id: 'f1vs8qjw-d93b-11e8-9f8b-f2801f1b9fd1',
        name: 'Noodles Everyday',
        author: 'Chef Susan'
      },
      {
        rating: 4,
        category: 'Cookbooks',
        cover:
          'https://d2z6cj5wcte8g7.cloudfront.net/book-covers/levantine_hummus.png',
        price: 23.95,
        id: '56ysbni4-d93b-11e8-9f8b-f2801f1b9fd1',
        name: 'Levantine Hummus',
        author: 'Jaylen Anderson'
      },
      {
        rating: 4,
        category: 'Cookbooks',
        cover: 'https://d2z6cj5wcte8g7.cloudfront.net/book-covers/carbs.png',
        price: 21.98,
        id: 'a7zyln40-d93b-11e8-9f8b-f2801f1b9fd1',
        name: 'Carbs',
        author: 'Ellen Kuvalis'
      }
    ]);
  }

  // eslint-disable-next-line @typescript-eslint/no-unused-vars
  public getBook(bookId: string): Observable<Book> {
    // return API.get('books', `/books/${bookId}`, null);
    return of();
  }

  // eslint-disable-next-line @typescript-eslint/no-unused-vars
  public search(query: string): Observable<Book[]> {
    // return API.get('search', `/search?q=${query}`, null);
    return of([]);
  }

  // eslint-disable-next-line max-lines-per-function
  public getFriendRecommendations(): Observable<Recommendation[]> {
    // return API.get('recommendations', '/recommendations', null);
    return of([
      {
        bookId: '2rb37qw5-d93b-11e8-9f8b-f2801f1b9fd1',
        purchases: 4,
        friendsPurchased: [
          'us-east-1:10f71594-5b56-4f82-bd25-6d02685bb44f',
          'us-east-1:15c0fb03-59b4-4cb0-ad1d-9aea9e6659e6',
          'us-east-1:21cc3e5a-da24-460f-896f-7a840faec879',
          'us-east-1:3f9f20ef-bfa6-448d-b08c-5daf30827a58'
        ]
      },
      {
        bookId: 'lk38ejv5-d93b-11e8-9f8b-f2801f1b9fd1',
        purchases: 3,
        friendsPurchased: [
          'us-east-1:0c6536d2-3ecf-42fe-a4fb-11f7aae4e6c3',
          'us-east-1:09048fa7-0587-4963-a17e-593196775c4a',
          'us-east-1:1f9576fb-3578-4666-ba8d-82212ae208ee'
        ]
      },
      {
        bookId: '3sbvndpe-d93b-11e8-9f8b-f2801f1b9fd1',
        purchases: 2,
        friendsPurchased: [
          'us-east-1:15b5db11-add4-486c-ae87-7691bb25e20e',
          'us-east-1:1d84411f-b7c7-494c-b667-bff41cc8cb12'
        ]
      },
      {
        bookId: 'bsx7u3xv-d93b-11e8-9f8b-f2801f1b9fd1',
        purchases: 2,
        friendsPurchased: [
          'us-east-1:1ba477dc-be70-43cc-a9ab-1aa0dcc0a31b',
          'us-east-1:36cbe406-660e-47fe-9a4e-91acdf5c4f1b'
        ]
      },
      {
        bookId: 'io6l0iyw-d93b-11e8-9f8b-f2801f1b9fd1',
        purchases: 2,
        friendsPurchased: [
          'us-east-1:3a7ee004-801e-45f0-9d35-e513f0f6845d',
          'us-east-1:3bbe79f1-3152-4105-b9d4-94c4c9afdaac'
        ]
      }
    ]);
  }

  public getBestSellers(): Observable<string[]> {
    // return API.get('bestsellers', '/bestsellers', null);
    return of([]);
  }

  // eslint-disable-next-line @typescript-eslint/no-unused-vars
  public addToCart(bookId: string, price: number): Observable<void> {
    // const bookInCart = API.get('cart', `/cart/${bookId}`, null);
    // if the book already exists in the cart, increase the quantity
    // if (bookInCart) {
    //   return API.put('cart', '/cart', {
    //     body: {
    //       bookId,
    //       quantity: bookInCart.quantity + 1
    //     }
    //   });
    // }

    // if the book does not exist in the cart, add it

    // return API.post('cart', '/cart', {
    //   body: {
    //     bookId,
    //     price,
    //     quantity: 1
    //   }
    // });
    return of();
  }

  // eslint-disable-next-line @typescript-eslint/no-unused-vars
  public removeFromCart(bookId: string): Observable<void> {
    // return API.del('cart', '/cart', {
    //   body: {
    //     bookId
    //   }
    // });
    return of();
  }

  // eslint-disable-next-line @typescript-eslint/no-unused-vars
  public listOrderInCart(): Observable<Order[]> {
    // return API.get('cart', '/cart', null);
    return of([]);
  }

  // eslint-disable-next-line @typescript-eslint/no-unused-vars
  public checkout(orders: Order[]): Observable<void> {
    // return API.post('orders', '/orders', {
    //   body: {
    //     books: orders
    //   }
    // });
    return of();
  }

  public getPastOrders(): Observable<PastOrder[]> {
    // return API.get('orders', '/orders', null);
    return of([]);
  }
}
