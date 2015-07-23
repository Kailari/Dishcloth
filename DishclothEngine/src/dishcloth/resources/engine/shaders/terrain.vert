#version 330 core

layout(location = 0) in vec2 vert_position;	// vec2 of 2 floats
layout(location = 1) in vec4 vert_color;	// vec2 of 4 ubytes
layout(location = 2) in vec2 vert_uv;		// vec2 of 2 floats
layout(location = 3) in vec2 vert_uvOffset;	// vec2 of 2 floats

out vec2 tCoord;
out vec2 tileOffset;

uniform mat4 mat_project;		// Projection
uniform mat4 mat_view;			// Camera location

void main()
{
	// Apply projection and transformations
	gl_Position = mat_project * mat_view * vec4(vert_position, 0.0, 1.0);

	tCoord = vert_uv; //* tileSize;
	tileOffset = vert_uvOffset;
}
