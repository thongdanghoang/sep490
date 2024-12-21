import 'package:shared_preferences/shared_preferences.dart';

class TokenRepository {
  static const String _tokenKey = 'token';
  static const String _refreshTokenKey = 'refreshToken';
  static const String _userIdKey = 'userId';

  // Save tokens to local storage
  Future<void> saveTokens({
    required String token,
    required String refreshToken,
    required int userId
  }) async {
    SharedPreferences prefs = await SharedPreferences.getInstance();
    await prefs.setString(_tokenKey, token);
    await prefs.setString(_refreshTokenKey, refreshToken);
    await prefs.setInt(_userIdKey, userId);
  }

  // Retrieve tokens from local storage
  Future<Map<String, String>> getTokens() async {
    SharedPreferences prefs = await SharedPreferences.getInstance();
    String? token = prefs.getString(_tokenKey) ?? '';
    String? refreshToken = prefs.getString(_refreshTokenKey) ?? '';
    int? userId = prefs.getInt(_userIdKey);
    return {
      _tokenKey: token,
      _refreshTokenKey: refreshToken,
      _userIdKey: userId.toString()
    };
  }
  Future<String> getJwtToken() async {
    Map<String, String> tokens = await getTokens();
    return tokens['token'] ?? '';
  }
  // Clear tokens from local storage
  Future<void> clearTokens() async {
    SharedPreferences prefs = await SharedPreferences.getInstance();
    await prefs.remove(_tokenKey);
    await prefs.remove(_refreshTokenKey);
    await prefs.remove(_userIdKey);
  }
  Future<int?> getLoginUserId() async {
    Map<String, dynamic> tokens = await getTokens();
    return int.parse(tokens['userId']);
  }
}