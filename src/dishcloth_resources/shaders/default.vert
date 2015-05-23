#version 330 core

layout(location = 0) in vec2 position;	// vec2 of 2 floats
layout(location = 1) in float brightness;
layout(location = 2) in vec4 color;		// vec4 of 4 normalized bytes

out vec4 vColor;

void main()
{
	// Pass the color trough
	vColor = color; // * brightness;

	// Spit the position trough as-is
	gl_Position = vec4(position, 0.0, 1.0);
}
