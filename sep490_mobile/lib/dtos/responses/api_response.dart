import 'package:sep490_mobile/extensions//custon_string.dart';
class ApiResponse {
  final String message;
  final String status;
  final dynamic data;

  ApiResponse({
    required this.message,
    required this.status,
    required this.data,
  });

  factory ApiResponse.fromJson(Map<String, dynamic> json) {
    return ApiResponse(
      message: (json['message'] as String?)?.toUtf8() ?? '',
      status: json['status'] ?? '',
      data: json['data'] ?? {},
    );
  }
}

