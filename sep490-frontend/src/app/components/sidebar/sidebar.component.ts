import {Component, OnInit} from '@angular/core';
import {Router} from '@angular/router';
import {MenuItem} from 'primeng/api';
import {takeUntil} from 'rxjs';
import {AppRoutingConstants} from '../../app-routing.constant';
import {UserRole} from '../../modules/authorization/enums/role-names';
import {
  ApplicationService,
  UserData
} from '../../modules/core/services/application.service';
import {SubscriptionAwareComponent} from '../../modules/core/subscription-aware.component';

@Component({
  selector: 'app-sidebar',
  templateUrl: './sidebar.component.html',
  styleUrl: './sidebar.component.css'
})
export class SidebarComponent
  extends SubscriptionAwareComponent
  implements OnInit
{
  items: MenuItem[] | undefined;

  settings: MenuItem[] | undefined;

  constructor(
    private readonly applicationService: ApplicationService,
    private readonly router: Router
  ) {
    super();
  }

  ngOnInit(): void {
    this.applicationService.UserData.pipe(takeUntil(this.destroy$)).subscribe(
      (userData: UserData): void => {
        if (
          this.applicationService.includeRole(
            userData.authorities,
            UserRole.ENTERPRISE_OWNER
          )
        ) {
          this.items = this.buildEnterpriseOwnerMenu();
        }
      }
    );
    this.settings = [
      {label: 'Settings', icon: 'pi pi-cog', route: '/settings'}
    ];
  }

  homepage(): void {
    return void this.router.navigate(['/']);
  }

  // eslint-disable-next-line max-lines-per-function
  private buildEnterpriseOwnerMenu(): MenuItem[] {
    return [
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
            route: `/${AppRoutingConstants.AUTH_PATH}/${AppRoutingConstants.USERS_PATH}`
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
  }
}
