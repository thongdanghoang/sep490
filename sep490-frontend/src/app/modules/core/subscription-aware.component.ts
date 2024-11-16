import {Directive, OnDestroy} from '@angular/core';
import {Subscription} from 'rxjs';

@Directive()
export abstract class SubscriptionAwareComponent implements OnDestroy {
  private subscriptions: Subscription[] = [];

  ngOnDestroy(): void {
    this.subscriptions.forEach(s => s.unsubscribe());
  }

  protected registerSubscription(subscription: Subscription): void {
    if (subscription) {
      this.subscriptions.push(subscription);
    }
  }

  protected registerSubscriptions(subscriptions: Subscription[]): void {
    if (subscriptions) {
      this.subscriptions = this.subscriptions.concat(subscriptions);
    }
  }

  protected removeAllSubscriptions(): void {
    this.subscriptions.forEach(s => s.unsubscribe());
    this.subscriptions.splice(0, this.subscriptions.length);
  }
}
