import 'package:shared_preferences/shared_preferences.dart';

class LanguageManager {
  static const _languageKey = 'selectedLanguage';

  Future<void> saveLanguage(String languageCode) async {
    final prefs = await SharedPreferences.getInstance();
    await prefs.setString(_languageKey, languageCode);
  }

  Future<String> loadLanguage() async {
    final prefs = await SharedPreferences.getInstance();
    return prefs.getString(_languageKey) ?? 'en';

  }
}