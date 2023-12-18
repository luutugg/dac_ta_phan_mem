package com.example.dactaphanmem.common

import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import android.text.SpannableString
import android.view.View
import android.view.animation.AccelerateInterpolator
import android.view.animation.DecelerateInterpolator
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.ColorRes
import androidx.annotation.DimenRes
import androidx.annotation.DrawableRes
import androidx.annotation.FontRes
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import com.example.dactaphanmem.presentation.getApplication

const val BOOLEAN_DEFAULT = false
const val INT_DEFAULT = 0
const val LONG_DEFAULT = 0L
const val DOUBLE_DEFAULT = 0.0
const val FLOAT_DEFAULT = 0f
const val STRING_DEFAULT = ""

fun View.setOnSafeClick(
    delayBetweenClicks: Long = DEFAULT_DEBOUNCE_INTERVAL,
    click: (view: View) -> Unit
) {
    setOnClickListener(object : DebouncedOnClickListener(delayBetweenClicks) {
        override fun onDebouncedClick(v: View) = click(v)
    })
}

fun View.setOnSafeGlobalClick(
    delayBetweenClicks: Long = DEFAULT_DEBOUNCE_INTERVAL,
    click: (view: View) -> Unit
) {
    setOnClickListener(object : GlobalDebouncedOnClickListener(delayBetweenClicks) {
        override fun onDebouncedClick(v: View) = click(v)
    })
}

fun View.setOnScaleClick(action: (() -> Unit)) {
    setOnSafeClick {
        scaleAnimation()
        action()
    }
}

fun View.scaleAnimation() {
    animate()
        .scaleX(1.3f)
        .scaleY(1.3f)
        .setDuration(150)
        .setInterpolator(DecelerateInterpolator())
        .withEndAction {
            ViewCompat.animate(this)
                .scaleX(1.0f)
                .scaleY(1.0f)
                .setDuration(50).interpolator = AccelerateInterpolator()
        }
}

fun View.show() {
    this.visibility = View.VISIBLE
}

fun View.hide() {
    this.visibility = View.INVISIBLE
}

fun View.gone() {
    this.visibility = View.GONE
}

fun View.enable() {
    isEnabled = true
}

fun View.disable() {
    isEnabled = false
}

fun TextView.setImageLeft(left: Drawable?) {
    setCompoundDrawablesWithIntrinsicBounds(left, null, null, null)
}

fun TextView.setImageTop(top: Drawable?) {
    setCompoundDrawablesWithIntrinsicBounds(null, top, null, null)
}

fun TextView.setImageRight(right: Drawable?) {
    setCompoundDrawablesWithIntrinsicBounds(null, null, right, null)
}

fun TextView.setImageBottom(bottom: Drawable?) {
    setCompoundDrawablesWithIntrinsicBounds(null, null, null, bottom)
}

fun TextView.setImageLeftRight(left: Drawable?, right: Drawable?) {
    setCompoundDrawablesWithIntrinsicBounds(left, null, right, null)
}

fun TextView.clearImage() {
    setCompoundDrawablesWithIntrinsicBounds(null, null, null, null)
}

fun getAppString(
    @StringRes stringId: Int,
    context: Context? = getApplication()
): String {
    return context?.getString(stringId) ?: ""
}

fun getAppString(
    @StringRes stringId: Int,
    vararg params: Any,
    context: Context? = getApplication()
): String {
    return context?.getString(stringId, *params) ?: ""
}

fun getAppSpannableString(
    @StringRes stringId: Int,
    context: Context? = getApplication()
): SpannableString {
    return SpannableString(context?.getString(stringId))
}

fun getAppFont(
    @FontRes fontId: Int,
    context: Context? = getApplication()
): Typeface? {
    return context?.let {
        ResourcesCompat.getFont(it, fontId)
    }
}

fun getAppDrawable(
    @DrawableRes drawableId: Int,
    context: Context? = getApplication()
): Drawable? {
    return context?.let {
        ContextCompat.getDrawable(it, drawableId)
    }
}

fun getAppDimensionPixel(
    @DimenRes dimenId: Int,
    context: Context? = getApplication()
): Int {
    return context?.resources?.getDimensionPixelSize(dimenId) ?: -1
}

fun getAppDimension(
    @DimenRes dimenId: Int,
    context: Context? = getApplication()
): Float {
    return context?.resources?.getDimension(dimenId) ?: -1f
}

fun getAppColor(
    @ColorRes colorRes: Int,
    context: Context? = getApplication()
): Int {
    return context?.let {
        ContextCompat.getColor(it, colorRes)
    } ?: Color.TRANSPARENT
}


private var lastPress: Long = 0

private const val EXIT_APP_DELAY = 1500

fun AppCompatActivity.checkBeforeBack(action: () -> Unit) {
    val currentTime = System.currentTimeMillis()
    if (currentTime - lastPress > EXIT_APP_DELAY) {
        Toast.makeText(this, "Vuốt thêm lần nữa để tắt app", Toast.LENGTH_SHORT).show()
    } else {
        action.invoke()
    }
    lastPress = currentTime
}


fun Fragment.checkBeforeBack(action: () -> Unit) {
    val currentTime = System.currentTimeMillis()
    if (currentTime - lastPress > EXIT_APP_DELAY) {
        Toast.makeText(context, "Vuốt thêm lần nữa để tắt app", Toast.LENGTH_SHORT).show()
    } else {
        action.invoke()
    }
    lastPress = currentTime
}


