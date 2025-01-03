import {Component, OnInit} from '@angular/core';
import {MenuItem} from 'primeng/api';
import {AppRoutingConstants} from '../../app-routing.constant';

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
            route: `/${AppRoutingConstants.DASHBOARD_PATH}`
          },
          {
            label: 'Coefficient center',
            icon: 'pi pi-percentage',
            route: `/${AppRoutingConstants.DASHBOARD_PATH}`
          },
          {
            label: 'Carbon inventory management',
            icon: 'pi pi-building',
            route: `/${AppRoutingConstants.ENTERPRISE_PATH}`
          },
          {
            label: 'Users',
            icon: 'pi pi-users',
            route: `/${AppRoutingConstants.DASHBOARD_PATH}`
          }
        ]
      },
      {
        label: 'Customer',
        items: [
          {
            label: 'Subscription',
            icon: 'pi pi-money-bill',
            route: `/${AppRoutingConstants.DASHBOARD_PATH}`
          },
          {
            label: 'Payment',
            icon: 'pi pi-wallet',
            route: `/${AppRoutingConstants.DASHBOARD_PATH}`
          }
        ]
      }
    ];
    this.settings = [
      {label: 'Settings', icon: 'pi pi-cog', route: '/settings'}
    ];
  }
}
