package com.heuristix.guns.swing;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

/**
 * Created by IntelliJ IDEA.
 * User: Matt
 * Date: 12/5/11
 * Time: 5:20 PM
 */
public class BufferedCanvas extends Canvas {

	private static final long serialVersionUID = -3823361008715743965L;

	private BufferedImage buffer;
    private Graphics2D bufferGraphics;

    public BufferedCanvas(int width, int height) {
        this.setSize(width, height);
    }

    @Override
    public void update(Graphics g) {
        if(buffer == null) {
            buffer = (BufferedImage) createImage(getWidth(), getHeight());
            bufferGraphics = buffer.createGraphics();
        }
        paint(bufferGraphics);
        g.drawImage(buffer, 0, 0, this);
    }

}
