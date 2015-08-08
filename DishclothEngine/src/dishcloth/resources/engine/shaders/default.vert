#version 330 core

layout(location = 0) in vec2 vert_position;	// vec2 of 2 floats
layout(location = 1) in vec4 vert_tint;		// vec2 of 4 ubyte
layout(location = 2) in vec2 vert_uv;		// vec2 of 2 floats

out vec2 tCoord;
out vec4 tint;

uniform mat4 mat_project;		// Projection
uniform mat4 mat_view;			// Camera location
//uniform mat4 mat_modelview;		// Object's world location

void main()
{
	// Apply projection and transformations
	//gl_Position = mat_project * mat_view * mat_modelview * vec4(position, 0.0, 1.0);
	gl_Position = mat_project * inverse(mat_view) * vec4(vert_position, 0.0, 1.0);

	tCoord = vert_uv;
	tint = vert_tint;
}
