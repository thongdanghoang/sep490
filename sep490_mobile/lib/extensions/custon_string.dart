import 'dart:convert' as convert;
extension CustomStringExtensions on String {
  String toUtf8() {
    return convert.utf8.decode(this.codeUnits);
  }
}
