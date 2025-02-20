import 'package:openid_client/openid_client.dart';
import '../repositories/log/log.dart';
import 'package:sep490_mobile/utils/openid_io.dart' if (dart.library.js_interop) 'package:sep490_mobile/utils/openid_browser.dart';
import 'api.dart';
import 'base_service.dart';

class ApiImpl extends BaseService implements Api {
  Log log;

  ApiImpl(this.log);

  @override
  Future<Credential> checkLogin(Client client,
      {List<String> scopes = const []}) async {
    var loginResponse = await authenticate(client,scopes: scopes);
    print(loginResponse.toString());
    return loginResponse;

  }

}