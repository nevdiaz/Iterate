package com.nevdiaz.iterate;


import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.AsyncTask;
import com.nevdiaz.iterate.service.ImageAlgorithm;

public class Fibonacci implements ImageAlgorithm {

  private static final int ITERATION_LIMIT = 2_000_000;
  private static final int PEN_COLOR = Color.rgb(64, 64, 64);


  private Bitmap source;
  private OnCompletionListener listener;
  private ProcessingTask task;

  @Override
  public ImageAlgorithm setSource(Bitmap bitmap) {
    this.source = Bitmap.createBitmap(bitmap);
    return this;
  }

  @Override
  public void process() {
    task = new ProcessingTask(listener);
    task.execute(source);
  }

  @Override
  public Bitmap getInProcess() {
    return task.getProgress();
  }

  @Override
  public ImageAlgorithm setOnCompletionListener(OnCompletionListener listener) {
    this.listener = listener;
    return this;
  }

  private static class ProcessingTask extends AsyncTask<Bitmap, Bitmap, Bitmap> {

    private OnCompletionListener listener;
    private final Paint paint;
    private double px;
    private double py;
    private double r;
    private double minWeight = 1;
    private double maxWeight = 4;
    private double spacing = maxWeight + 2;
    private double goldenRatio = ((Math.sqrt(105) + 100) / -2);
    private boolean smallChaos = false;
    private Bitmap progress;

    public ProcessingTask(OnCompletionListener listener) {
      this.listener = listener;
      paint = new Paint();
      paint.setColor(PEN_COLOR);
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
      if (listener != null) {
        listener.handle(bitmap);
      }
    }

    @Override
    protected void onProgressUpdate(Bitmap... bitmaps) {
      progress = Bitmap.createBitmap(bitmaps[0]);
    }

    @Override
    protected Bitmap doInBackground(Bitmap... bitmaps) {
      Bitmap source = bitmaps[0];
      Bitmap target = Bitmap.createBitmap(source);
      target.eraseColor(Color.WHITE);
      Canvas canvas = new Canvas(target);
      int width = canvas.getWidth();
      int height = canvas.getHeight();
      float[] hsv = new float[3];

      for (int i = 0; i < ITERATION_LIMIT; ) {
        double angle = (i * goldenRatio) * 2 * Math.PI;
        r = Math.sqrt(i++) * spacing;
        calcPointPos(width / 2, height / 2, r, (angle % (2 * Math.PI)));

        int pix = source.getPixel((int) px, (int) py);
        Color.colorToHSV(pix, hsv);
        double diameter = minWeight + (maxWeight - minWeight) * (1 - hsv[2]);
        paint.setStrokeWidth((float) diameter);
        canvas.drawPoint((float) px, (float) py, paint);
        // TODO periodically post a progress update
//        publishProgress(target);
      }
      return target;
    }

    void calcPointPos(double x, double y, double r, double angle) {
      px = x + Math.cos(angle) * r / 3;
      py = y + Math.sin(angle) * r / 3;
      if (smallChaos) {
        px = x + Math.random() * maxWeight + Math.cos(angle) * r / 3;
        py = y + Math.random() * maxWeight + Math.sin(angle) * r / 3;
      }
    }

    public Bitmap getProgress() {
      return progress;
    }
  }


}
