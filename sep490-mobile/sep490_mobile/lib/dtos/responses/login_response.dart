import 'package:openid_client/openid_client.dart';

class LoginResponse {
  final UserInfo? userInfo;
  final TokenResponse? token;

//<editor-fold desc="Data Methods">
  const LoginResponse({
    this.userInfo,
    this.token,
  });

  @override
  bool operator ==(Object other) =>
      identical(this, other) ||
      (other is LoginResponse &&
          runtimeType == other.runtimeType &&
          userInfo == other.userInfo &&
          token == other.token);

  @override
  int get hashCode => userInfo.hashCode ^ token.hashCode;

  @override
  String toString() {
    return 'LoginResponse{' + ' userInfo: $userInfo,' + ' token: $token,' + '}';
  }

  LoginResponse copyWith({
    UserInfo? userInfo,
    TokenResponse? token,
  }) {
    return LoginResponse(
      userInfo: userInfo ?? this.userInfo,
      token: token ?? this.token,
    );
  }

  Map<String, dynamic> toMap() {
    return {
      'userInfo': this.userInfo,
      'token': this.token,
    };
  }

  factory LoginResponse.fromMap(Map<String, dynamic> map) {
    return LoginResponse(
      userInfo: map['userInfo'] as UserInfo,
      token: map['token'] as TokenResponse,
    );
  }

//</editor-fold>
}