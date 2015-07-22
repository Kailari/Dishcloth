#version 330 core

layout(location = 0) in vec2 position;	// vec2 of 2 floats
layout(location = 1) in vec2 uv;		// vec2 of 2 floats

out vec2 tCoord;
out vec2 tileOffset;

uniform mat4 mat_project;		// Projection
uniform mat4 mat_view;			// Camera location

void main()
{
	// Apply projection and transformations
	gl_Position = mat_project * mat_view * vec4(position, 0.0, 1.0);

	tCoord = uv; //* tileSize;
	tileOffset = vec2(0.0, 0.0);
}
