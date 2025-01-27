part of 'login_cubit.dart';

class LoginState {
  final LoadStatus loadStatus;
  final Credential? loginResponse;

  const LoginState.init({
    this.loadStatus = LoadStatus.Init,
    this.loginResponse
  });

//<editor-fold desc="Data Methods">
  const LoginState({
    required this.loadStatus,
    this.loginResponse,
  });

  @override
  bool operator ==(Object other) =>
      identical(this, other) ||
      (other is LoginState &&
          runtimeType == other.runtimeType &&
          loadStatus == other.loadStatus &&
          loginResponse == other.loginResponse);

  @override
  int get hashCode => loadStatus.hashCode ^ loginResponse.hashCode;

  @override
  String toString() {
    return 'LoginState{' +
        ' loadStatus: $loadStatus,' +
        ' loginResponse: $loginResponse,' +
        '}';
  }

  LoginState copyWith({
    LoadStatus? loadStatus,
    Credential? loginResponse,
  }) {
    return LoginState(
      loadStatus: loadStatus ?? this.loadStatus,
      loginResponse: loginResponse ?? this.loginResponse,
    );
  }

  Map<String, dynamic> toMap() {
    return {
      'loadStatus': this.loadStatus,
      'loginResponse': this.loginResponse,
    };
  }

  factory LoginState.fromMap(Map<String, dynamic> map) {
    return LoginState(
      loadStatus: map['loadStatus'] as LoadStatus,
      loginResponse: map['loginResponse'] as Credential,
    );
  }

//</editor-fold>
}