/*
 *
 * Copyright (c) 2000-2003 by Rodney Kinney
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Library General Public
 * License (LGPL) as published by the Free Software Foundation.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Library General Public License for more details.
 *
 * You should have received a copy of the GNU Library General Public
 * License along with this library; if not, copies are available
 * at http://www.opensource.org.
 */
package VASSAL.build.module.map.boardPicker.board;

import VASSAL.build.module.map.boardPicker.board.mapgrid.GridNumbering;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;

/**
 * A MapGrid overlays a map board to constrain
 * the legal locations of GamePieces
 */
public interface MapGrid {
  /**
   * @see MapGrid#snapTo(Point p, boolean force, boolean onlyCenter)
   */
  Point snapTo(Point p);

  /**
   * @param onlyCenter If true, snaps only to the center, never to the edge or corner.
   * @return The nearest grid location to the given point. This can for instance snap
   * to center, edge or corner, depending on settings and arguments.
   */
  default Point snapTo(Point p, boolean force, boolean onlyCenter) {
    return snapTo(p);
  }

  /**
   * @see MapGrid#snapTo(Point p, boolean force, boolean onlyCenter)
   */
  default Point snapTo(Point p, boolean force) {
    return snapTo(p, force, false);
  }

  /**
   * @return true if the given point may not be a local location.
   * I.e., if this grid will attempt to snap it to the nearest grid location */
  boolean isLocationRestricted(Point p);

  /**
   * @return a string describing the location containing the given point
   */
  String locationName(Point p);
  String localizedLocationName(Point p);

  /**
   * @return A point p such that locationName(p).equals(location).
   * @throws BadCoords if the location is not valid or formatted incorrectly.
   */
  Point getLocation(String location) throws BadCoords;

  /**
   * @return the range between two points, in some unit appropriate
   * to the grid (e.g. hexes or squares)
   */
  int range(Point p1, Point p2);


  /**
   * Return an estimation of the maximum number of pixels per range unit for the grid that applies at the specified point.
   * Does not need to be exact, but must defer on the larger side to ensure fast range-checking by QTree lookup finds all target pieces.
   *
   * @return maximum number of pixels per range unit.
   */
  default int getMaxPixelsPerRangeUnit(Point p) {
    return 1;
  }

  /** Whether this grid should be drawn on the map */
  boolean isVisible();

  /**
   * Draw the grid
   * @param bounds the boundaries of the grid (in magnified coordinates)
   * @param scale the magnification factor
   */
  void draw(Graphics g, Rectangle bounds, Rectangle visibleRect, double scale, boolean reversed);

  GridNumbering getGridNumbering();

  final class BadCoords extends Exception {
    private static final long serialVersionUID = 1L;

    public BadCoords() {
      super();
    }

    public BadCoords(String s) {
      super(s);
    }
  }
}
