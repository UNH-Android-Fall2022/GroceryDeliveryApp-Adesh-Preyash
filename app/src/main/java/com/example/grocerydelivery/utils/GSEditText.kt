package com.example.grocerydelivery.utils

import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatEditText

class GSEditText(context: Context, attrs: AttributeSet) : AppCompatEditText(context, attrs)
{
    init {
        applyFont()
    }
    //Citation : Referred to Youtube tutorial - https://youtu.be/hoK9OOP1KZw
    private fun applyFont()
    {
        val typeface: Typeface = Typeface.createFromAsset(context.assets, "Montserrat-Bold.otf")
        setTypeface(typeface)
    }
}