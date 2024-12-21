import 'dart:convert' as convert;

extension CustomStringExtensions on String {
  /// Converts the string to UTF-8 encoding.
  ///
  /// This is particularly useful when handling internationalized text that may
  /// contain non-ASCII characters. Returns the UTF-8 encoded string, preserving
  /// special characters and ensuring proper text display.
  ///
  /// Example:
  /// ```dart
  /// final text = "Hello 世界";
  /// final utf8Text = text.toUtf8();
  /// ```
  String toUtf8() {
    if (this.isEmpty) return '';
    try {
      // First check if the string is already valid UTF-8
      if (convert.utf8.encode(this) == this.codeUnits) {
        return this;
      }
      return convert.utf8.decode(this.codeUnits, allowMalformed: false);
    } catch (e) {
      // Fallback to lenient decoding if strict decoding fails
      return convert.utf8.decode(this.codeUnits, allowMalformed: true);
    }
  }
}
