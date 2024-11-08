import {Component} from '@angular/core';
import {ThemeService} from '../../modules/core/services/theme.service';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrl: './header.component.scss'
})
export class HeaderComponent {
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
