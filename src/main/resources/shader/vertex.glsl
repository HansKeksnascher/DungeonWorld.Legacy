#version 330 core

layout(location = 0) in vec2 pos;
layout(location = 1) in vec2 texCoord;
out vec2 texCoordFrag;

void main() {
    gl_Position = vec4(pos, 1.0, 1.0);
    texCoordFrag = texCoord;
}