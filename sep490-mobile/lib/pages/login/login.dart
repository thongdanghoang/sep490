import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:flutter_localization/flutter_localization.dart';
import 'package:openid_client/openid_client.dart';
import 'package:sep490_mobile/utils/l18n/app_localizations.dart';
import '../../enums/load_status.dart';
import '../../main.dart';
import '../../main_cubit.dart';
import '../../services/api.dart';
import 'login_cubit.dart';
import 'package:sep490_mobile/utils/openid_io.dart' if (dart.library.js_interop) 'package:sep490_mobile/utils/openid_browser.dart';

class Login extends StatefulWidget {

  const Login({super.key});

  @override
  State<Login> createState() => _LoginState();
}

class _LoginState extends State<Login> {

  @override
  Widget build(BuildContext context) {
    return BlocProvider(
      create: (context) => LoginCubit(context.read<Api>()),
      child: const MyHomePage(),
    );
  }

}


class MyHomePage extends StatefulWidget {

  const MyHomePage({super.key});

  @override
  State<MyHomePage> createState() => _MyHomePageState();
}

class _MyHomePageState extends State<MyHomePage> {
  UserInfo? userInfo;
  @override
  void initState() {
    if (credential != null) {
      credential!.getUserInfo().then((userInfo) {
        setState(() {
          this.userInfo = userInfo;
        });
      });
    }
    super.initState();
  }
  @override
  Widget build(BuildContext context) {
    final cubit = context.read<MainCubit>();
    return BlocBuilder<MainCubit, MainState>(
      builder: (contextMain, stateMain) {
        return Scaffold(
          appBar: AppBar(
            title: Text(AppLocale.homePageTitle.getString(context)),
            actions: [
              // Language Selector Dropdown
              DropdownButton<String>(
                value: cubit.localization.currentLocale?.languageCode,
                onChanged: (String? language) {
                  if (language != null) {
                    cubit.localization.translate(language);
                    cubit.setLocale(language);
                  }
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
                  stateMain.isLightTheme ? Icons.light_mode : Icons.dark_mode,
                ),
                onPressed: () {
                  context.read<MainCubit>().setTheme(!stateMain.isLightTheme);
                },
              ),
            ],
          ),
          body: BlocConsumer<LoginCubit, LoginState>(
            listener: (context, state) {
              // TODO: implement listener
            },
            builder: (context, state) {
              return Center(
                child: Column(
                  mainAxisAlignment: MainAxisAlignment.center,
                  children: <Widget>[
                    if (userInfo != null) ...[
                      Text(
                        '${AppLocale.hello.getString(context)} ${userInfo!
                            .name}',
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
                          // context.read<LoginCubit>().checkLogin();
                          var credential = await context.read<LoginCubit>().checkLogin(client, scopes: scopes);
                          var userInfo = await credential.getUserInfo();
                          setState(() {
                            this.userInfo = userInfo;
                          });
                        },
                      ),
                  ],
                ),
              );
            },
          ),
        );
      },
    );
  }
}