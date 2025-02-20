import 'dart:async';
import 'dart:io';

import 'package:openid_client/openid_client.dart';

import 'package:url_launcher/url_launcher.dart';
import 'package:openid_client/openid_client_io.dart' as io;

// const keycloakUri = 'http://localhost:8081/realms/myrealm';
// const keycloakUri = 'http://localhost:8080';
// const scopes = [
//   'openid',
//   'email',
//   'phone'
// ];
//
// Credential? credential;
// late final Client client;
//
// Future<Client> getClient() async {
//   var uri = Uri.parse(keycloakUri);
//   if (!kIsWeb && Platform.isAndroid) uri = uri.replace(host: '10.0.2.2');
//   var clientId = 'myclient';
//
//   var issuer = await Issuer.discover(uri);
//   return Client(issuer, clientId);
// }

// Future<LoginResponse?> authenticate() async {
//
//   client = await getClient();
//   credential = await getRedirectResult(client, scopes: scopes);
//
//   // create a function to open a browser with an url
//   urlLauncher(String url) async {
//     var uri = Uri.parse(url);
//     if (await canLaunchUrl(uri) || Platform.isAndroid) {
//       await launchUrl(uri);
//     } else {
//       throw 'Could not launch $url';
//     }
//   }
//
//   // create an authenticator
//   var authenticator = io.Authenticator(client,
//       scopes: scopes, port: 8080, urlLancher: urlLauncher);
//
//   // starts the authentication
//   var c = await authenticator.authorize();
//
//   // close the webview when finished
//   if (Platform.isAndroid || Platform.isIOS) {
//     closeInAppWebView();
//   }
//   var token = await c.getTokenResponse();
//   print("token:$token");
//   var userInfo = await c.getUserInfo();
//   print("User Info: ${userInfo.toJson()}");
//   LoginResponse loginResponse = LoginResponse(userInfo: userInfo, token: token);
//   if(loginResponse.userInfo == null || loginResponse.userInfo == null) {
//     return null;
//   }
//   return loginResponse;
// }
Future<Credential> authenticate(Client client,
    {List<String> scopes = const []}) async {

  // create a function to open a browser with an url
  urlLauncher(String url) async {
    var uri = Uri.parse(url);
    if (await canLaunchUrl(uri) || Platform.isAndroid) {
      await launchUrl(uri);
    } else {
      throw 'Could not launch $url';
    }
  }

  // create an authenticator
  var authenticator = io.Authenticator(client,
      scopes: scopes, port: 4200, urlLancher: urlLauncher);

  // starts the authentication
  var c = await authenticator.authorize();

  // close the webview when finished
  if (Platform.isAndroid || Platform.isIOS) {
    closeInAppWebView();
  }

  return c;
}

Future<Credential?> getRedirectResult(Client client,
    {List<String> scopes = const []}) async {

  return null;
}