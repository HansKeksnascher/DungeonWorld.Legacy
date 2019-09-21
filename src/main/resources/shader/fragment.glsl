#version 330 core

precision highp float;

uniform sampler2D tex;
in vec2 texCoordFrag;
out vec4 fragColor;

void main() {
    fragColor = texture(tex, texCoordFrag);
}
