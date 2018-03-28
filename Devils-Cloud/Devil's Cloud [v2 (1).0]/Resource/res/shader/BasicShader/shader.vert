#version 330
//BasicShader

layout (location = 0) in vec3 in_position;

out vec4 color;

void main()
{
	color = vec4(clamp(in_position, 0.0, 1.0), 1.0);
	gl_Position = vec4(in_position * 0.5, 1.0);
}