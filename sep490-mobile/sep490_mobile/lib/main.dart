import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:go_router/go_router.dart';
import 'package:sep490_mobile/pages/login/login.dart';
import 'package:sep490_mobile/repositories/log/log.dart';
import 'package:sep490_mobile/repositories/log/log_impl.dart';
import 'package:sep490_mobile/services/api.dart';
import 'package:sep490_mobile/services/api_impl.dart';
import 'package:sep490_mobile/utils/theme/theme_mode.dart';

import 'main_cubit.dart';

final GoRouter _router = GoRouter(
  //errorBuilder: (context, state) => const ErrorScreen(),
  routes: <RouteBase>[
    GoRoute(
      path: '/',
      builder: (BuildContext context, GoRouterState state) {
        return const Login();
      },
      // routes: <RouteBase>[
      //   GoRoute(
      //     path: AppRoutes.login,
      //     builder: (BuildContext context, GoRouterState state) {
      //       return Login();
      //     },
      //   ),
      //   GoRoute(
      //     path: AppRoutes.register,
      //     builder: (BuildContext context, GoRouterState state) {
      //       return Register();
      //     },
      //   ),
      //   GoRoute(
      //     path: AppRoutes.detailProduct,
      //     builder: (BuildContext context, GoRouterState state) {
      //       int productId = ((state.extra as Map)['productId'] as int?) ?? 0;
      //       return DetailProduct(productId: productId); //receive
      //     },
      //   ),
      //   GoRoute(
      //     path: AppRoutes.orderList,
      //     builder: (BuildContext context, GoRouterState state) {
      //       return OrderList();
      //     },
      //   ),
      //   GoRoute(
      //     path: AppRoutes.confirmOrder,
      //     builder: (BuildContext context, GoRouterState state) {
      //       return ConfirmOrder();
      //     },
      //   ),
      //   GoRoute(
      //     path: AppRoutes.appTab,
      //     builder: (BuildContext context, GoRouterState state) {
      //       return AppTab();
      //     },
      //   ),
      //   GoRoute(
      //     path: AppRoutes.orderDetail,
      //     builder: (BuildContext context, GoRouterState state) {
      //       Order order = (state.extra as Map)['order'] as Order;
      //       return OrderDetail(order: order);
      //     },
      //   ),
      //],
    ),
  ],
);


void main() async {
  Log log = LogImpl();
  WidgetsFlutterBinding.ensureInitialized();
  final themeModeManager = ThemeModeManager();
  await themeModeManager.loadThemeMode(); // Ensure theme is loaded before UI
  runApp(
    MultiRepositoryProvider(
      providers: [
        RepositoryProvider<ThemeModeManager>.value(
          value: themeModeManager,
        ),
        RepositoryProvider<Log>.value(
        value: log,
        )
      ],
      child: const Repository(),
        ),
  );
}

class Repository extends StatelessWidget {
  const Repository({super.key});

  @override
  Widget build(BuildContext context) {
    return RepositoryProvider<Api>(
      // create: (context) => ApiServerImpl(context.read<Log>()),
      create: (context) => ApiImpl(context.read<Log>()),
      child: const Provider(),
    );
  }
}

class Provider extends StatelessWidget {
  const Provider({super.key});

  @override
  Widget build(BuildContext context) {
   // final themeModeManager = context.read<ThemeModeManager>();
    return BlocProvider(
      create: (context) => MainCubit(),
      child: const MyApp(),
    );
  }
}

class MyApp extends StatelessWidget {
  const MyApp({super.key});

  @override
  Widget build(BuildContext context) {


    return SafeArea(
      child: BlocBuilder<MainCubit, MainState>(
        builder: (context, state) {
          if (state.isLoading) {
            return const MaterialApp(
              debugShowCheckedModeBanner: false,
              home: Scaffold(
                body: Center(
                  child: CircularProgressIndicator(), // Loading indicator
                ),
              ),
            );
          }
          final cubit = context.read<MainCubit>();

          return MaterialApp.router(
            locale: Locale(state.locale), // Set current locale from state
            supportedLocales: cubit.localization.supportedLocales,
            localizationsDelegates: cubit.localization.localizationsDelegates,
            darkTheme: ThemeData.dark(),
            theme: ThemeData.light(),
            themeMode: state.isLightTheme ? ThemeMode.light : ThemeMode.dark,
            debugShowCheckedModeBanner: false,
            routerConfig: _router,
          );
        },
      ),
    );
  }
}