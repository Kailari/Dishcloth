#version 330 core

layout(location = 0) in vec2 position;

void main()
{
	// Spit the position trough as-is
	gl_position = vec4(position, 0.0, 1.0);
}
