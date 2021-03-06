package com.heuristix.guns.client.render;

import java.awt.image.BufferedImage;
import java.io.File;

import net.minecraft.client.Minecraft;

import com.heuristix.guns.helper.IOHelper;
import com.heuristix.guns.helper.ImageHelper;
import com.heuristix.guns.helper.MathHelper;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.client.TextureFXManager;

public class TextureManager {
	
	public static final String TEXTURE_FILE_NAME = "sprites";
	public static final String TEXTURE_FILE_FORMAT = "png";
	public static final String TEXTURE_FILE_NAME_FORMAT = TEXTURE_FILE_NAME + "_%d." + TEXTURE_FILE_FORMAT;
	
	private TextureList[] textures;
	private int maxResolution;
	
	public TextureManager() {
		this.textures = new TextureList[6];
		for (int i = 0; i < 6; i++) {
			textures[i] = new TextureList(MathHelper.pow(2, i + 4));
		}
	}
	
	public int registerTexture(String name, BufferedImage... images) {
		int textureIndex = -1;
		for (BufferedImage image : images) {
			int index = MathHelper.log2(image.getWidth()) - 4;
			if (textureIndex == -1 || textures[index].size() <= textureIndex) {
				textureIndex = textures[index].addTexture(image);
			}
		}
		for (int i = 0; i < textures.length; i++) {
			if (textures[i].size() <= textureIndex) {
				textures[i].addTexture(ImageHelper.getClosestTexture(textures[i].getSize(), images));
			}
		}
		return textureIndex;
	}
	
	public void setMaxResolution(int resolution) {
		maxResolution = MathHelper.log2(resolution) - 4;
	}
	
	public File writeTemporaryTextures(String folder) {
		File f = null;
		for (int i = 0; i <= maxResolution; i++) {
			f = IOHelper.getHeuristixTempFile(folder, getTextureFileName(textures[i].getSize()));
			ImageHelper.writeImage(textures[i].toBufferedImage(), TEXTURE_FILE_FORMAT, f);
		}
		return (f == null) ? f : f.getParentFile();
	}
	
	public static String getTextureFileName(int size) {
		return String.format(TEXTURE_FILE_NAME_FORMAT, size);
	}
	
	public static String getCurrentTextureFileName() {
		return getTextureFileName(getCurrentTextureSize());
	}
	
	public static int getCurrentTextureSize() {
		Minecraft client = FMLClientHandler.instance().getClient();
		if (client != null) {
			return TextureFXManager.instance().getTextureDimensions("/gui/items.png").width >> 4;
		}
		return 16;
	}
	
}