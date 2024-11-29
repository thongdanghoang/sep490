import 'package:flutter/material.dart';
import 'package:get_it/get_it.dart';
import 'package:go_router/go_router.dart';
import 'package:sep490_mobile/pages/app_routes.dart';
import 'package:sep490_mobile/pages/login/login.dart';

void main() async {
  WidgetsFlutterBinding.ensureInitialized();

  //register services
  // GetIt.instance.registerLazySingleton<UserService>(() => UserService());
  // GetIt.instance.registerLazySingleton<ProductService>(() => ProductService());
  // GetIt.instance.registerLazySingleton<CategoryService>(() => CategoryService());
  // GetIt.instance.registerLazySingleton<CouponService>(() => CouponService());
  // GetIt.instance.registerLazySingleton<OrderService>(() => OrderService());

  runApp(const MyApp());
}


final GoRouter _router = GoRouter(
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



class MyApp extends StatelessWidget {
  const MyApp({super.key});

  // This widget is the root of your application.
  @override
  Widget build(BuildContext context) {
    var theme = ThemeData(
      textTheme: const TextTheme(
          bodySmall: TextStyle(
            fontSize: 14, // Normal text size
            // You can also set other properties like fontFamily, fontWeight, etc.
          ),
          bodyMedium: TextStyle(
            fontSize: 16, // Normal text size
          ),
          bodyLarge: TextStyle(
            fontSize: 20, // Normal text size
            // You can also set other properties like fontFamily, fontWeight, etc.
          )
      ),
    );

    return MaterialApp.router(
        routerConfig: _router,
        theme: theme
    );
  }
}