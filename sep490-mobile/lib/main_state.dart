part of 'main_cubit.dart';

class MainState {
  final bool isLightTheme;
  final DrawerItem selected;
  final String locale;
  final bool isLoading; // New field to track loading status

  const MainState.init({
    this.isLightTheme = true,
    this.selected = DrawerItem.Home,
    this.locale = AppLocale.defaultLocale,
    this.isLoading = true, // Default to true (loading state)
  });

//<editor-fold desc="Data Methods">
  const MainState({
    required this.isLightTheme,
    required this.selected,
    required this.locale,
    required this.isLoading,
  });

  @override
  bool operator ==(Object other) =>
      identical(this, other) ||
          (other is MainState &&
              runtimeType == other.runtimeType &&
              isLightTheme == other.isLightTheme &&
              selected == other.selected &&
              locale == other.locale &&
              isLoading == other.isLoading);

  @override
  int get hashCode =>
      isLightTheme.hashCode ^
      selected.hashCode ^
      locale.hashCode ^
      isLoading.hashCode;

  @override
  String toString() {
    return 'MainState{' +
        ' isLightTheme: $isLightTheme,' +
        ' selected: $selected,' +
        ' locale: $locale,' +
        ' isLoading: $isLoading,' +
        '}';
  }

  MainState copyWith({
    bool? isLightTheme,
    DrawerItem? selected,
    String? locale,
    bool? isLoading,
  }) {
    return MainState(
      isLightTheme: isLightTheme ?? this.isLightTheme,
      selected: selected ?? this.selected,
      locale: locale ?? this.locale,
      isLoading: isLoading ?? this.isLoading,
    );
  }

  Map<String, dynamic> toMap() {
    return {
      'isLightTheme': this.isLightTheme,
      'selected': this.selected,
      'locale': this.locale,
      'isLoading': this.isLoading,
    };
  }

  factory MainState.fromMap(Map<String, dynamic> map) {
    return MainState(
      isLightTheme: map['isLightTheme'] as bool,
      selected: map['selected'] as DrawerItem,
      locale: map['locale'] as String,
      isLoading: map['isLoading'] as bool,
    );
  }

//</editor-fold>
}