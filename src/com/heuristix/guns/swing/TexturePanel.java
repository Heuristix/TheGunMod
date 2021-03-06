package com.heuristix.guns.swing;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JLabel;
import javax.swing.JPanel;

import com.heuristix.guns.helper.ImageHelper;
import com.heuristix.guns.helper.MathHelper;

/**
 * Created by IntelliJ IDEA.
 * User: Matt
 * Date: 1/31/12
 * Time: 10:18 PM
 */
public class TexturePanel extends JPanel {

	private static final long serialVersionUID = -6465252106344788762L;

	private DisplayableImageButton[][] textures;

    public TexturePanel() {
        init();
    }

    private void init() {
        textures = new DisplayableImageButton[2][6];
        JPanel[] panels = new JPanel[textures.length];
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new FlowLayout());
        for(int i = 0; i < textures.length; i++) {
            panels[i] = new JPanel();
            panels[i].setLayout(new GridLayout(textures[i].length / 2, 2, 5, 5));
            for(int j = 0; j < textures[i].length; j++) {
                JPanel panel = new JPanel();
                panel.setLayout(new FlowLayout());
                textures[i][j] = new DisplayableImageButton();
                textures[i][j].setPreferredSize(new Dimension(64, 64));
                int size = MathHelper.pow(2, 4 + j);
                panel.add(new JLabel(size + "x" + size));
                panel.add(textures[i][j]);
                panels[i].add(panel);
            }
            contentPanel.add(panels[i]);
        }
        setLayout(new BorderLayout());
        JPanel labelPanel = new JPanel();
        labelPanel.setLayout(new GridLayout(0, 2, 0, 0));
        JPanel gunLabelPanel = new JPanel(), projectileLabelPanel = new JPanel();
        gunLabelPanel.add(new JLabel("Gun"));
        projectileLabelPanel.add(new JLabel("Projectile"));
        labelPanel.add(gunLabelPanel);
        labelPanel.add(projectileLabelPanel);
        add(labelPanel, BorderLayout.NORTH);
        add(contentPanel, BorderLayout.CENTER);
    }

    public void setTexture(BufferedImage image, boolean gun) {
        int index = getTextureIndex(image.getWidth());
        int size = getTextureSize(index);
        textures[(gun) ? 0 : 1][index].updateImage(ImageHelper.resizeImage(image, size, size));
    }

    public Map<Integer, BufferedImage> getTextures(boolean gun) {
        Map<Integer, BufferedImage> textures = new HashMap<Integer, BufferedImage>();
        int i = (gun) ? 0 : 1;
        for(int j = 0; j < this.textures[i].length; j++) {
            if(this.textures[i][j].getImage() != null) {
                textures.put(getTextureSize(j), this.textures[i][j].getImage());
            }
        }
        return textures;
    }

    public BufferedImage getTexture(int i, int j) {
        return textures[i][j].getImage();
    }

    public int getTextureIndex(int size) {
        int i = 0;
        while(getTextureSize(i) < size) {
            i++;
        }
        return i;
    }

    public void addTextureChangedListener(boolean gun, TextureChangedListener... listeners) {
        for (DisplayableImageButton b : textures[(gun) ? 0 : 1]) {
            b.addTextureChangedListener(listeners);
        }
    }

    public void removeTextureChangedListener(boolean gun, TextureChangedListener... listeners) {
        for (DisplayableImageButton b : textures[(gun) ? 0 : 1]) {
            b.removeTextureChangedListener(listeners);
        }
    }

    public static int getTextureSize(int index) {
        return (int) Math.pow(2, 4 + index);
    }

    boolean updateFile(File file, int i, int j) {
        return textures[i][j].updateFile(file);
    }

    void updateImage(BufferedImage image, int i, int j) {
        textures[i][j].updateImage(image);
    }

    void selectedFile(File file, int i, int j) {
        textures[i][j].selectedFile(file);
    }

    File getFile(int i, int j) {
        return textures[i][j].getFile();
    }
}
