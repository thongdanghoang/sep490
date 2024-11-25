import {Component, OnDestroy, OnInit} from '@angular/core';
import {ThemeService} from './modules/core/services/theme.service';
import {TranslateService} from '@ngx-translate/core';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrl: './app.component.scss'
})
export class AppComponent implements OnInit, OnDestroy {
  private systemThemeMediaQuery?: MediaQueryList;

  constructor(
    private readonly themeService: ThemeService,
    private readonly translate: TranslateService
  ) {
    this.themeService.initTheme();
  }

  ngOnInit(): void {
    this.translate.setDefaultLang('vi');
    // Listen for system theme changes
    this.systemThemeMediaQuery = window.matchMedia(
      this.themeService.SYSTEM_COLOR_SCHEME_QUERY
    );
    this.systemThemeMediaQuery.addEventListener(
      'change',
      this.handleThemeChange
    );
  }

  ngOnDestroy(): void {
    this.systemThemeMediaQuery?.removeEventListener(
      'change',
      this.handleThemeChange
    );
  }

  private readonly handleThemeChange = (e: MediaQueryListEvent): void => {
    this.themeService.systemPreferredColorThemeChanged.next(e.matches);
  };
}
