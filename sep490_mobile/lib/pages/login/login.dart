import 'package:flutter/material.dart';
import 'package:flutter_localization/flutter_localization.dart';
import 'package:openid_client/openid_client.dart';
import 'package:sep490_mobile/utils/theme/theme_mode.dart';
import 'package:sep490_mobile/utils/l10n/app_localizations.dart';
import 'package:sep490_mobile/utils/openid_io.dart' if (dart.library.html) 'package:sep490_mobile/utils/openid_browser.dart';


class Login extends StatefulWidget {

   const Login({super.key});

  @override
  State<Login> createState() => _LoginState();
}

class _LoginState extends State<Login> {
  final FlutterLocalization _localization = FlutterLocalization.instance;
  final themeModeManager = ThemeModeManager();

  bool _isLoading = true;

  @override
  void initState() {
    super.initState();
    _initializeApp(); // Call the asynchronous initialization method without awaiting it.
    themeModeManager.loadThemeMode(); // You can safely call this method here.
  }

  Future<void> _initializeApp() async {
    await _initializeLocalization();
    setState(() {
      _isLoading = false; // Update the UI when the asynchronous initialization is complete.
    });
  }

  Future<void> _initializeLocalization() async {
    final enTranslations = await AppLocale.loadTranslations(AppLocale.english);
    final viTranslations = await AppLocale.loadTranslations(AppLocale.vietnamese);

    _localization.init(
      mapLocales: [
        MapLocale(AppLocale.english, enTranslations),
        MapLocale(AppLocale.vietnamese, viTranslations),
      ],
      initLanguageCode: AppLocale.defaultLocale,
    );
    _localization.onTranslatedLanguage = _onTranslatedLanguage;
  }

  void _onTranslatedLanguage(Locale? locale) {
    setState(() {});
  }

  @override
  Widget build(BuildContext context) {
    if (_isLoading) {
      return const MaterialApp(
        home: Scaffold(
          body: Center(
            child: CircularProgressIndicator(),
          ),
        ),
      );
    }

    return ValueListenableBuilder<ThemeMode>(
      valueListenable: themeModeManager.themeModeNotifier,
      builder: (context, themeMode, child) {
        return MaterialApp(
          supportedLocales: _localization.supportedLocales,
          localizationsDelegates: _localization.localizationsDelegates,
          theme: ThemeData.light(),
          darkTheme: ThemeData.dark(),
          themeMode: themeMode,
          home: MyHomePage(themeModeManager: themeModeManager),
        );
      },
    );
  }
}


class MyHomePage extends StatefulWidget {
  final ThemeModeManager themeModeManager;

  const MyHomePage({super.key, required this.themeModeManager});

  @override
  State<MyHomePage> createState() => _MyHomePageState();
}

class _MyHomePageState extends State<MyHomePage> {
  UserInfo? userInfo;

  @override
  Widget build(BuildContext context) {
    final localization = FlutterLocalization.instance;
    return Scaffold(
      appBar: AppBar(
        title: Text(AppLocale.homePageTitle.getString(context)),
        actions: [
          // Language Selector Dropdown
          DropdownButton<String>(
            value: localization.currentLocale?.languageCode,
            onChanged: (String? language) {
              if (language != null) localization.translate(language);
            },
            items: AppLocale.supportedLanguageCodes.map((String code) {
              return DropdownMenuItem(
                value: code,
                child: Text(AppLocale.getLanguageName(code)),
              );
            }).toList(),
          ),
          // Light/Dark Mode Toggle Button
          IconButton(
            icon: Icon(
              widget.themeModeManager.currentThemeMode == ThemeMode.dark
                  ? Icons.dark_mode
                  : Icons.light_mode,
            ),
            onPressed: () async {
              final newMode = widget.themeModeManager.currentThemeMode == ThemeMode.dark
                  ? ThemeMode.light
                  : ThemeMode.dark;
              await widget.themeModeManager.saveThemeMode(newMode);
            },
          ),
        ],
      ),
      body: Center(
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: <Widget>[
            if (userInfo != null) ...[
              Text(
                '${AppLocale.hello.getString(context)} ${userInfo!.name}',
              ),
              Text(
                userInfo!.email ?? AppLocale.email.getString(context),
              ),
              OutlinedButton(
                child: Text(AppLocale.logout.getString(context)),
                onPressed: () {
                  setState(() {
                    userInfo = null;
                  });
                },
              ),
            ],
            if (userInfo == null)
              OutlinedButton(
                child: Text(AppLocale.login.getString(context)),
                onPressed: () async {
                  var credential = await authenticate();
                  var userInfo = await credential.getUserInfo();
                  setState(() {
                    this.userInfo = userInfo;
                  });
                },
              ),
          ],
        ),
      ),
    );
  }
}