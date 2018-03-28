package de.janik.devils.texture;

import static org.lwjgl.opengl.GL11.glBindTexture;
import static org.lwjgl.opengl.GL11.glDeleteTextures;

/**
 * This class represent's a texture/image in the graphic-card's memory.
 * 
 * @author Jan.Marcel.Janik
 * 
 */
public class Texture
{
	/**
	 * The id, used to call this object on the graphic's-card.
	 */
	private int	textureID;

	/**
	 * A texture/image representation.
	 * 
	 * @param textureID
	 */
	public Texture(int textureID)
	{
		this.textureID = textureID;
	}

	/**
	 * Bind's this texture to the current OpenGL-context.
	 */
	public void bind()
	{
		glBindTexture(LWJGL_TextureLoader.GL_TEXTURE_2D, textureID);
	}

	/**
	 * Remove's this object from the texture-loader hashtable and delet's it's
	 * id from the OpenGl-context.
	 */
	public void remove()
	{
		LWJGL_TextureLoader.getInstance().removeTexture(this);

		glDeleteTextures(textureID);
	}

	/**
	 * Release's the current texture-object (bind's zero(no texture) to the
	 * current OpenGL-context)
	 */
	public void release()
	{
		glBindTexture(LWJGL_TextureLoader.GL_TEXTURE_2D, 0);
	}

	// <-Getter & Setter->
	/**
	 * 
	 * @return The texture-ID.
	 */
	public int getID()
	{
		return textureID;
	}

	@Override
	public String toString()
	{
		return "ID: [" + textureID + "]";
	}
}
