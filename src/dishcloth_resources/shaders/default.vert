#version 330 core

layout(location = 0) in vec2 position;	// vec2 of 2 floats
layout(location = 1) in vec2 uv;		// vec2 of 2 floats

out vec2 tCoord;

void main()
{
	// Spit the position trough as-is
	gl_Position = vec4(position, 0.0, 1.0);

	tCoord = uv;
}
