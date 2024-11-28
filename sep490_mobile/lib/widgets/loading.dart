import 'package:flutter/material.dart';
import '../utils/color/app_colors.dart';
import 'package:loading_animation_widget/loading_animation_widget.dart';

class Loading extends StatelessWidget {
  final double size;
  final Color leftDotColor;
  final Color rightDotColor;

  const Loading({super.key,
    this.size = 100, // Set default value for size
    this.leftDotColor = AppColors.primaryColor, // Set default value for leftDotColor
    this.rightDotColor = Colors.blue, // Set default value for rightDotColor
  });

  @override
  Widget build(BuildContext context) {
    return Center(
      child: LoadingAnimationWidget.twistingDots(
        leftDotColor: leftDotColor,
        rightDotColor: rightDotColor,
        size: size,
      ),
    );
  }
}
