import 'dart:async';
import 'dart:io';

import 'package:flutter/foundation.dart';
import 'package:openid_client/openid_client.dart';
import 'package:sep490_mobile/dtos/responses/login_response.dart';
import 'package:url_launcher/url_launcher.dart';
import 'package:openid_client/openid_client_io.dart' as io;

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

Future<LoginResponse?> authenticate() async {

  client = await getClient();
  credential = await getRedirectResult(client, scopes: scopes);

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
      scopes: scopes, port: 4000, urlLancher: urlLauncher);

  // starts the authentication
  var c = await authenticator.authorize().timeout(
        const Duration(minutes: 2),
        onTimeout: () =>
            throw TimeoutException('Authentication timed out after 2 minutes'),
      );

  // close the webview when finished
  if (Platform.isAndroid || Platform.isIOS) {
    closeInAppWebView();
  }
  var token = await c.getTokenResponse();
  var userInfo = await c.getUserInfo();

  LoginResponse loginResponse = LoginResponse(userInfo: userInfo, token: token);
  if(loginResponse.userInfo == null || loginResponse.userInfo == null) {
    return null;
  }
  return loginResponse;

}

Future<Credential?> getRedirectResult(Client client,
    {List<String> scopes = const []}) async {

  return null;
}
