class CustomException implements Exception {
  final int statusCode;
  final String message;
  static const int INTERNAL_SERVER_ERROR = 500;
  static const int UNAUTHORIZED = 401;

  /// Creates a custom exception with the specified status code and message.
  ///
  /// Both [statusCode] and [message] parameters are required.
  CustomException({required this.statusCode, required this.message});

  /// Returns a string representation of the exception.
  @override
  String toString() {
    return 'MyCustomException: Status Code $statusCode, Message: $message';
  }
}
