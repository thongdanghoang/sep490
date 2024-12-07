class Validations {
  static bool isValidEmail(String email) {
    // Regular expression for validating email addresses
    final RegExp emailRegex = RegExp(
        r'^[\w-\.]+@([\w-]+\.)+[\w-]{2,4}$',
        caseSensitive: false,
        multiLine: false);

    return emailRegex.hasMatch(email);
  }

  static bool isValidPhoneNumber(String phoneNumber) {
    final phoneRegex = RegExp(
      r"^(?:[+0]9)?[0-9]{10}$",
    );
    return phoneRegex.hasMatch(phoneNumber);
  }
}
