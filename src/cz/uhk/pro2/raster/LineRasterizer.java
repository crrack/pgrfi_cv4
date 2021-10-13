package cz.uhk.pro2.raster;

import cz.uhk.pro2.model.Line;
import cz.uhk.pro2.model.Point;
import cz.uhk.pro2.model.Polygon;

import java.util.List;

public abstract class LineRasterizer {
    Raster raster;
    public LineRasterizer(Raster raster){
        this.raster = raster;
    }

    public void rasterize(int x1, int y1, int x2, int y2){
        rasterize(x1, y1, x2, y2, 0xff00ff);
    }

    public void rasterize(int x1, int y1, int x2, int y2, int color){

    }

    public void rasterize(Polygon polygon){
        List<Point> points = polygon.getPoints();
        for(int i = 0; i < points.size(); i++){
            int x1 = points.get(i).getX();
            int y1 = points.get(i).getY();
            int x2 = points.get((i+1)%points.size()).getX();
            int y2 = points.get((i+1)%points.size()).getY();
            rasterize(x1,y1,x2,y2);
        }
        /*rasterize(
                points.get(0).getX(),
                points.get(0).getY(),
                points.get(points.size()-1).getX(),
                points.get(points.size()-1).getY()
        );*/
    }

    public void rasterize(Line line){
        //TODO
        rasterize(line.getX1(),line.getY1(),line.getX2(),line.getY2(), line.getColor());
    }
}
