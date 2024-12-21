class APIConstants {
  static const String serverName = 'Nguyens-Mac-mini.local';
  //static const String serverName = 'Nguyens-iMac';

  //static const String serverName = '192.168.1.208';//imac
  static const int port = 8088;
  static const String basePath = 'api/v1';
  //remember NSAppTransportSecurity on ios
  static String get baseUrl {
    return 'http://$serverName:$port/$basePath';
  }
}
