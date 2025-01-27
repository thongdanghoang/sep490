import 'package:bloc/bloc.dart';
import 'package:flutter/material.dart';
import 'package:flutter_localization/flutter_localization.dart';
import 'package:sep490_mobile/repositories/i18n_repository.dart';
import 'package:sep490_mobile/utils/l18n/app_localizations.dart';
import 'package:sep490_mobile/utils/theme/theme_mode.dart';

import 'enums/drawer_item.dart';


part 'main_state.dart';

class MainCubit extends Cubit<MainState> {
  final ThemeModeManager _themeModeManager = ThemeModeManager();
  final LanguageManager _languageManager = LanguageManager();
  final FlutterLocalization _localization = FlutterLocalization.instance;



  MainCubit() : super(MainState.init()) {
    _initialize();
  }

  FlutterLocalization get localization => _localization;
  ThemeModeManager get themeModeManager => _themeModeManager;

  Future<void> _initialize() async {
    await Future.wait([
      _initializeTheme(),
      _initializeLocalization(),
    ]);
    emit(state.copyWith(isLoading: false));
  }


  Future<void> _initializeLocalization() async {
    try {
      final enTranslations = await AppLocale.loadTranslations(AppLocale.english);

      final viTranslations = await AppLocale.loadTranslations(AppLocale.vietnamese);

      if (enTranslations.isEmpty || viTranslations.isEmpty) {
        throw Exception("Translations cannot be empty. Verify source files.");
      }

      _localization.init(
        mapLocales: [
          MapLocale(AppLocale.english, enTranslations,countryCode: 'US'),
          MapLocale(AppLocale.vietnamese, viTranslations,countryCode: 'VN'),
        ],
        initLanguageCode: AppLocale.defaultLocale,
      );

      if (!_localization.supportedLocales.any(
              (locale) => locale.languageCode == AppLocale.defaultLocale)) {
        throw Exception("Default locale ${AppLocale.defaultLocale} is not supported.");
      }

       _localization.onTranslatedLanguage = (Locale? locale) {
        if (locale != null) {
          setLocale(locale.languageCode);
        }
      };

    } catch (e) {
      debugPrint("Error initializing localization: $e");
    }
  }

  Future<void> _initializeTheme() async {
    await _themeModeManager.loadThemeMode();
    setTheme(_themeModeManager.currentThemeMode == ThemeMode.light);
  }

  void setTheme(bool isLightTheme) {
    emit(state.copyWith(isLightTheme: isLightTheme));
    _themeModeManager.saveThemeMode(
        isLightTheme ? ThemeMode.light : ThemeMode.dark);
  }

  void setSelected(DrawerItem selected) {
    emit(state.copyWith(selected: selected));
  }
  Future<void> _loadLocale() async {
    final get = _languageManager.loadLanguage();
    String locale = get.toString();
    emit(state.copyWith(locale: locale));
    localization.translate(locale);
  }

  void setLocale(String locale) async {
    emit(state.copyWith(locale: locale));
    localization.translate(locale);
    await _languageManager.saveLanguage(locale);
  }
}
