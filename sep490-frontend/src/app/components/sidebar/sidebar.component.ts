import {Component, OnInit} from '@angular/core';
import {MenuItem} from 'primeng/api';

@Component({
  selector: 'app-sidebar',
  templateUrl: './sidebar.component.html',
  styleUrl: './sidebar.component.css'
})
export class SidebarComponent implements OnInit {
  items: MenuItem[] | undefined;

  settings: MenuItem[] | undefined;

  // eslint-disable-next-line max-lines-per-function
  ngOnInit(): void {
    this.items = [
      {
        label: 'Enterprise',
        items: [
          {
            label: 'Dashboard',
            icon: 'pi pi-chart-line',
            route: '/dashboard'
          },
          {
            label: 'Coefficient center',
            icon: 'pi pi-percentage',
            route: '/coefficient-center'
          },
          {
            label: 'Carbon inventory management',
            icon: 'pi pi-building',
            route: '/carbon-inventory-management'
          },
          {
            label: 'Users',
            icon: 'pi pi-users',
            route: '/users'
          }
        ]
      },
      {
        label: 'Customer',
        items: [
          {
            label: 'Subscription',
            icon: 'pi pi-money-bill',
            route: '/subscription'
          },
          {
            label: 'Payment',
            icon: 'pi pi-wallet',
            route: '/payment'
          }
        ]
      }
    ];
    this.settings = [
      {label: 'Settings', icon: 'pi pi-cog', route: '/settings'}
    ];
  }
}
