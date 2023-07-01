#version 460 core

out vec4 color;
in vec2 fTexCoords;

uniform sampler2D uTexture;


float rand(vec2 co){
    return fract(sin(dot(co, vec2(12.9898, 78.233))) * 43758.5453);
}

void main() {
//    color = vec4(rand(vec2(0.45f, 1.0f)), 0.0f, 0.0f, 1.0f);
    color = texture(uTexture, vec2(fTexCoords.x, fTexCoords.y));

}