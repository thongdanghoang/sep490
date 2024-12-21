import 'package:flutter/material.dart';
import '../utils/color/app_colors.dart';
import 'package:loading_animation_widget/loading_animation_widget.dart';

class Loading extends StatelessWidget {
  final double size;
  final Color leftDotColor;
  final Color rightDotColor;

  /// A loading animation widget that displays twisting dots.
  ///
  /// Parameters:
  /// - [size]: The size of the loading animation (default: 50)
  /// - [leftDotColor]: Color of the left dot (default: AppColors.primaryColor)
  /// - [rightDotColor]: Color of the right dot (default: Colors.blue)

  const Loading({
    super.key,
    this.size = 100, // Set default value for size
    this.leftDotColor =
        AppColors.primaryColor, // Set default value for leftDotColor
    this.rightDotColor = Colors.blue, // Set default value for rightDotColor
  });

  @override
  Widget build(BuildContext context) {
    assert(size > 0, 'Size must be positive');
    return Center(
      child: LoadingAnimationWidget.twistingDots(
        leftDotColor: leftDotColor,
        rightDotColor: rightDotColor,
        size: size,
      ),
    );
  }
}
