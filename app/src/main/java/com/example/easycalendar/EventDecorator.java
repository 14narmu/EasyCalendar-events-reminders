package com.example.easycalendar;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.graphics.drawable.shapes.RectShape;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;
import com.prolificinteractive.materialcalendarview.spans.DotSpan;


import org.threeten.bp.LocalDate;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Locale;

public class EventDecorator implements DayViewDecorator {

    private final int color;
    private final  HashSet<CalendarDay> dates;
    Context context;

    public EventDecorator(int color, HashSet<CalendarDay> dates, Context context) {
        this.color = color;
        this.dates = dates;
        this.context = context;
    }

    @Override
    public boolean shouldDecorate(CalendarDay day) {
        return dates.contains(day);

    }

    @Override
    public void decorate(DayViewFacade view) {
        //view.addSpan(new DotSpan(20, color));
        if(color == 1){
            ShapeDrawable circle = new ShapeDrawable( new RectShape());
            circle.setAlpha(30);
            view.setBackgroundDrawable(circle);

          //  view.setBackgroundDrawable(context.getDrawable(R.drawable.circle3));
            //view.setBackgroundDrawable(context.getDrawable(R.drawable.circle2));
        }else
            view.setBackgroundDrawable(context.getDrawable(R.drawable.circle));
        /*
        ShapeDrawable circle = new ShapeDrawable( new OvalShape() );
        view.setBackgroundDrawable(circle);*/

    }
}