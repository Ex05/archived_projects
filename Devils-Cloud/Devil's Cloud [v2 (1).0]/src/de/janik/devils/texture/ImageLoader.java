package de.janik.devils.texture;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

/**
 * An utility class to load and manipulate buffered images.
 * 
 * @author Jan.Marcel.Janik
 * 
 */
public class ImageLoader
{
	/**
	 * The local instance of this utility class, used to only have one object of
	 * this.
	 */
	private static ImageLoader	loader;

	// <- The weight of the single color components, used in the creation of a
	// gray-image. Based on the fact, that the human eye is most sensitive in
	// the red to yellow color range. ->
	private static final float	WEIGHT_RED		= 0.299f;
	private static final float	WEIGHT_GREEN	= 0.587f;
	private static final float	WEIGHT_BLUE		= 0.114f;

	// <- Hidden constructor ->
	private ImageLoader()
	{
		// <- Temporarily do nothing ->
	}

	/**
	 * Creates a gray-image.
	 * 
	 * @param img
	 * 
	 * @return A gray-image.
	 */
	public BufferedImage createGrayImage(BufferedImage img)
	{
		return createGrayImage(img, WEIGHT_RED, WEIGHT_GREEN, WEIGHT_BLUE);
	}

	/**
	 * Creates a gray-image, with recognition of the color weighting.
	 * 
	 * @param img
	 * @param weight_red
	 * @param weight_green
	 * @param weight_blue
	 * 
	 * @return A gray-image.
	 */
	public BufferedImage createGrayImage(BufferedImage img, float weight_red, float weight_green, float weight_blue)
	{
		BufferedImage grayImage = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_BYTE_GRAY);
		//
		// byte[] pixels = ((DataBufferByte)
		// grayImage.getRaster().getDataBuffer()).getData();
		//
		// for (int i = 0; i < pixels.length; i += 4)
		// {
		// byte alpha = pixels[i];
		// byte red = (byte) (pixels[i + 1] * weight_red);
		// byte green = (byte) (pixels[i + 2] * weight_green);
		// byte blue = (byte) (pixels[i + 3] * weight_blue);
		//
		// // System.out.println("Red: " + pixels[i + 1] + " Green: " +
		// // pixels[i + 2] + " Blue: " + pixels[i + 2] + " Alpha: " +
		// // pixels[i]);
		//
		// byte gray = (byte) (alpha + red + green + blue);
		//
		// pixels[i + 1] = pixels[i + 2] = pixels[i + 3] = gray;
		// }

		Graphics g = grayImage.getGraphics();
		g.drawImage(img, 0, 0, null);
		g.dispose();

