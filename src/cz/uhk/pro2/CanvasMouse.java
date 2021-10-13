package cz.uhk.pro2;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import cz.uhk.pro2.model.Line;
import cz.uhk.pro2.raster.*;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

/**
 * trida pro kresleni na platno: zobrazeni pixelu, ovladani mysi
 *
 * @author PGRF FIM UHK
 * @version 2020
 */
public class CanvasMouse {

    private JPanel panel;
    //private BufferedImage img;
    private Raster raster;
    private LineRasterizer lineRasterizer;
    private List<Line> lines = new ArrayList<>();
    private int x1, y1;

    public CanvasMouse(int width, int height) {
        JFrame frame = new JFrame();

        frame.setLayout(new BorderLayout());
        frame.setTitle("UHK FIM PGRF : " + this.getClass().getName());
        frame.setResizable(false);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        raster = new RasterBufferedImage(width, height);

        lineRasterizer = new LineRasterizerBI(raster);

        panel = new JPanel() {
            private static final long serialVersionUID = 1L;

            @Override
            public void paintComponent(Graphics g) {
                super.paintComponent(g);
                present(g);
            }
        };
        panel.setPreferredSize(new Dimension(width, height));

        frame.add(panel);
        frame.pack();
        frame.setVisible(true);

        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1) {
                    x1 = e.getX();
                    y1 = e.getY();
                    //raster.setPixel(e.getX(), e.getY(), 0xffff00);
                }
                if (e.getButton() == MouseEvent.BUTTON2)
                    raster.setPixel(e.getX(), e.getY(), 0xff00ff);
                if (e.getButton() == MouseEvent.BUTTON3)
                    raster.setPixel(e.getX(), e.getY(), 0xffffff);
                //panel.repaint();
            }

            public void mouseReleased(MouseEvent e) {
                lines.add(new Line(x1,y1,e.getX(),e.getY()));
                redraw();
            }
        });

        panel.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                clear();
                redraw();
                lineRasterizer.rasterize(x1,y1,e.getX(),e.getY());
                panel.repaint();
            }
        });
    }

    public void clear() {
        BufferedImage img = ((RasterBufferedImage)raster).getImg();
        Graphics gr = img.getGraphics();
        gr.setColor(new Color(0x2f2f2f));
        gr.fillRect(0, 0, img.getWidth(), img.getHeight());
    }

    public void redraw() {
        for (int i = 0; i < lines.size(); i++){
            lineRasterizer.rasterize(lines.get(i));
        }
        panel.repaint();
    }

    public void present(Graphics graphics) {
        BufferedImage img = ((RasterBufferedImage)raster).getImg();
        graphics.drawImage(img, 0, 0, null);
    }

    public void start() {
        clear();
        BufferedImage img = ((RasterBufferedImage)raster).getImg();
        img.getGraphics().drawString("Use mouse buttons", 5, img.getHeight() - 5);
        panel.repaint();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new CanvasMouse(800, 600).start());
    }

}
