package de.janik.devils.texture;

import static de.janik.devils.utility.BufferTools.createByteBufferDirect;
import static de.janik.devils.utility.BufferTools.createIntBufferDirect;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MAG_FILTER;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MIN_FILTER;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_WRAP_S;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_WRAP_T;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_BYTE;
import static org.lwjgl.opengl.GL11.glBindTexture;
import static org.lwjgl.opengl.GL11.glGenTextures;
import static org.lwjgl.opengl.GL11.glTexImage2D;
import static org.lwjgl.opengl.GL11.glTexParameterf;
import static org.lwjgl.opengl.GL11.glTexParameteri;
import static org.lwjgl.opengl.GL30.glGenerateMipmap;

import java.awt.Graphics;
import java.awt.color.ColorSpace;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.ComponentColorModel;
import java.awt.image.DataBuffer;
import java.awt.image.Raster;
import java.awt.image.WritableRaster;
import java.io.File;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.Hashtable;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;
import org.lwjgl.opengl.GL14;

import de.janik.devils.math.MathHelper;

/**
 * A utility class, to load buffered-images and turn them into OpenGL-textures.
 * 
 * @author Jan.Marcel.Janik
 * 
 */
public class LWJGL_TextureLoader
{
	public static final int				GL_TEXTURE_2D				= GL11.GL_TEXTURE_2D;

	public static final int				GL_RGBA						= GL11.GL_RGBA;
	public static final int				GL_RGB						= GL11.GL_RGB;

	public static final int				GL_NEAREST					= GL11.GL_NEAREST;
	public static final int				GL_LINEAR					= GL11.GL_LINEAR;
	public static final int				GL_NEAREST_MIPMAP_NEAREST	= GL11.GL_NEAREST_MIPMAP_NEAREST;
	public static final int				GL_NEAREST_MIPMAP_LINEAR	= GL11.GL_NEAREST_MIPMAP_LINEAR;
	public static final int				GL_LINEAR_MIPMAP_NEAREST	= GL11.GL_LINEAR_MIPMAP_NEAREST;
	public static final int				GL_LINEAR_MIPMAP_LINEAR		= GL11.GL_LINEAR_MIPMAP_LINEAR;

	public static final int				GL_REPEAT					= GL11.GL_REPEAT;
	public static final int				GL_MIRRORED_REPEAT			= GL14.GL_MIRRORED_REPEAT;
	public static final int				GL_CLAMP					= GL11.GL_CLAMP;
	public static final int				GL_CLAMP_TO_EDGE			= GL12.GL_CLAMP_TO_EDGE;

	private static LWJGL_TextureLoader	loader;

	private static ImageLoader			imageLoader;

	private ColorModel					glAlphaColorModel;
	private ColorModel					glColorModel;

	private Hashtable<String, Texture>	textures;

	// <- Hidden constructor ->
	private LWJGL_TextureLoader()
	{
		// The Color-Model used for RGBA color's with 32bit color depth
		glAlphaColorModel = new ComponentColorModel(ColorSpace.getInstance(ColorSpace.CS_sRGB), new int[] { 8, 8, 8, 8 }, true, false,
				ComponentColorModel.TRANSLUCENT, DataBuffer.TYPE_BYTE);

		// The Color-Model used for RGB color's with 24bit color depth
		glColorModel = new ComponentColorModel(ColorSpace.getInstance(ColorSpace.CS_sRGB), new int[] { 8, 8, 8, 0 }, false, false, ComponentColorModel.OPAQUE,
				DataBuffer.TYPE_BYTE);

		textures = new Hashtable<String, Texture>();

		imageLoader = ImageLoader.getInstance();
	}

	private int createTextureID()
	{
		IntBuffer tmp = createIntBufferDirect(1);
		glGenTextures(tmp);
		return tmp.get(0);
	}

	public Texture getTexture(String name)
	{
		Texture tex = null;

		tex = textures.get(name);

		return tex;
	}

	public void putTexture(Texture t, String name)
	{
		textures.put(name, t);
	}

	public void removeTexture(Texture t)
	{
		textures.remove(t);
	}

	private ByteBuffer convertImageData(BufferedImage img)
	{
		WritableRaster raster;
		BufferedImage texImage;

		int texWidth = MathHelper.get2Fold(img.getWidth());
		int texHeight = MathHelper.get2Fold(img.getHeight());

		// create a raster that can be used by OpenGL as a source for a texture
		if (img.getColorModel().hasAlpha())
		{
			raster = Raster.createInterleavedRaster(DataBuffer.TYPE_BYTE, texWidth, texHeight, 4, null);
			texImage = new BufferedImage(glAlphaColorModel, raster, false, new Hashtable<Object, Object>());
		}
		else
		{
			raster = Raster.createInterleavedRaster(DataBuffer.TYPE_BYTE, texWidth, texHeight, 3, null);
			texImage = new BufferedImage(glColorModel, raster, false, new Hashtable<Object, Object>());
		}

		// copy the source image into the produced image
		Graphics g = texImage.getGraphics();
		g.setColor(new java.awt.Color(0f, 0f, 0f, 0f));
		g.fillRect(0, 0, texWidth, texHeight);
		g.drawImage(img, 0, 0, null);

		// Convert the bufferedImage into an byte buffer.

		/*
		 * byte[] data = ((DataBufferByte)
		 * texImage.getRaster().getDataBuffer()).getData(); return
		 * createByteBufferDirect(data);
		 */

		return imgToByteArray(texImage);
	}

