#version 330 core

in vec2 tCoord;
in vec2 tileOffset;

out vec4 fragColor;

uniform sampler2D textureSampler;
uniform float tileSize;

void main()
{
	//vec2 dividedCoord = vec2(tCoord.x / tileSize, tCoord.y / tileSize);
	vec2 fractCoord = fract(tCoord);
	vec2 resultCoord = (fractCoord * tileSize) + tileOffset;

	fragColor = texture(textureSampler, resultCoord);

	// Accept only fully opaque pixels
	if (fragColor.a < 1.0) {
		discard;
	}
}