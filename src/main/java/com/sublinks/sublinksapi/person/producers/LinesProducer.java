package com.sublinks.sublinksapi.person.producers;

import cn.apiclub.captcha.noise.CurvedLineNoiseProducer;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.CubicCurve2D;
import java.awt.geom.PathIterator;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.security.SecureRandom;
import java.util.List;
import java.util.Random;

public class LinesProducer extends CurvedLineNoiseProducer {
  private static final Random RAND = new SecureRandom();

  private long linesToDraw = 1;
  private List<Color> colors = List.of(Color.BLACK);

  public LinesProducer(int linesToDraw) {

    super();
    this.linesToDraw = linesToDraw;
  }

  public LinesProducer(int linesToDraw, List<Color> colors) {

    super();
    this.linesToDraw = linesToDraw;
    this.colors = colors;
  }

  @Override
  public void makeNoise(BufferedImage image) {

    for (int i = 0; i < linesToDraw; i++) {
      this.doNoise(image, colors.get(RAND.nextInt(colors.size())), RAND.nextInt(3) + 2);
    }
  }
  private void doNoise(BufferedImage image, Color _color, int _width) {
    int width = image.getWidth();
    int height = image.getHeight();

    // the curve from where the points are taken
    CubicCurve2D cc = new CubicCurve2D.Float(width * .1f, height
        * RAND.nextFloat(), width * .1f, height
        * RAND.nextFloat(), width * .25f, height
        * RAND.nextFloat(), width * .9f, height
        * RAND.nextFloat());

    // creates an iterator to define the boundary of the flattened curve
    PathIterator pi = cc.getPathIterator(null, 2);
    Point2D[] tmp = new Point2D[200];
    int i = 0;

    // while pi is iterating the curve, adds points to tmp array
    while (!pi.isDone()) {
      float[] coords = new float[6];
      switch (pi.currentSegment(coords)) {
        case PathIterator.SEG_MOVETO:
        case PathIterator.SEG_LINETO:
          tmp[i] = new Point2D.Float(coords[0], coords[1]);
      }
      i++;
      pi.next();
    }

    // the points where the line changes the stroke and direction
    Point2D[] pts = new Point2D[i];
    // copies points from tmp to pts
    System.arraycopy(tmp, 0, pts, 0, i);

    Graphics2D graph = (Graphics2D) image.getGraphics();
    graph.setRenderingHints(new RenderingHints(
        RenderingHints.KEY_ANTIALIASING,
        RenderingHints.VALUE_ANTIALIAS_ON));

    graph.setColor(_color);

    // for the maximum 3 point change the stroke and direction
    for (i = 0; i < pts.length - 1; i++) {
      if (i < 3) {
        graph.setStroke(new BasicStroke(_width));
      }
      graph.drawLine((int) pts[i].getX(), (int) pts[i].getY(),
          (int) pts[i + 1].getX(), (int) pts[i + 1].getY());
    }

    graph.dispose();
  }
}
