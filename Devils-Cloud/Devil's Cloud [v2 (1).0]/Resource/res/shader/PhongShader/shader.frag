#version 330 
//DirectionalLight

const int MAX_POINT_LIGHTS = 4;

in vec2 texCoord0;
in vec3 normal0;
in vec3 worldPos0;

struct BaseLight
{
	vec4 color;
	float intensity;
};

struct DirectionalLight
{
	BaseLight base;
	vec3 direction;
};

struct Attenuation
{
	float constant;
	float linear;
	float exponent;
};

struct PointLight
{
	BaseLight base;
	Attenuation attenuation;
	vec3 position;
	float range;
};

uniform int lighting;
uniform float lightLevel;
uniform vec3 cameraPos;
uniform vec4 baseColor;
uniform vec4 ambientLight;
uniform sampler2D sampler;

uniform float specularIntensity;
uniform float specularPower;

uniform DirectionalLight directionalLight;
uniform PointLight pointLights[MAX_POINT_LIGHTS];

vec4 calcLight(BaseLight base, vec3 direction, vec3 normal)
{
	float diffuse = dot(normal, -direction);
	
	vec4 diffuseColor = vec4(0, 0, 0, 0);
	vec4 specularColor = vec4(0, 0, 0, 0);
	
	if(diffuse > 0)
	{
		diffuseColor = base.color * base.intensity * diffuse;
		
		vec3 directionToCamara = normalize(cameraPos - worldPos0);
		vec3 reflectionDirection = normalize(reflect(direction, normal));
		
		float specularLightFactor = dot(directionToCamara, reflectionDirection);
		specularLightFactor = pow(specularLightFactor, specularPower);
		
		if(specularLightFactor > 0)
			specularColor = vec4(base.color) * specularIntensity * specularLightFactor;
	}	
	return diffuseColor + specularColor;
}

vec4 calcDirectionalLight(DirectionalLight directionalLight, vec3 normal)
{
	return calcLight(directionalLight.base, -directionalLight.direction, normal);
}

bool lightingEnabled()
{
	return lighting == 1 ? true : false;
}

vec4 calcPointLight(PointLight pointLight, vec3 normal)
{
	vec3 lightDirection = worldPos0 - pointLight.position;
	float distanceToLight = length(lightDirection);
	
	if(distanceToLight > pointLight.range)
		return vec4(0, 0, 0, 0);
		
	lightDirection = normalize(lightDirection);
		
	vec4 color = calcLight(pointLight.base, lightDirection, normal);
	
	float attenuation = pointLight.attenuation.constant + 
						pointLight.attenuation.linear * distanceToLight +
						pointLight.attenuation.exponent * distanceToLight * distanceToLight +
						0.0001;
						
	return color / attenuation;
}

void main()
{
		vec4 textureColor = texture2D(sampler, texCoord0.xy);		
		vec4 totalLight = ambientLight * lightLevel;	
		vec4 color = baseColor;
		
		if(textureColor != vec4(0, 0, 0, 0))
			color *= textureColor;	
		
		if(lightingEnabled())
		{
			//Useless, if incoming normals are already normalized.
			vec3 normal = normalize(normal0);
			
			totalLight += calcDirectionalLight(directionalLight, normal);
			
			for(int i = 0; i < MAX_POINT_LIGHTS; i++)
				if(pointLights[i].base.intensity > 0)
					totalLight += calcPointLight(pointLights[i], normal);
		}
		
		gl_FragColor = color * totalLight;
}	





