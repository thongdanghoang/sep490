import 'package:flutter/material.dart';
import '../utils/color/app_colors.dart';

class CustomButton extends StatelessWidget {
  final VoidCallback? onTap;
  final Color textColor;
  final Color backgroundColor;
  final Color borderColor;
  final String text;

  const CustomButton({
    Key? key,
    this.onTap,
    required this.text,
    this.textColor = Colors.white,//defaut value
    this.backgroundColor = AppColors.primaryColor,
    this.borderColor = Colors.blue,
  }) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return InkWell(
      onTap: onTap,
      child: AnimatedContainer(
        duration: Duration(milliseconds: 1000),
        height: 50,
        padding: EdgeInsets.symmetric(vertical: 12, horizontal: 15),
        alignment: Alignment.center,
        child: Text(
          text,
          style: TextStyle(color: textColor, fontSize: 17),
        ),
        decoration: BoxDecoration(
          color: backgroundColor,
          borderRadius: BorderRadius.circular(25), // Border radius of 25
          border: Border.all(
            width: 1, // Border width of 1
            color: borderColor, // Border color
          ),
        ),
      ),
    );
  }
}
