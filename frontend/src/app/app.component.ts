import {Component} from '@angular/core';
import {ThemeService} from './modules/core/services/theme.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrl: './app.component.scss'
})
export class AppComponent {

  protected isDarkMode: boolean = false;

  constructor(private readonly themeService: ThemeService) {
    // this.translate.setDefaultLang('vi');
  }

  protected toggleLightDark(): void {
    if (this.themeService.isDarkMode()) {
      this.themeService.switchTheme('theme-aura-light-cyan');
      this.isDarkMode = false;
      return;
    }
    this.themeService.switchTheme('theme-aura-dark-cyan');
    this.isDarkMode = true;
  }
}
