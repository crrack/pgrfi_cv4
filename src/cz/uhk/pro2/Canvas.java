import cz.uhk.pro2.model.Point;
import cz.uhk.pro2.model.Polygon;
import cz.uhk.pro2.raster.*;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

/**
 * trida pro kresleni na platno: zobrazeni pixelu
 *
 * @author PGRF FIM UHK
 * @version 2020
 */

public class Canvas {

    private JFrame frame;
    private JPanel panel;
    //private BufferedImage img;
    private Raster raster;
    private LineRasterizer lineRasterizer;

    public Canvas(int width, int height) {
        frame = new JFrame();

        frame.setLayout(new BorderLayout());
        frame.setTitle("UHK FIM PGRF : " + this.getClass().getName());
        frame.setResizable(false);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        raster = new RasterBufferedImage(width, height);
        //lineRasterizer = new LineRasterizerFill(raster);
        lineRasterizer = new LineRasterizerBI(raster);
        //img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        panel = new JPanel() {
            private static final long serialVersionUID = 1L;

            @Override
            public void paintComponent(Graphics g) {
                super.paintComponent(g);
                present(g);
            }
        };

        panel.setPreferredSize(new Dimension(width, height));

        frame.add(panel, BorderLayout.CENTER);
        frame.pack();
        frame.setVisible(true);
    }

    public void clear() {
        BufferedImage img = ((RasterBufferedImage)raster).getImg();
        Graphics gr = img.getGraphics();
        gr.setColor(new Color(0x2f2f2f));
        gr.fillRect(0, 0, img.getWidth(), img.getHeight());
    }

    public void present(Graphics graphics) {
        BufferedImage img = ((RasterBufferedImage)raster).getImg();
        graphics.drawImage(img, 0, 0, null);
    }

    public void draw() {
        clear();

        lineRasterizer.rasterize(10,10,100,50);
        lineRasterizer.rasterize(50,100,200,150);
        lineRasterizer.rasterize(10,10,100,250);
        lineRasterizer.rasterize(10,10,100,550);
        Polygon  polygon = new Polygon();
        polygon.addPoint(new Point(50,100));
        polygon.addPoint(new Point(150,250));
        polygon.addPoint(new Point(100,10));
        lineRasterizer.rasterize(polygon);
    }


    public void start() {
        draw();
        panel.repaint();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Canvas(800, 600).start());
    }

}