		return grayImage;
	}

	/**
	 * Create's a clone of a buffered-image.
	 * 
	 * @param img
	 * 
	 * @return A clone of <b>'img'</b>
	 */
	public BufferedImage cloneImage(BufferedImage img)
	{
		BufferedImage clone = new BufferedImage(img.getWidth(), img.getHeight(), img.getType());

		clone.getGraphics().drawImage(img, 0, 0, null);

		return clone;
	}

	// <- Loading method's ->

	/**
	 * Load's a image from an inout-stream.
	 * 
	 * @param is
	 * 
	 * @return A buffered-image.
	 * @throws IOException
	 */
	private BufferedImage load(InputStream is) throws IOException, IllegalArgumentException
	{
		return ImageIO.read(is);
	}

	/**
	 * Load's an image from inside a .jar-file.
	 * 
	 * @param path
	 * 
	 * @return A buffered-image.
	 */
	public BufferedImage load(String path)
	{
		BufferedImage img = null;

		try
		{
			img = load(this.getClass().getResourceAsStream(path));
		}
		catch (IOException | IllegalArgumentException e)
		{
			System.out.println("Failed to load Image from: " + path);

			e.printStackTrace();
		}

		return img;
	}

	/**
	 * Load's an image from inside a .jar-file and resize's it.
	 * 
	 * @param path
	 * @param size
	 * 
	 * @return A resized buffered-image.
	 */
	public BufferedImage load(String path, Dimension size)
	{
		BufferedImage img = load(path);

		return resize(img, (int) size.getWidth(), (int) size.getHeight());
	}

	/**
	 * Load's an image from inside a .jar-file and resize's it.
	 * 
	 * @param path
	 * @param width
	 * @param height
	 * 
	 * @returnA resized buffered-image.
	 */
	public BufferedImage load(String path, int width, int height)
	{
		BufferedImage img = load(path);

		return resize(img, width, height);
	}

	/**
	 * Load's an image from inside a .jar-file and resize's it.
	 * 
	 * @param path
	 * @param size
	 * 
	 * @return A resized buffered-image.
	 */
	public BufferedImage load(String path, int size)
	{
		return load(path, new Dimension(size, size));
	}

	/**
	 * Load's an image from the file-system.
	 * 
	 * @param file
	 * 
	 * @return A buffered-image.
	 */
	public BufferedImage load(File file)
	{
		BufferedImage img = null;

		try
		{
			img = load(new FileInputStream(file));
		}
		catch (IOException e)
		{
			System.out.println("Failed to load Image from: " + file.getAbsolutePath());

			e.printStackTrace();
		}

		return img;

	}

	/**
	 * Load's an image from the file-system and resize's it.
	 * 
	 * @param file
	 * @param size
	 * 
	 * @return A buffered-image.
	 */
	public BufferedImage load(File file, Dimension size)
	{
		BufferedImage img = load(file);

		return resize(img, (int) size.getWidth(), (int) size.getHeight());
	}

	/**
	 * Load's an image from an input-stream and resize's it.
	 * 
	 * @param is
	 * @param size
	 * 
	 * @return A buffered-image.
	 */
	public BufferedImage load(InputStream is, Dimension size)
	{
		BufferedImage img = null;
		try
		{
			img = load(is);
		}
		catch (IOException e)
		{
			System.out.println("ERROR, while reading or opening inputstream");
			e.printStackTrace();
		}

		return resize(img, (int) size.getWidth(), (int) size.getHeight());
	}

	/**
	 * Load's an image from the file-system and resize's it.
	 * 
	 * @param is
	 * @param size
	 * 
	 * @return A buffered-image.
	 */
	public BufferedImage load(File file, int size)
	{
		return load(file, new Dimension(size, size));
	}

	/**
	 * Load's an image from an input-stream and resize's it.
	 * 
	 * @param is
	 * @param size
	 * 
	 * @return A buffered-image.
	 */
	public BufferedImage load(InputStream is, int size)
	{
		return load(is, new Dimension(size, size));
	}

	/**
	 * Load's an icon from the .jar-file.
	 * 
	 * @param path
	 * 
	 * @return An image-icon,
	 */
	public ImageIcon loadIcon(String path)
	{
		ImageIcon icon = new ImageIcon(load(path));

		return icon;
	}

	/**
	 * Load's an icon from the .jar-file and resize's it.
	 * 
	 * @param path
	 * @param size
	 * 
	 * @return An image-icon.
	 */
	public ImageIcon loadIcon(String path, int size)
	{
		ImageIcon icon = new ImageIcon(load(path, size));

		return icon;
	}

	/**
	 * Load's an icon from the .jar-file and resize's it.
	 * 
	 * @param path
	 * @param size
	 * 
	 * @return An image-icon.
	 */
	public ImageIcon loadIcon(String path, Dimension size)
	{
		ImageIcon icon = new ImageIcon(load(path, size));

		return icon;
	}

	/**
	 * Load's an icon from the file-system
	 * 
	 * @param file
	 * 
	 * @return An image-icon.
	 */
	public ImageIcon loadIcon(File file)
	{
		ImageIcon icon = new ImageIcon(load(file));

		return icon;
	}

	/**
	 * Load's an icon from the file-system and resize's it.
	 * 
	 * @param file
	 * @param size
	 * 
	 * @return An image-icon.
	 */
	public ImageIcon loadIcon(File file, int size)
	{
		ImageIcon icon = new ImageIcon(load(file, size));

		return icon;
	}

	/**
	 * Load's an icon from the file-system and resize's it.
	 * 
	 * @param file
	 * @param size
	 * 
	 * @return An image-icon.
	 */
	public ImageIcon loadIcon(File file, Dimension size)
	{
		ImageIcon icon = new ImageIcon(load(file, size));

		return icon;
	}

	/**
	 * Load's an icon from an input-stream and create's a list of icon's, with
	 * the following icon size's (16x16,32x32,64x64,128x128).
	 * 
	 * @param img
	 * @param file
	 * 
	 * @return A list of icon-images.
	 */
	public ArrayList<BufferedImage> loadIconImages(InputStream is)
	{
		BufferedImage icon = null;
		try
		{
			icon = load(is);
		}
		catch (IOException e)
		{
			System.out.println("ERROR, while reading or opening inputstream");
			e.printStackTrace();
		}

		return createIconImages(icon);
	}

	/**
	 * Load's an icon from the .jar-file and create's a list of icon's, with the
	 * following icon size's (16x16,32x32,64x64,128x128).
	 * 
	 * @param img
	 * @param file
	 * 
	 * @return A list of icon-images.
	 */
	public ArrayList<BufferedImage> loadIconImages(String path)
	{
		BufferedImage icon = load(path);

		return createIconImages(icon);
	}

	/**
	 * Load's an icon from the file-system and create's a list of icon's, with
	 * the following icon size's (16x16,32x32,64x64,128x128).
	 * 
	 * @param img
	 * @param file
	 * 
	 * @return A list of icon-images.
	 */
	public ArrayList<BufferedImage> loadIconImages(File file)
	{
		BufferedImage icon = load(file);

		return createIconImages(icon);
	}

	/**
	 * Create's a list of icon's from an image, with the following icon size's
	 * (16x16,32x32,64x64,128x128).
	 * 
	 * @param img
	 * 
	 * @return A list of icon-images.
	 */
	private ArrayList<BufferedImage> createIconImages(BufferedImage img)
	{
		// Create a list to store the images.
		// Note: We can't use an array, because the JFrame can receive several
		// icon's with a different size, we decide to go with 16x16, 32x32,
		// 64x64, 128x128, if the size of all icon's is the same, the first one
		// in the list will be chosen.
		ArrayList<BufferedImage> list = new ArrayList<BufferedImage>(4);

		// Add the images to the list, and cleverly resize them in the right
		// order to 16x16, 32x32, 64x64, 128x128.
		for (int i = 0; i < 4; i++)
			list.add(resize(img, 16 << i));

		return list;
	}

	/**
	 * Smoothly resize's an image.
	 * 
	 * @param img
	 * @param size
	 * 
	 * @return A smooth resized image,
	 */
	public BufferedImage resize(BufferedImage img, int size)
	{
		return resize(img, size, size);
	}

	/**
	 * Smoothly resize's an image.
	 * 
	 * @param img
	 * @param width
	 * @param height
	 * 
	 * @return A smooth resized image.
	 */
	public BufferedImage resize(BufferedImage img, int width, int height)
	{
		// If the image already has the right size, return.
		if (img.getWidth() == width && img.getHeight() == height)
			return img;

		// Create a new Image in the buffer.
		BufferedImage resizedImage = null;

		// Scale the image smoothly, cost more time, but this is much much
		// better, than getting the graphic's from an image an then scale the
		// image with the draw-method.
		// [resizedImage.getGraphics().draw(img, 0,0, width, height, null);]
		Image scaledImage = img.getScaledInstance(width, height, BufferedImage.SCALE_SMOOTH);

		// Create a new image with the size we want the resized image to be.
		resizedImage = new BufferedImage(width, height, img.getType());

		// Put the data of the resized image into our buffer/storage.
		resizedImage.getGraphics().drawImage(scaledImage, 0, 0, null);

		return resizedImage;
	}

	// <- Static method's ->
	public static ImageLoader getInstance()
	{
		if (loader == null)
			loader = new ImageLoader();

		return loader;
	}
}