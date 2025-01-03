import 'package:sep490_mobile/repositories/log/log.dart';

class LogImpl implements Log {
  bool isDebug = true;

  @override
  void d(String tag, String content) {
    if (isDebug) print("[$tag] $content");
  }

  @override
  void e(String tag, String content) {
    print("[Error][$tag] $content");
  }

  @override
  void i(String tag, String content) {
    print("[$tag] $content");
  }
}
