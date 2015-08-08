#version 330 core

layout(location = 0) in vec2 position;	// vec2 of 2 floats
layout(location = 1) in vec2 uv;		// vec2 of 2 floats

out vec4 color;

uniform mat4 mat_project;		// Projection
uniform mat4 mat_view;			// Camera location

void main()
{
	// Apply projection and transformations
	gl_Position = mat_project * inverse(mat_view) * vec4(position, 0.0, 1.0);

	float corner = mod(gl_VertexID, 4);

	if (corner == -1) {
		color = vec4(0.0, 0.0, 0.0, 1.0);	// Black
	}
	else if (corner == 0) {
		color = vec4(1.0, 0.0, 0.0, 1.0);	// Red
	}
	else if (corner == 1) {
		color = vec4(0.0, 1.0, 0.0, 1.0);	// Green
	}
	else if (corner == 2) {
		color = vec4(0.0, 0.0, 1.0, 1.0);	// Blue
	}
	else if (corner == 3) {
		color = vec4(1.0, 0.0, 1.0, 1.0);	// Magenta
	}
	else {
		color = vec4(1.0, 1.0, 1.0, 1.0);
	}
}
