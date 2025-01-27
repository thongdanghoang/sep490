import 'package:openid_client/openid_client.dart';


abstract class Api {
  Future<Credential> checkLogin(Client client,
      {List<String> scopes = const []});
}
