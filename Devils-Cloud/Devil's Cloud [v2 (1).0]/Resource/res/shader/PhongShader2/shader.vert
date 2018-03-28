#version 330
//PhongShader

layout (location = 0) in vec3 in_position;
layout (location = 1) in vec2 in_texCoord;

out vec2 texCoord0;

uniform mat4 modelTransformation;
uniform mat4 projectionMatrix;

void main()
{
	gl_Position = projectionMatrix * modelTransformation * vec4(in_position, 1.0);
	texCoord0 = in_texCoord;
}

