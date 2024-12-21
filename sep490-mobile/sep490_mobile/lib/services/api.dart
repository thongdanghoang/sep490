

import 'package:sep490_mobile/dtos/responses/login_response.dart';

abstract class Api {
  Future<LoginResponse?> checkLogin();
}
