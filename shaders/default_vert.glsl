#version 460 core

layout (location = 0) in vec3 aPos;
layout (location = 1) in vec2 aTexCoords;

uniform mat4 uProjection;
uniform mat4 uRot;

out vec2 fTexCoords;

void main() {
    fTexCoords = aTexCoords;
    gl_Position = uProjection * uRot * vec4(aPos.xyz, 1.0f);
}