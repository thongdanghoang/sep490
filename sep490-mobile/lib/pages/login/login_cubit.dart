import 'package:bloc/bloc.dart';
import 'package:meta/meta.dart';
import 'package:openid_client/openid_client.dart';

import '../../dtos/responses/login_response.dart';
import '../../enums/load_status.dart';
import '../../services/api.dart';
import 'login.dart';


part 'login_state.dart';

class LoginCubit extends Cubit<LoginState> {
  final Api api;

  LoginCubit(this.api) : super(LoginState.init());

  Future<Credential> checkLogin(Client client,
      {List<String> scopes = const []}) async {
    emit(state.copyWith(loadStatus: LoadStatus.Loading));
    var result = await api.checkLogin(client,scopes: scopes);
    if (result.response!.isNotEmpty) {
      emit(state.copyWith(loginResponse: result, loadStatus: LoadStatus.Done));
    } else {
      emit(state.copyWith(loadStatus: LoadStatus.Error));
    }
    return result;
  }
}
