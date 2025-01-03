import {Component, OnDestroy, OnInit} from '@angular/core';
import {TranslateService} from '@ngx-translate/core';
import {ThemeService} from './modules/core/services/theme.service';

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

    // this language will be used as a fallback when a translation isn't found in the current language
    this.translate.setDefaultLang('vi');

    // the lang to use, if the lang isn't available, it will use the current loader to get them
    this.translate.use('vi');
  }

  ngOnInit(): void {
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
