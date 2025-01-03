import {Component, OnInit} from '@angular/core';
import {TranslateService} from '@ngx-translate/core';
import {Observable} from 'rxjs';
import {ApplicationService} from '../../modules/core/services/application.service';
import {ThemeService} from '../../modules/core/services/theme.service';
import {SubscriptionAwareComponent} from '../../modules/core/subscription-aware.component';

interface Language {
  display: string;
  mobile: string;
  key: string;
}

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrl: './header.component.scss'
})
export class HeaderComponent
  extends SubscriptionAwareComponent
  implements OnInit
{
  protected readonly authenticated: Observable<boolean>;
  protected languages: Language[] | undefined;
  protected selectedLanguage: Language | undefined;
  protected visible: boolean = false;

  constructor(
    protected readonly applicationService: ApplicationService,
    private readonly themeService: ThemeService,
    private readonly translate: TranslateService
  ) {
    super();
    this.authenticated = this.applicationService.isAuthenticated();
  }

  ngOnInit(): void {
    this.languages = [
      {display: 'Tiếng Việt', mobile: 'VI', key: 'vi'},
      {display: 'English', mobile: 'EN', key: 'en'},
      {display: '中文(简体)', mobile: 'ZH', key: 'zh'}
    ];
    this.selectedLanguage = this.languages[0];
  }

  protected changeLanguage(language: Language): void {
    this.translate.use(language.key);
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
