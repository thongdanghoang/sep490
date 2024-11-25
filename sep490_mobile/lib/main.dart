import 'dart:io';
import 'package:flutter/foundation.dart';
import 'package:flutter/material.dart';
import 'package:flutter_localization/flutter_localization.dart';
import 'package:openid_client/openid_client.dart';
import 'package:shared_preferences/shared_preferences.dart';
import 'package:sep490_mobile/utils/l10n/app_localizations.dart';
import 'package:sep490_mobile/utils/openid_io.dart' if (dart.library.html) 'package:sep490_mobile/utils/openid_browser.dart';

const keycloakUri = 'http://localhost:8080/realms/myrealm';
const scopes = ['profile'];

Credential? credential;
late final Client client;

Future<Client> getClient() async {
  var uri = Uri.parse(keycloakUri);
  if (!kIsWeb && Platform.isAndroid) uri = uri.replace(host: '10.0.2.2');
  var clientId = 'myclient';

  var issuer = await Issuer.discover(uri);
  return Client(issuer, clientId);
}

void main() async {
  WidgetsFlutterBinding.ensureInitialized();
  client = await getClient();
  credential = await getRedirectResult(client, scopes: scopes);
  runApp(const MyApp());
}

class MyApp extends StatefulWidget {
  const MyApp({super.key});

  @override
  State<MyApp> createState() => _MyAppState();
}

class _MyAppState extends State<MyApp> {
  final FlutterLocalization _localization = FlutterLocalization.instance;
  final ValueNotifier<ThemeMode> _themeModeNotifier = ValueNotifier(ThemeMode.system);
  bool _isLoadingTheme = true;
  bool _isLoadingLocalizatioin = true;

  @override
  void initState() {
    super.initState();
    _initializeApp();
  }

  Future<void> _initializeApp() async {
    await _initializeLocalization();
    await _loadThemeMode();
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
    setState(() {
      _isLoadingLocalizatioin = false;
    });
  }

  void _onTranslatedLanguage(Locale? locale) {
    setState(() {});
  }

  Future<void> _loadThemeMode() async {
    final prefs = await SharedPreferences.getInstance();
    final themeModeIndex = prefs.getInt('themeMode') ?? ThemeMode.system.index;
    _themeModeNotifier.value = ThemeMode.values[themeModeIndex];
    setState(() {
      _isLoadingTheme = false;
    });
  }

  Future<void> _saveThemeMode(ThemeMode themeMode) async {
    final prefs = await SharedPreferences.getInstance();
    await prefs.setInt('themeMode', themeMode.index);
  }

  @override
  Widget build(BuildContext context) {
    if (_isLoadingTheme || _isLoadingLocalizatioin) {
      return const MaterialApp(
        home: Scaffold(
          body: Center(
            child: CircularProgressIndicator(),
          ),
        ),
      );
    }

    return ValueListenableBuilder<ThemeMode>(
      valueListenable: _themeModeNotifier,
      builder: (context, themeMode, child) {
        return MaterialApp(
          supportedLocales: _localization.supportedLocales,
          localizationsDelegates: _localization.localizationsDelegates,
          theme: ThemeData.light(),
          darkTheme: ThemeData.dark(),
          themeMode: themeMode,
          home: MyHomePage(
            themeModeNotifier: _themeModeNotifier,
            onThemeModeChanged: _saveThemeMode,
          ),
        );
      },
    );
  }
}

class MyHomePage extends StatefulWidget {
  final ValueNotifier<ThemeMode> themeModeNotifier;
  final Future<void> Function(ThemeMode) onThemeModeChanged;

  const MyHomePage({
    super.key,
    required this.themeModeNotifier,
    required this.onThemeModeChanged,
  });

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
              widget.themeModeNotifier.value == ThemeMode.dark
                  ? Icons.dark_mode
                  : Icons.light_mode,
            ),
            onPressed: () async {
              final newMode = widget.themeModeNotifier.value == ThemeMode.dark
                  ? ThemeMode.light
                  : ThemeMode.dark;
              widget.themeModeNotifier.value = newMode;
              await widget.onThemeModeChanged(newMode);
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
                  var credential = await authenticate(client, scopes: scopes);
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
