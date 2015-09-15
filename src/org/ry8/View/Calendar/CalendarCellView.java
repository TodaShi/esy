// Copyright 2013 Square, Inc.

package org.ry8.View.Calendar;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.widget.TextView;

import cn.ecailan.esy.R;


public class CalendarCellView extends TextView {

  private static final int[] STATE_SELECTABLE = {
      R.attr.state_selectable
  };
  private static final int[] STATE_CURRENT_MONTH = {
      R.attr.state_current_month
  };
  private static final int[] STATE_TODAY = {
      R.attr.state_today
  };
  private static final int[] STATE_RANGE_FIRST = {
      R.attr.state_range_first
  };
  private static final int[] STATE_RANGE_MIDDLE = {
      R.attr.state_range_middle
  };
  private static final int[] STATE_RANGE_LAST = {
      R.attr.state_range_last
  };
  
  private static final int[] STATE_MRAK_TEXT = {
      R.attr.state_mark_text
  };

  private boolean isSelectable = false;
  private boolean isCurrentMonth = false;
  private boolean isToday = false;
  private boolean isMark = false;
  private boolean isMarkText = false;
  private MonthCellDescriptor.RangeState rangeState = MonthCellDescriptor.RangeState.NONE;
  private Paint paint = new Paint(Paint.SUBPIXEL_TEXT_FLAG
          |Paint.ANTI_ALIAS_FLAG);
  
  public CalendarCellView(Context context) {
    super(context);
  }

  public CalendarCellView(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  public CalendarCellView(Context context, AttributeSet attrs, int defStyle) {
    super(context, attrs, defStyle);
  }

  public void setSelectable(boolean isSelectable) {
    this.isSelectable = isSelectable;
    refreshDrawableState();
  }

  public void setCurrentMonth(boolean isCurrentMonth) {
    this.isCurrentMonth = isCurrentMonth;
    refreshDrawableState();
  }

  public void setToday(boolean isToday) {
    this.isToday = isToday;
    refreshDrawableState();
  }

  public void setRangeState(MonthCellDescriptor.RangeState rangeState) {
    this.rangeState = rangeState;
    refreshDrawableState();
  }
  
  

  public void setMark(boolean isMark) {
	this.isMark = isMark;
  }

  public void setMarkText(boolean isMarkText) {
	this.isMarkText = isMarkText;
	refreshDrawableState();
  }

@Override protected int[] onCreateDrawableState(int extraSpace) {
    final int[] drawableState = super.onCreateDrawableState(extraSpace + 4);

    if (isSelectable) {
      mergeDrawableStates(drawableState, STATE_SELECTABLE);
    }

    if (isCurrentMonth) {
      mergeDrawableStates(drawableState, STATE_CURRENT_MONTH);
    }

    if (isToday) {
      mergeDrawableStates(drawableState, STATE_TODAY);
    }
    
    if (isMarkText) {
      mergeDrawableStates(drawableState, STATE_MRAK_TEXT);
    }

    if (rangeState == MonthCellDescriptor.RangeState.FIRST) {
      mergeDrawableStates(drawableState, STATE_RANGE_FIRST);
    } else if (rangeState == MonthCellDescriptor.RangeState.MIDDLE) {
      mergeDrawableStates(drawableState, STATE_RANGE_MIDDLE);
    } else if (rangeState == MonthCellDescriptor.RangeState.LAST) {
      mergeDrawableStates(drawableState, STATE_RANGE_LAST);
    }

    return drawableState;
  }

  public   int mark_pic_color=getResources().getColor( R.color.calendar_mark_pic_color);

    @Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		if (isMark) {
			paint.setColor(mark_pic_color);
            paint.setStyle(Paint.Style.FILL);
                    // 绘制这个三角形,你可以绘制任意多边形
                    Path path = new Path();
                    path.moveTo(getWidth(), 0);// 此点为多边形的起点
                    path.lineTo(getWidth(), 25);
                    path.lineTo(getWidth()-25, 0);
                    path.close(); // 使这些点构成封闭的多边形
                    canvas.drawPath(path, paint);

            //canvas.drawCircle(getWidth()-20, 20, 8.0f, paint);
		}
	}


}
