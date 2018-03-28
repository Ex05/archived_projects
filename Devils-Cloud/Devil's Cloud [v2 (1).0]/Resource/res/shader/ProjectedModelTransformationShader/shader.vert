#version 330
//ProjectedModelTransformationShader

layout (location = 0) in vec3 in_position;

out vec4 color;

uniform mat4 modelTransformation;
uniform mat4 projectionMatrix;

void main()
{
	color = vec4(clamp(in_position, 0.0, 1.0), 1.0);
	gl_Position = projectionMatrix * modelTransformation * vec4(in_position, 1.0);
}

