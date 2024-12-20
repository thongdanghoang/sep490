import {Component} from '@angular/core';
import {Observable} from 'rxjs';
import {ApplicationService} from '../../modules/core/services/application.service';
import {ThemeService} from '../../modules/core/services/theme.service';
import {SubscriptionAwareComponent} from '../../modules/core/subscription-aware.component';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrl: './header.component.scss'
})
export class HeaderComponent extends SubscriptionAwareComponent {
  protected readonly authenticated: Observable<boolean>;

  constructor(
    private readonly applicationService: ApplicationService,
    private readonly themeService: ThemeService
  ) {
    super();
    this.authenticated = this.applicationService.isAuthenticated();
  }

  protected login(): void {
    this.applicationService.login();
  }

  protected logout(): void {
    this.applicationService.logout();
  }

  protected get isDarkMode(): Observable<boolean> {
    return this.themeService.isDarkMode();
  }

  protected toggleLightDark(): void {
    this.themeService.toggleLightDark();
  }
}
