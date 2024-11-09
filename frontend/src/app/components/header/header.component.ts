import {Component} from '@angular/core';
import {ThemeService} from '../../modules/core/services/theme.service';
import {ApplicationService} from '../../modules/core/services/application.service';
import {SubscriptionAwareComponent} from '../../modules/core/subscription-aware.component';
import {Observable} from "rxjs";

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrl: './header.component.scss'
})
export class HeaderComponent extends SubscriptionAwareComponent {

  protected isDarkMode: boolean = false;
  protected readonly authenticated: Observable<boolean>;

  constructor(
    private readonly applicationService: ApplicationService,
    private readonly themeService: ThemeService
  ) {
    super();
    this.authenticated = this.applicationService.isAuthenticated();
    this.applicationService.userDataSubject.subscribe((data) => {
      console.log('userDataSubject', data);
    });
  }

  protected login(): void {
    this.applicationService.login();
  }

  protected logout(): void {
    this.applicationService.logout();
  }

  protected toggleLightDark(): void {
    if (this.themeService.isDarkMode()
    ) {
      this.themeService.switchTheme('theme-aura-light-cyan');
      this.isDarkMode = false;
      return;
    }
    this.themeService.switchTheme('theme-aura-dark-cyan');
    this.isDarkMode = true;
  }
}
