package de.janik.devils.entity.model.objloader;

public enum MTL_Token
{
	UNDEFINED(""), NEW_MATERIAL("newmtl"), TEXTURE_MAP("map_kd");
	private final String	name;

	MTL_Token(String token)
	{
		this.name = token;
	}

	public String getName()
	{
		return name;
	}
}