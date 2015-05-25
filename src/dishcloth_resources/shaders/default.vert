#version 330 core

layout(location = 0) in vec2 position;	// vec2 of 2 floats
layout(location = 1) in vec2 uv;		// vec2 of 2 floats

out vec2 tCoord;

uniform mat4 mat_project;		// Projection
uniform mat4 mat_view;			// Camera location
uniform mat4 mat_modelview;		// Object's world location

void main()
{
	// Apply projection and transformations
	gl_Position = mat_project * mat_modelview * mat_view * vec4(position, 0.0, 1.0);

	tCoord = uv;
}
