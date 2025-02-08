import {Component, Input} from '@angular/core';
import {animate, style, transition, trigger} from '@angular/animations';

@Component({
  selector: 'app-payment-status',
  templateUrl: './payment-status.component.html',
  styleUrl: './payment-status.component.css',
  animations: [
    trigger('fadeIn', [
      transition(':enter', [
        style({opacity: 0, transform: 'translateY(-20px)'}),
        animate(
          '0.5s ease-out',
          style({opacity: 1, transform: 'translateY(0)'})
        )
      ])
    ])
  ]
})
export class PaymentStatusComponent {
  @Input() status: 'success' | 'failed' = 'success'; // Input for dynamic status
}
