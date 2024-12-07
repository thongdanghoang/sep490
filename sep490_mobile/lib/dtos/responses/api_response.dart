import 'package:sep490_mobile/extensions//custom_string.dart';

/// A generic class that represents the standard API response structure.
///
/// The [status] field typically contains values like "success" or "error".
/// The [message] field contains a human-readable message about the response.
/// The [data] field contains the actual response payload.
///
/// Example:
/// ```dart
/// final response = ApiResponse.fromJson({
///   'status': 'success',
///   'message': 'Data retrieved successfully',
///   'data': {'id': 1, 'name': 'John'}
/// });
/// ```
///
/// Throws [FormatException] if required fields are missing or invalid.
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
