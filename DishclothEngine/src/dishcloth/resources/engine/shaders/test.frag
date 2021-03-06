#version 330 core

in vec4 color;

out vec4 fragColor;

uniform sampler2D textureSampler;

void main()
{
	fragColor = color;

	// Accept only fully opaque pixels
	if (fragColor.a < 1.0) {
		discard;
	}
}