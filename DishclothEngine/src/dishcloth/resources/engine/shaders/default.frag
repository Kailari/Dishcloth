#version 330 core

in vec2 tCoord;
in vec4 tint;

out vec4 fragColor;

uniform sampler2D textureSampler;

void main()
{
	// Set all pixels to input color
	fragColor = texture(textureSampler, tCoord) * tint;

	// Accept only fully opaque pixels
	if (fragColor.a < 1.0) {
		discard;
	}
}