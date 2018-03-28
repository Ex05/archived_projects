#version 330
//PhongShader

in vec2 texCoord0;

uniform vec4 baseColor;
uniform vec3 ambientLight;
uniform sampler2D sampler;

void main()
{
	vec4 textureColor = texture2D(sampler, texCoord0.xy);
	
	vec4 totalLight = vec4(ambientLight, 1);

	vec4 color = baseColor;

	if(textureColor != vec4(0, 0, 0, 0))
		color *= textureColor;	
	
	gl_FragColor = color * totalLight;		
}