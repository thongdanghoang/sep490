import {Directive, OnDestroy} from '@angular/core';
import {Subscription} from 'rxjs';

@Directive()
export abstract class SubscriptionAwareComponent implements OnDestroy {
  private readonly subscriptions: Subscription[] = [];

  ngOnDestroy(): void {
    this.subscriptions.forEach(s => s.unsubscribe());
  }

  protected registerSubscription(subscription: Subscription | null | undefined): void {
    if (subscription) {
      this.subscriptions.push(subscription);
    }
  }

  protected registerSubscriptions(subscriptions: (Subscription | null | undefined)[]): void {
    if (subscriptions) {
      this.subscriptions.push(...subscriptions.filter((s): s is Subscription => !!s));
    }
  }

  protected removeAllSubscriptions(): void {
    this.subscriptions.forEach(s => s.unsubscribe());
    this.subscriptions.splice(0, this.subscriptions.length);
  }
}
