package com.rzmmzdh.toro.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDirection
import androidx.compose.ui.unit.sp

@Immutable
data class Style(
    val noteCardTitle: TextStyle = TextStyle(
        fontFamily = vazirFontFamily,
        fontSize = 18.sp,
        fontWeight = FontWeight.Medium,
        textDirection = TextDirection.ContentOrRtl,
        textAlign = TextAlign.Center
    ),
    val noteCardBody: TextStyle = TextStyle(
        fontFamily = vazirFontFamily,
        fontSize = 14.sp,
        fontWeight = FontWeight.Normal,
        textDirection = TextDirection.ContentOrRtl,
        textAlign = TextAlign.Center
    ),
    val topBarTitle: TextStyle = TextStyle(fontFamily = vazirFontFamily,
        fontSize = 24.sp,
        fontWeight = FontWeight.Bold,
        textDirection = TextDirection.ContentOrRtl,
        textAlign = TextAlign.Center),
    val searchableTopBarText: TextStyle = TextStyle(fontFamily = vazirFontFamily,
        fontSize = 24.sp,
        fontWeight = FontWeight.Bold,
        textDirection = TextDirection.ContentOrRtl,
        textAlign = TextAlign.Center),
    val errorBoxTitle: TextStyle = TextStyle(
        fontFamily = vazirFontFamily,
        textDirection = TextDirection.ContentOrRtl,
        fontSize = 20.sp,
        fontWeight = FontWeight.Normal
    ),
    val errorBoxButton: TextStyle = TextStyle(
        fontFamily = vazirFontFamily,
        textDirection = TextDirection.ContentOrRtl,
        fontSize = 16.sp,
        fontWeight = FontWeight.Normal
    ),
    val noteTitleInputValue: TextStyle = TextStyle(
        fontFamily = vazirFontFamily,
        fontSize = 18.sp,
        fontWeight = FontWeight.Medium,
        textDirection = TextDirection.ContentOrRtl,
        textAlign = TextAlign.Center
    ),
    val noteTitleInputPlaceholder: TextStyle = TextStyle(
        fontFamily = vazirFontFamily,
        fontSize = 18.sp,
        fontWeight = FontWeight.Medium,
        textDirection = TextDirection.ContentOrRtl,
        textAlign = TextAlign.Center
    ),
    val noteBodyInputValue: TextStyle = TextStyle(
        fontFamily = vazirFontFamily,
        fontSize = 16.sp,
        fontWeight = FontWeight.Normal,
        textDirection = TextDirection.ContentOrRtl,
        textAlign = TextAlign.Center
    ),
    val noteBodyInputPlaceholder: TextStyle = TextStyle(
        fontFamily = vazirFontFamily,
        fontSize = 16.sp,
        fontWeight = FontWeight.Normal,
        textDirection = TextDirection.ContentOrRtl,
        textAlign = TextAlign.Center
    ),
)

val LocalStyle = staticCompositionLocalOf { Style() }
val MaterialTheme.style: Style
    @Composable
    @ReadOnlyComposable
    get() = LocalStyle.current
