export interface Order {
  bookId: string;
  customerId: string;
  price: number;
  quantity: number;
}

export interface PastOrder {
  books: Order[];
  customerId: string;
  orderDate: number;
  orderId: string;
}
