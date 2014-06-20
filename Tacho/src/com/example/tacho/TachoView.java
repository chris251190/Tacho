package com.example.tacho;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

public class TachoView extends View {
	Paint myPaint;
	Bitmap image;
	Bitmap temp;

	private float x1, y1, x2, y2;

	public TachoView(Context context, AttributeSet attrs) {
		super(context, attrs);

		myPaint = new Paint();
		myPaint.setAntiAlias(true);
		myPaint.setColor(Color.RED);
		myPaint.setStrokeWidth(5);

		TypedArray a = context.getTheme().obtainStyledAttributes(attrs,
				R.styleable.TachoView, 0, 0);

		try {
			// get the floats specified using the names in attrs.xml
			x1 = a.getFloat(R.styleable.TachoView_ersteKoordinate, 0);
			y1 = a.getFloat(R.styleable.TachoView_zweiteKoordinate, 0);
			x2 = a.getFloat(R.styleable.TachoView_dritteKoordinate, 0);
			y2 = a.getFloat(R.styleable.TachoView_vierteKoordinate, 0);

		} finally {
			a.recycle();
		}
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);

		temp = BitmapFactory.decodeResource(getResources(), R.drawable.tacho);
		image = Bitmap.createScaledBitmap(temp, canvas.getWidth() + 15,
				(int) ((canvas.getHeight() / 2) * 0.8), true);

		canvas.drawBitmap(image, 5, 10, null);

		canvas.drawLine(x1, y1, x2, y2, myPaint);				
	}

	public float getX1() {
		return x1;
	}

	public float getY1() {
		return y1;
	}

	public float getX2() {
		return x2;
	}

	public float getY2() {
		return y2;
	}

	public void setX1(float newX1) {
		// update the instance variable
		x1 = newX1;
		// redraw the view
		invalidate();
		requestLayout();
	}

	public void setY1(float newY1) {
		// update the instance variable
		y1 = newY1;
		// redraw the view
		invalidate();
		requestLayout();
	}

	public void setX2(float newX2) {
		// update the instance variable
		x2 = newX2;
		// redraw the view
		invalidate();
		requestLayout();
	}

	public void setY2(float newY2) {
		// update the instance variable
		y2 = newY2;
		// redraw the view
		invalidate();
		requestLayout();
	}
}
