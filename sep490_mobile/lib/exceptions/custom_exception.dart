class CustomException implements Exception {
  final int statusCode;
  final String message;
  static const int INTERNAL_SERVER_ERROR = 500;
  static const int UNAUTHORIZED = 401;
  CustomException({
    required this.statusCode,
    required this.message
  });

  @override
  String toString() {
    return 'MyCustomException: Status Code $statusCode, Message: $message';
  }
}
