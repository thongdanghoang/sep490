import {Component, OnDestroy, OnInit} from '@angular/core';
import {TranslateService} from '@ngx-translate/core';
import {Subject, filter, takeUntil} from 'rxjs';
import {ThemeService} from './modules/core/services/theme.service';
import {UserService} from './services/user.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrl: './app.component.scss'
})
export class AppComponent implements OnInit, OnDestroy {
  protected readonly destroy$: Subject<void> = new Subject<void>();
  private systemThemeMediaQuery?: MediaQueryList;

  constructor(
    private readonly themeService: ThemeService,
    private readonly translate: TranslateService,
    private readonly userService: UserService
  ) {}

  ngOnInit(): void {
    this.userService.userConfigs
      .pipe(
        takeUntil(this.destroy$),
        filter(userConfigs => !!userConfigs)
      )
      .subscribe(userConfigs => {
        this.translate.use(userConfigs.language.split('-')[0]);
        this.themeService.setTheme(userConfigs.theme);
        this.themeService.initTheme();
      });
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
    this.destroy$.next();
    this.destroy$.complete();
    this.systemThemeMediaQuery?.removeEventListener(
      'change',
      this.handleThemeChange
    );
  }

  private readonly handleThemeChange = (e: MediaQueryListEvent): void => {
    this.themeService.systemPreferredColorThemeChanged.next(e.matches);
  };
}
