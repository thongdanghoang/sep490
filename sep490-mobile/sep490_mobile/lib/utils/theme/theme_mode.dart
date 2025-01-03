import 'package:flutter/material.dart';
import 'package:shared_preferences/shared_preferences.dart';

class ThemeModeManager {
  final ValueNotifier<ThemeMode> _themeModeNotifier = ValueNotifier(ThemeMode.system);

  ThemeMode get currentThemeMode => _themeModeNotifier.value;
  ValueNotifier<ThemeMode> get themeModeNotifier => _themeModeNotifier;

  /// Loads the saved theme mode from `SharedPreferences`.
  Future<void> loadThemeMode() async {
    final prefs = await SharedPreferences.getInstance();
    final themeModeIndex = prefs.getInt('themeMode') ?? ThemeMode.system.index;
    _themeModeNotifier.value = ThemeMode.values[themeModeIndex];
  }

  /// Saves the current theme mode to `SharedPreferences`.
  Future<void> saveThemeMode(ThemeMode themeMode) async {
    final prefs = await SharedPreferences.getInstance();
    await prefs.setInt('themeMode', themeMode.index);
    _themeModeNotifier.value = themeMode;
  }
}