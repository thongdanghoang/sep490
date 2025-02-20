import 'package:openid_client/openid_client.dart';

class LoginResponse {
  final Credential credential;

//<editor-fold desc="Data Methods">
  const LoginResponse({
    required this.credential,
  });

  @override
  bool operator ==(Object other) =>
      identical(this, other) ||
          (other is LoginResponse &&
              runtimeType == other.runtimeType &&
              credential == other.credential);

  @override
  int get hashCode => credential.hashCode;

  @override
  String toString() {
    return 'LoginResponse{' + ' credential: $credential,' + '}';
  }

  LoginResponse copyWith({
    Credential? credential,
  }) {
    return LoginResponse(
      credential: credential ?? this.credential,
    );
  }

  Map<String, dynamic> toMap() {
    return {
      'credential': this.credential,
    };
  }

  factory LoginResponse.fromMap(Map<String, dynamic> map) {
    return LoginResponse(
      credential: map['credential'] as Credential,
    );
  }

//</editor-fold>
}