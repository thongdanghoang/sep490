import 'package:flutter/material.dart';
import 'package:sep490_mobile/enums/popup_type.dart';
import '../utils/color/app_colors.dart';




class InfoPopup extends StatelessWidget {
  final PopupType popupType;
  final String message;
  final Function onOkPressed;

  InfoPopup({
    required this.popupType,
    required this.message,
    required this.onOkPressed,
  });

  @override
  Widget build(BuildContext context) {
    String title;
    IconData icon;
    Color iconColor;
    Color buttonColor;

    switch (popupType) {
      case PopupType.success:
        title = 'Success';
        icon = Icons.check_circle;
        iconColor = Colors.green;
        buttonColor = AppColors.primaryColor;
        break;
      case PopupType.failure:
        title = 'Failure';
        icon = Icons.error;
        iconColor = Colors.red;
        buttonColor = Colors.red;
        break;
      case PopupType.info:
        title = 'Information';
        icon = Icons.info;
        iconColor = Colors.blue;
        buttonColor = Colors.blue;
        break;
    }

    return AlertDialog(
      title: Text(title),
      content: Column(
        mainAxisSize: MainAxisSize.min,
        children: [
          Icon(
            icon,
            color: iconColor,
            size: 50,
          ),
          SizedBox(height: 16),
          Text(message),
        ],
      ),
      actions: [
        Container(
          padding: EdgeInsets.symmetric(vertical: 8.0, horizontal: 16.0),
          child: Row(
            mainAxisAlignment: MainAxisAlignment.center,
            children: [
              InkWell(
                onTap: () {
                  Navigator.of(context).pop(); // Đóng popup
                  onOkPressed(); // Xử lý khi bấm nút OK
                },
                child: Container(
                  padding: EdgeInsets.symmetric(vertical: 12.0, horizontal: 32.0),
                  decoration: BoxDecoration(
                    color: buttonColor,
                    borderRadius: BorderRadius.circular(8.0),
                  ),
                  child: Text(
                    'OK',
                    style: TextStyle(color: Colors.white),
                  ),
                ),
              ),
            ],
          ),
        ),
      ],
    );
  }
}
