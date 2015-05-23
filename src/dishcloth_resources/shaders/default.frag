#version 330 core

in vec4 vColor;

out vec4 fragColor;

void main()
{
	// Set all pixels to input color
	fragColor = vColor;
}