# Kardeiro Android

A modern Android layout library with a distinctive visual style called **Kardeiro** —
warm terracotta tones, soft rounded corners, and gentle warm-tinted shadows.

## Project structure

```
kardeiro-android/
├── library/                      # Kardeiro layout library (AAR)
│   └── src/main/java/com/kardeiro/library/
│       ├── theme/                # KardeiroTheme (canonical palette + helpers)
│       ├── util/                 # Density / color blend helpers
│       └── views/                # Custom views:
│           ├── KardeiroCard.kt
│           ├── KardeiroButton.kt
│           ├── KardeiroLayout.kt
│           ├── KardeiroTextField.kt
│           ├── KardeiroBadge.kt
│           └── KardeiroHeader.kt
│
├── app/                          # Demo app that uses the library
│   └── src/main/
│       ├── java/com/kardeiro/demo/MainActivity.kt
│       └── res/layout/activity_main.xml
│
├── .github/workflows/build.yml   # CI: builds debug APK on every push
└── settings.gradle
```

## Kardeiro style — at a glance

| Aspect        | Value                                   |
|---------------|------------------------------------------|
| Primary       | Terracotta `#C2410C`                     |
| Secondary     | Warm Amber `#D97706`                     |
| Accent        | Sand Gold `#F59E0B`                      |
| Background    | Cream `#FFF8F1`                          |
| Surface       | Off-white `#FFFDF8`                      |
| Corner radius | 16dp default, 20dp for large cards       |
| Spacing grid  | 8dp base unit                            |
| Shadow tint   | Warm `#1FC2410C` (terracotta-tinted)     |

## Components

### KardeiroCard
Warm rounded container with soft shadow + 1dp outline.

```xml
<com.kardeiro.library.views.KardeiroCard
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    app:kardeiro_cornerRadius="20dp"
    app:kardeiro_elevation="6dp"
    app:kardeiro_borderWidth="1dp"
    app:kardeiro_padding="20dp"
    app:kardeiro_shadowEnabled="true">
    <!-- children -->
</com.kardeiro.library.views.KardeiroCard>
```

### KardeiroButton
Four styles: `filled`, `tonal`, `outlined`, `text`. Supports icons + loading.

```xml
<com.kardeiro.library.views.KardeiroButton
    android:layout_width="match_parent"
    android:layout_height="52dp"
    android:text="Entrar"
    app:kardeiro_buttonStyle="filled"
    app:kardeiro_buttonRadius="14dp" />
```

### KardeiroLayout
LinearLayout-style container that auto-applies uniform child spacing.

```xml
<com.kardeiro.library.views.KardeiroLayout
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    app:kardeiro_orientation="vertical"
    app:kardeiro_spacing="16dp"
    app:kardeiro_insetStart="16dp"
    app:kardeiro_insetEnd="16dp"
    app:kardeiro_insetTop="16dp"
    app:kardeiro_insetBottom="16dp">
    <!-- children auto-spaced -->
</com.kardeiro.library.views.KardeiroLayout>
```

### KardeiroTextField
Rounded cream-toned input with floating label + helper/error slot.

```xml
<com.kardeiro.library.views.KardeiroTextField
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    app:kardeiro_fieldLabel="E-mail"
    app:kardeiro_fieldHint="voce@exemplo.com"
    app:kardeiro_fieldHelperText="Nunca compartilhamos seu e-mail" />
```

### KardeiroBadge
Small rounded tag for counts / status, three preset sizes.

```xml
<com.kardeiro.library.views.KardeiroBadge
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    app:kardeiro_badgeText="Novo"
    app:kardeiro_badgeSize="medium" />
```

### KardeiroHeader
Top app-bar-style header with the warm Kardeiro gradient background.

```xml
<com.kardeiro.library.views.KardeiroHeader
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    app:kardeiro_header_title="Bem-vindo"
    app:kardeiro_header_subtitle="Estilo Kardeiro" />
```

## Building locally

```bash
./gradlew assembleDebug
```

The APK is generated at `app/build/outputs/apk/debug/app-debug.apk`.

## CI

The GitHub Actions workflow `.github/workflows/build.yml` builds the debug APK
on every push to `main`/`master` and uploads it as an artifact.

## Requirements

- JDK 17
- Android SDK with platform 34 + build-tools 34.0.0
- Gradle 8.2 (handled automatically by the gradle wrapper)
