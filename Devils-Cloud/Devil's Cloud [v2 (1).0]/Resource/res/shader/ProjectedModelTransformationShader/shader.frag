#version 330
//ProjectedModelTransformationShader

in vec4 color;

out vec4 fragColor;

void main()
{
	//fragColor = vec4(0.0 , 1.0 , 1.0, 1.0);
	fragColor = color;
}