#version 330 core

in vec2 tCoord;

out vec4 fragColor;

uniform sampler2D textureSampler;

uniform vec4 color_tint;

void main()
{
	// Set all pixels to input color
	fragColor = texture(textureSampler, tCoord) * color_tint;

	// Accept only fully opaque pixels
	if (fragColor.a < 1.0) {
		discard;
	}
}