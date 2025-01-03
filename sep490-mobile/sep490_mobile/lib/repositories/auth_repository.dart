import 'package:shared_preferences/shared_preferences.dart';

class AuthRepository{
  String _phoneNumberKey = 'phoneNumber';
  String _passwordKey = 'password';

  // Save credentials to local storage
  Future<void> saveCredentials({required String phoneNumber, required String password}) async {
    SharedPreferences prefs = await SharedPreferences.getInstance();
    await prefs.setString(_phoneNumberKey, phoneNumber);
    await prefs.setString(_passwordKey, password);
  }

  // Retrieve credentials from local storage
  Future<Map<String, String>> getCredentials() async {
    SharedPreferences prefs = await SharedPreferences.getInstance();
    String? phoneNumber = prefs.getString(_phoneNumberKey);
    String? password = prefs.getString(_passwordKey);
    return {'phoneNumber': phoneNumber ?? '', 'password': password ?? ''};
  }
}
