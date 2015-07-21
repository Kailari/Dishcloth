#version 330 core

layout(location = 0) in vec2 position;	// vec2 of 2 floats
layout(location = 1) in vec2 uv;		// vec2 of 2 floats

out vec2 tCoord;
out vec2 tileOffset;
out vec2 tileSize;

uniform mat4 mat_project;		// Projection
uniform mat4 mat_view;			// Camera location
uniform mat4 mat_modelview;		// Object's world location

uniform vec4 subtexture;

void main()
{
	// Apply projection and transformations
	gl_Position = mat_project * mat_view * mat_modelview * vec4(position, 0.0, 1.0);

	tCoord = subtexture.xy + (uv * subtexture.zw);
	tileOffset = subtexture.xy;
	tileSize = subtexture.zw;
}
