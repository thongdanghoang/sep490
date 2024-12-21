import 'package:bloc/bloc.dart';
import 'package:meta/meta.dart';

import '../../dtos/responses/login_response.dart';
import '../../enums/load_status.dart';
import '../../services/api.dart';
import 'login.dart';


part 'login_state.dart';

class LoginCubit extends Cubit<LoginState> {
  final Api api;

  LoginCubit(this.api) : super(LoginState.init());

  Future<void> checkLogin() async {
    emit(state.copyWith(loadStatus: LoadStatus.Loading));
    var result = await api.checkLogin();
    if (result != null) {
      emit(state.copyWith(loginResponse: result, loadStatus: LoadStatus.Done));
    } else {
      emit(state.copyWith(loadStatus: LoadStatus.Error));
    }
  }
}
