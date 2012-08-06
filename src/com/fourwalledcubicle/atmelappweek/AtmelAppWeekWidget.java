package com.fourwalledcubicle.atmelappweek;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.res.Resources;
import android.widget.RemoteViews;

import java.util.Calendar;

public class AtmelAppWeekWidget extends AppWidgetProvider {
	private enum WeekTypes
	{
		WEEK_BEER,
		WEEK_WAFFLES;
		
		public static WeekTypes getWeekType(int weekOfYear)
		{
			if ((weekOfYear % 2) == 0)
				return WEEK_BEER;
			else
				return WEEK_WAFFLES;
		}
	}
	
	final Calendar now = Calendar.getInstance();
	 
	@Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager,
			int[] appWidgetIds) {
		ComponentName thisWidget = new ComponentName(context, AtmelAppWeekWidget.class);
		Resources res = context.getResources();

		int[] allWidgetIds = appWidgetManager.getAppWidgetIds(thisWidget);
		for (int widgetId : allWidgetIds) {
			RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.widgetlayout);
			
			int weekOfYear = now.get(Calendar.WEEK_OF_YEAR);
			WeekTypes weekType = WeekTypes.getWeekType(weekOfYear);
						
			remoteViews.setTextViewText(R.id.foodtext,
					res.getString(R.string.week_indicator, weekOfYear) + " " +
					res.getStringArray(R.array.foodstuffs)[(weekType == WeekTypes.WEEK_BEER) ? 0 : 1]);
			remoteViews.setImageViewResource(R.id.foodimage,
					(weekType == WeekTypes.WEEK_BEER) ? R.drawable.beer : R.drawable.waffles);

			appWidgetManager.updateAppWidget(widgetId, remoteViews);
		}
	}
		
}