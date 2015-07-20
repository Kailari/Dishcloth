#version 330 core

in vec2 tCoord;
in vec2 tileOffset;
in vec2 tileSize;

out vec4 fragColor;

uniform sampler2D textureSampler;

uniform vec4 color_tint;

void main()
{
	vec2 dividedCoord = vec2(tCoord.x / tileSize.x, tCoord.y / tileSize.y);
	vec2 fractCoord = fract(dividedCoord);
	vec2 resultCoord = vec2(fractCoord.x * tileSize.x, fractCoord.y * tileSize.y) + tileOffset;

	// Set all pixels to input color
	fragColor = texture(textureSampler, resultCoord) * color_tint;

	// Accept only fully opaque pixels
	if (fragColor.a < 1.0) {
		discard;
	}
}