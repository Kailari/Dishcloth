#version 330 core

in vec2 tCoord;

out vec4 fragColor;

uniform sampler2D textureSampler;

void main()
{
	// Set all pixels to input color
	fragColor = texture(textureSampler, tCoord);

	// Accept only fully opaque pixels
	if (fragColor.a < 1.0) {
		discard;
	}
}