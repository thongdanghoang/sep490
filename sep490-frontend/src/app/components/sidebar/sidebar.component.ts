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
        label: 'enterprise.title',
        items: [
          {
            label: 'Dashboard',
            icon: 'pi pi-chart-line',
            route: `/${AppRoutingConstants.DASHBOARD_PATH}`
          },
          {
            label: 'Coefficient center',
            icon: 'pi pi-percentage',
            route: `/${AppRoutingConstants.EMISSIONS_PATH}`
          },
          {
            label: 'Building',
            icon: 'pi pi-building',
            route: `/${AppRoutingConstants.ENTERPRISE_PATH}/${AppRoutingConstants.BUILDING_PATH}`
          }
        ]
      },
      {
        label: 'Manage',
        items: [
          {
            label: 'Users',
            icon: 'pi pi-users',
            route: `/${AppRoutingConstants.ENTERPRISE_PATH}/${AppRoutingConstants.USERS_PATH}`
          },
          {
            label: 'Subscription',
            icon: 'pi pi-money-bill',
            route: `/${AppRoutingConstants.ENTERPRISE_PATH}/${AppRoutingConstants.PLAN_PATH}`
          },
          {
            label: 'Payment',
            icon: 'pi pi-wallet',
            route: `/${AppRoutingConstants.ENTERPRISE_PATH}/${AppRoutingConstants.PAYMENT_PATH}`
          }
        ]
      }
    ];
    this.settings = [
      {label: 'Settings', icon: 'pi pi-cog', route: '/settings'}
    ];
  }
}
