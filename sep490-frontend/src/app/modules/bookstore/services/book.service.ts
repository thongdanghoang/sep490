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

  // eslint-disable-next-line @typescript-eslint/no-unused-vars
  public listBook(category: string): Observable<Book[]> {
    // return API.get('books', `/books?category=${category}`, null);
    return of([]);
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

  public getFriendRecommendations(): Observable<Recommendation[]> {
    // return API.get('recommendations', '/recommendations', null);
    return of([]);
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
