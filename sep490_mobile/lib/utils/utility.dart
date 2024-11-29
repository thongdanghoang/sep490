import 'dart:io';

import 'package:flutter/material.dart';
import 'package:sep490_mobile/enums/popup_type.dart';
import 'package:sep490_mobile/services/api_constants.dart';
import 'package:sep490_mobile/widgets/info_popup.dart';

class Utility {
  static double screenWidth(BuildContext context) => MediaQuery.of(context).size.width;
  static double screenHeight(BuildContext context) => MediaQuery.of(context).size.height;

  static String get osName {
    if (Platform.isAndroid) {
      return 'android';
    } else if (Platform.isIOS) {
      return 'ios';
    } else if (Platform.isWindows) {
      return 'windows';
    } else if (Platform.isMacOS) {
      return 'macos';
    } else if (Platform.isLinux) {
      return 'linux';
    } else {
      return 'unknown';
    }
  }
  static String getImageUrl(String? imageUrl) {
    if (imageUrl == null || imageUrl.isEmpty) {
      return '${APIConstants.baseUrl}/products/images/notfound.jpeg';
    }

    if (!imageUrl.contains('http')) {
      return '${APIConstants.baseUrl}/products/images/$imageUrl';
    }

    return imageUrl;
  }

  static void alert({
    required BuildContext context,
    required String message,
    required PopupType popupType,
    required Function onOkPressed // action when press ok
  }) {
    showDialog(
      context: context,
      builder: (BuildContext context) {
        return InfoPopup(
          popupType: popupType,
          message: message,
          onOkPressed: onOkPressed,
        );
      },
    );
  }
}
