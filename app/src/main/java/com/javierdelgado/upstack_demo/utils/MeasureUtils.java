package com.javierdelgado.upstack_demo.utils;

import android.content.Context;
import android.content.res.Resources;
import android.util.TypedValue;

public class MeasureUtils {
    public static int dipToPixel(int n, Context context){
        Resources r = context.getResources();
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, n, r.getDisplayMetrics());
    }
}
