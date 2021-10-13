package cz.uhk.pro2.raster;

import java.awt.*;
import java.awt.image.BufferedImage;

public interface Raster {
    void setPixel(int x, int y, int color);

    int getWidth();
    int getHeight();
}
