import 'dart:convert';
import 'package:flutter/services.dart';

class AppLocale {
  // Localization keys
  static const String homePageTitle = 'homePageTitle';
  static const String hello = 'hello';
  static const String email = 'email';
  static const String login = 'login';
  static const String logout = 'logout';

  // Supported locales
  static const String english = 'en';
  static const String vietnamese = 'vi';

  // Default locale
  static const String defaultLocale = english;

  // Get supported language codes
  static List<String> get supportedLanguageCodes => [
    english,
    vietnamese,
  ];

  // Get language name from code
  static String getLanguageName(String code) {
    switch (code) {
      case english:
        return 'English';
      case vietnamese:
        return 'Tiếng Việt';
      default:
        return code;
    }
  }

  // Load translations from JSON files
  static Future<Map<String, dynamic>> loadTranslations(String languageCode) async {
    String jsonString = await rootBundle.loadString('assets/i18n/$languageCode.json');
    return json.decode(jsonString);
  }
}