	public Texture load(int textureTarget, int pixelFormat, int minificationFilter, int magnificationFilter, boolean mipMap, int mipMapLevel,
			boolean autoCreateMipMap, int wrap_S, int wrap_T, String path)
	{
		/** The pixel format of the source file */
		int srcPixelFormat = pixelFormat;

		/** The ID used to call the texture buffer (RAM) */
		int textureID = createTextureID();

		Texture texture = new Texture(textureID);

		// bind this texture
		glBindTexture(GL_TEXTURE_2D, textureID);

		ByteBuffer[] textureData;
		BufferedImage[] images;

		/*
		 * Put's the raw image data from the bufferedImage to a byteBuffer, to
		 * make the texture usable for OpenGl
		 */
		if (mipMap && !autoCreateMipMap)
		{
			textureData = new ByteBuffer[mipMapLevel];
			images = new BufferedImage[mipMapLevel];

			String s0 = path.substring(0, path.length() - 4);
			String s1 = path.substring(path.length() - 4, path.length());

			for (int i = 0; i < mipMapLevel; i++)
			{
				images[i] = adjustSize(imageLoader.load(s0 + i + s1));

				textureData[i] = convertImageData(images[i]);
			}
		}
		else
		{
			textureData = new ByteBuffer[1];
			images = new BufferedImage[1];

			images[0] = adjustSize(imageLoader.load(path));

			textureData[0] = convertImageData(images[0]);
		}

		if (!mipMap || (autoCreateMipMap && mipMap))
		{
			if (autoCreateMipMap)
			{
				glTexParameteri(GL_TEXTURE_2D, GL12.GL_TEXTURE_MAX_LEVEL, mipMapLevel);
				glGenerateMipmap(GL_TEXTURE_2D);
			}

			glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, minificationFilter);
			glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, magnificationFilter);
			// GL11.GL_REPEAT GL11.GL_CLAMP GL12.GL_CLAMP_TO_EDGE
			glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, wrap_S);
			glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, wrap_T);

			/** Specify's a two-dimensional texture image, from a given buffer */
			glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, MathHelper.get2Fold(images[0].getWidth()), MathHelper.get2Fold(images[0].getHeight()), 0, srcPixelFormat,
					GL_UNSIGNED_BYTE, textureData[0]);
		}
		else
		{
			glTexParameteri(GL_TEXTURE_2D, GL12.GL_TEXTURE_MAX_LEVEL, mipMapLevel);

			for (int i = 0; i < mipMapLevel; i++)
			{
				glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, minificationFilter);
				glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, magnificationFilter);
				// GL11.GL_REPEAT GL11.GL_CLAMP GL12.GL_CLAMP_TO_EDGE
				glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, wrap_S);
				glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, wrap_T);

				/**
				 * Specify's a two-dimensional texture image, from a given
				 * buffer
				 */
				glTexImage2D(GL_TEXTURE_2D, i, GL_RGBA, MathHelper.get2Fold(images[0].getWidth()), MathHelper.get2Fold(images[0].getHeight()), 0,
						srcPixelFormat, GL_UNSIGNED_BYTE, textureData[0]);
			}
		}

		return texture;
	}

	public Texture load(int textureTarget, int pixelFormat, int minificationFilter, int magnificationFilter, boolean mipMap, int mipMapLevel, int wrap_S,
			int wrap_T, File file)
	{
		/** The pixel format of the source file */
		int srcPixelFormat = pixelFormat;

		/** The ID used to call the texture buffer (RAM) */
		int textureID = createTextureID();

		Texture texture = new Texture(textureID);

		// bind this texture
		glBindTexture(GL_TEXTURE_2D, textureID);

		ByteBuffer[] textureData;
		BufferedImage[] images;

		textureData = new ByteBuffer[1];
		images = new BufferedImage[1];

		images[0] = adjustSize(imageLoader.load(file));

		textureData[0] = convertImageData(images[0]);

		if (!mipMap)
		{
			glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, minificationFilter);
			glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, magnificationFilter);
			// GL11.GL_REPEAT GL11.GL_CLAMP GL12.GL_CLAMP_TO_EDGE
			glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, wrap_S);
			glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, wrap_T);

			/** Specify's a two-dimensional texture image, from a given buffer */
			glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, MathHelper.get2Fold(images[0].getWidth()), MathHelper.get2Fold(images[0].getHeight()), 0, srcPixelFormat,
					GL_UNSIGNED_BYTE, textureData[0]);
		}
		else
		{
			glTexParameteri(GL_TEXTURE_2D, GL12.GL_TEXTURE_MAX_LEVEL, mipMapLevel);
			glGenerateMipmap(GL_TEXTURE_2D);

			glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, minificationFilter);
			glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, magnificationFilter);
			// GL11.GL_REPEAT GL11.GL_CLAMP GL12.GL_CLAMP_TO_EDGE
			glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, wrap_S);
			glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, wrap_T);

			/** Specify's a two-dimensional texture image, from a given buffer */
			glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, MathHelper.get2Fold(images[0].getWidth()), MathHelper.get2Fold(images[0].getHeight()), 0, srcPixelFormat,
					GL_UNSIGNED_BYTE, textureData[0]);

		}

		return texture;
	}

	public Texture createTexture(int textureTarget, int pixelFormat, int minificationFilter, int magnificationFilter, boolean mipMap, int mipMapLevel,
			int wrap_S, int wrap_T, BufferedImage img)
	{
		/** The pixel format of the source file */
		int srcPixelFormat = pixelFormat;

		/** The ID used to call the texture buffer (RAM) */
		int textureID = createTextureID();

		Texture texture = new Texture(textureID);

		// bind texture
		glBindTexture(GL_TEXTURE_2D, textureID);

		if (img.getWidth() != MathHelper.get2Fold(img.getWidth()))
		{
			img = imageLoader.resize(img, MathHelper.get2Fold(img.getWidth()));
		}

		ByteBuffer imageData = convertImageData(img);

		if (mipMap)
		{
			glTexParameteri(GL_TEXTURE_2D, GL12.GL_TEXTURE_MAX_LEVEL, mipMapLevel);
			glGenerateMipmap(GL_TEXTURE_2D);
		}

		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, minificationFilter);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, magnificationFilter);
		// GL11.GL_REPEAT GL11.GL_CLAMP GL12.GL_CLAMP_TO_EDGE
		glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, wrap_S);
		glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, wrap_T);

		/** Specify's a two-dimensional texture image, from a given buffer */
		glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, img.getWidth(), img.getHeight(), 0, srcPixelFormat, GL_UNSIGNED_BYTE, imageData);

		return texture;
	}

	private static ByteBuffer imgToByteArray(BufferedImage img)
	{
		// Store the width and height of the image.
		int width = img.getWidth(), height = img.getHeight();

		/*
		 * System.out.println("Width:" + width + "\n" + "Height: " + height +
		 * "\n" + "Alocated Bytes: " + width * height * 4 + "\n");
		 */

		ByteBuffer bBuffer = createByteBufferDirect(new byte[(width * height) * 4]);
		// ByteBuffer.allocate((height * width) * 4);

		// TODO Change it, to use the Writable-Raster of the image, so we have
		// each color-component as a byte value (it should be little bit faster,
		// too.)

		// For each pixel of the image
		for (int y = 0; y < height; y++)
		{
			for (int x = 0; x < width; x++)
			{
				// Get the RGB-Color value of the current pixel
				int rgb = img.getRGB(x, y);

				// Put the color value in the buffer, ordered by "red", "green",
				// "blue", "alpha"
				bBuffer.put((byte) ((rgb >> 16) & 0xff));

				bBuffer.put((byte) ((rgb >> 8) & 0xff));

				bBuffer.put((byte) ((rgb & 0xff)));

				bBuffer.put((byte) ((rgb >> 24) & 0xff));

				/*
				 * System.out.printf("RGB: %d R: %d G: %d B: %d A: %d \n", rgb,
				 * (byte) ((rgb >> 16) & 0xff), (byte) ((rgb >> 8) & 0xff),
				 * (byte) (rgb & 0xff), (byte) ((rgb >> 24) & 0xff));
				 */
			}
		}
		// Flip the buffer
		bBuffer.flip();

		return bBuffer;
	}

	public ByteBuffer[] loadIconAsByteBuffer(String path)
	{
		BufferedImage img = imageLoader.load(path);

		return createIconsAsByteBuffer(img);
	}

	public ByteBuffer[] loadIconAsByteBuffer(File file)
	{
		BufferedImage icon = imageLoader.load(file);

		return (createIconsAsByteBuffer(icon));
	}

	public ByteBuffer[] createIconsAsByteBuffer(BufferedImage icon)
	{
		ByteBuffer bBuffer32 = imgToByteArray(imageLoader.resize(icon, 32));
		ByteBuffer bBuffer64 = imgToByteArray(imageLoader.resize(icon, 64));

		return new ByteBuffer[] { bBuffer32, bBuffer64 };
	}

	private BufferedImage adjustSize(BufferedImage img)
	{
		int width = img.getWidth();
		int height = img.getHeight();

		if ((width != MathHelper.get2Fold(width)) || (height != MathHelper.get2Fold(height)))
			img = ImageLoader.getInstance().resize(img, MathHelper.get2Fold(width), MathHelper.get2Fold(height));

		return img;
	}

	// <- Static method's ->
	public static LWJGL_TextureLoader getInstance()
	{
		if (loader == null)
			loader = new LWJGL_TextureLoader();

		return loader;
	}
}