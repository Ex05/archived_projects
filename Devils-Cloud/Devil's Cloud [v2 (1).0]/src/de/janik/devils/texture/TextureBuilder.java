package de.janik.devils.texture;

import java.io.File;

public class TextureBuilder
{
	private int		textureTarget;
	private int		pixelFormat;
	private int		minificationFilter;
	private int		magnificationFilter;

	private boolean	mipMap;

	private int		mipMapLevel;

	private boolean	autoCreateMipMap;

	private int		wrap_S;
	private int		wrap_T;

	private String	texturePath;
	private File	textureFile;

	public TextureBuilder()
	{

	}

	public Texture loadTexture()
	{
		Texture tex = null;

		if (textureFile == null)
			tex = LWJGL_TextureLoader.getInstance().load(textureTarget, pixelFormat, minificationFilter,
					magnificationFilter, mipMap, mipMapLevel, autoCreateMipMap, wrap_S, wrap_T, texturePath);
		else
			if (texturePath == null)
				tex = LWJGL_TextureLoader.getInstance().load(textureTarget, pixelFormat, minificationFilter,
						magnificationFilter, mipMap, mipMapLevel, wrap_S, wrap_T, textureFile);

		return tex;
	}

	// <-Getter & Setter->
	public int getTextureTarget()
	{
		return textureTarget;
	}

	public void setTextureTarget(int textureTarget)
	{
		this.textureTarget = textureTarget;
	}

	public int getPixelFormat()
	{
		return pixelFormat;
	}

	public void setPixelFormat(int pixelFormat)
	{
		this.pixelFormat = pixelFormat;
	}

	public int getMinificationFilter()
	{
		return minificationFilter;
	}

	public void setMinificationFilter(int minificationFilter)
	{
		this.minificationFilter = minificationFilter;
	}

	public int getMagnificationFilter()
	{
		return magnificationFilter;
	}

	public void setMagnificationFilter(int magnificationFilter)
	{
		this.magnificationFilter = magnificationFilter;
	}

	public boolean isMipMap()
	{
		return mipMap;
	}

	public void setMipMap(boolean mipMap)
	{
		this.mipMap = mipMap;
	}

	public int getMipMapLevel()
	{
		return mipMapLevel;
	}

	public void setMipMapLevel(int mipMapLevel)
	{
		this.mipMapLevel = mipMapLevel;
	}

	public boolean isAutoCreateMipMap()
	{
		return autoCreateMipMap;
	}

	public void setAutoCreateMipMap(boolean autoCreateMipMap)
	{
		this.autoCreateMipMap = autoCreateMipMap;
	}

	public int getWrap_S()
	{
		return wrap_S;
	}

	public void setWrap_S(int wrap_S)
	{
		this.wrap_S = wrap_S;
	}

	public int getWrap_T()
	{
		return wrap_T;
	}

	public void setWrap_T(int wrap_T)
	{
		this.wrap_T = wrap_T;
	}

	public String getTexturePath()
	{
		return texturePath;
	}

	public void setTexturePath(String texturePath)
	{
		this.texturePath = texturePath;
	}

	public void setTextureFile(String file)
	{
		this.textureFile = new File(file);
	}

	public void setTextureFile(File file)
	{
		this.textureFile = file;
	}

}
