#version 330
//ModelTransformationShader

layout (location = 0) in vec3 in_position;

out vec4 color;

uniform mat4 modelTransformation;

void main()
{
	color = modelTransformation * vec4(clamp(in_position, 0.0, 1.0), 1.0);
	gl_Position = modelTransformation * vec4(in_position, 1.0);
}
