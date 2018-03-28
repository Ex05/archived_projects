#version 330
//DirectionalLight

layout (location = 0) in vec3 in_position;
layout (location = 1) in vec2 in_texCoord;
layout (location = 2) in vec3 in_normal;

out vec2 texCoord0;
out vec3 normal0;
out vec3 worldPos0;

uniform mat4 modelTransformation;
uniform mat4 projectionMatrix;

void main()
{
	gl_Position = projectionMatrix * modelTransformation * vec4(in_position, 1.0);	
	normal0 = (modelTransformation * vec4(in_normal, 0.0)).xyz;	
	worldPos0 = (modelTransformation * vec4(in_normal, 1.0)).xyz;	
	texCoord0 = in_texCoord;
}

