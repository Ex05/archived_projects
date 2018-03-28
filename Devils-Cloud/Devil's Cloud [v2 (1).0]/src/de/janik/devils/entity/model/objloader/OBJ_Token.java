package de.janik.devils.entity.model.objloader;

public enum OBJ_Token
{
	UNDEFINED(""), COMMENT("#"), GROUP("g"), OBJECT("o"), VECTOR("v"), TEXTURE_COORDINATE("vt"), NORMAL("vn"), FACE("f"), MATERIAL_LIB("mtllib"),USE_MATERIAL("usemtl");

	private final String	name;

	OBJ_Token(String token)
	{
		this.name = token;
	}

	public String getName()
	{
		return name;
	}
}