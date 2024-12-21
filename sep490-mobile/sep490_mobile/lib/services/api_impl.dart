import 'package:sep490_mobile/dtos/responses/login_response.dart';

import '../repositories/log/log.dart';
import '../utils/openid_io.dart';
import 'api.dart';
import 'base_service.dart';

class ApiImpl extends BaseService implements Api {
  Log log;

  ApiImpl(this.log);

  @override
  Future<LoginResponse?> checkLogin() async {
    var loginResponse = await authenticate();
      print(loginResponse.toString());
      return loginResponse;

  }

}
