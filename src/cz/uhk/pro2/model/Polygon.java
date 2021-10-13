package cz.uhk.pro2.model;

import java.util.ArrayList;
import java.util.List;

public class Polygon {
    private List<Point> points;

    public Polygon() {
        points = new ArrayList<>();
    }

    public void addPoint(Point point) {
        points.add(point);
    }

    public List<Point> getPoints() {
        return points;
    }
}
