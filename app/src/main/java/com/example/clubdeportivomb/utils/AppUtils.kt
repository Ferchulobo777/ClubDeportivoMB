package com.example.clubdeportivomb.utils

import android.content.Context
import android.graphics.Color
import android.text.SpannableString
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.graphics.Typeface
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import com.example.clubdeportivomb.R

object AppUtils {

    // Función para el saludo con nombre destacado
    fun setStyledWelcomeMessage(textView: TextView, userName: String, context: Context) {
        val textoCompleto = "Bienvenido $userName!!"
        val spannable = SpannableString(textoCompleto)
        val startIndex = textoCompleto.indexOf(userName)
        val endIndex = startIndex + userName.length

        // Color dorado y negrita para el nombre
        spannable.setSpan(
            ForegroundColorSpan(Color.parseColor("#FFD700")),
            startIndex,
            endIndex,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )

        spannable.setSpan(
            StyleSpan(Typeface.BOLD),
            startIndex,
            endIndex,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )

        textView.text = spannable
    }

    // Función para la animación de la pelota
    fun startBallAnimation(imageView: ImageView, context: Context) {
        val rotation = AnimationUtils.loadAnimation(context, R.anim.rotate_ball)
        imageView.startAnimation(rotation)
    }
    // En AppUtils.kt - Agregar esta nueva función
    fun setStyledTextWithHighlight(textView: TextView, fullText: String, wordToHighlight: String, context: Context) {
        val spannable = SpannableString(fullText)
        val startIndex = fullText.indexOf(wordToHighlight)

        if (startIndex != -1) {
            val endIndex = startIndex + wordToHighlight.length

            // Color dorado y negrita para la palabra destacada
            spannable.setSpan(
                ForegroundColorSpan(Color.parseColor("#FFD700")),
                startIndex,
                endIndex,
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
            )

            spannable.setSpan(
                StyleSpan(Typeface.BOLD),
                startIndex,
                endIndex,
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
            )

            textView.text = spannable
        } else {
            textView.text = fullText
        }
    }
}