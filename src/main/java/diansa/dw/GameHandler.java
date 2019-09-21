package diansa.dw;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL3;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.util.GLBuffers;
import com.jogamp.opengl.util.glsl.ShaderCode;
import com.jogamp.opengl.util.glsl.ShaderProgram;
import diansa.dw.graphics.Screen;
import diansa.dw.util.ResourceLoader;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;

public class GameHandler implements GLEventListener {

    private final Game game;
    private final int[] vbo = new int[1];
    private final int[] vao = new int[1];
    private final ShaderProgram program = new ShaderProgram();
    private final int[] tex = new int[1];
    private IntBuffer buffer = null;

    public GameHandler(Game game) {
        this.game = game;
    }

    @Override
    public void init(GLAutoDrawable drawable) {
        GL3 gl = drawable.getGL().getGL3();
        gl.glClearColor(1f, 0f, 0f, 1f);
        gl.glGenBuffers(1, vbo, 0);
        gl.glBindBuffer(GL3.GL_ARRAY_BUFFER, vbo[0]);

        ByteBuffer texBuffer = GLBuffers.newDirectByteBuffer(1024);

        texBuffer.putFloat(-1f);
        texBuffer.putFloat(-1f);
        texBuffer.putFloat(0f);
        texBuffer.putFloat(1f);

        texBuffer.putFloat(1f);
        texBuffer.putFloat(1f);
        texBuffer.putFloat(1f);
        texBuffer.putFloat(0f);

        texBuffer.putFloat(-1f);
        texBuffer.putFloat(1f);
        texBuffer.putFloat(0f);
        texBuffer.putFloat(0f);

        texBuffer.putFloat(1f);
        texBuffer.putFloat(1f);
        texBuffer.putFloat(1f);
        texBuffer.putFloat(0f);

        texBuffer.putFloat(-1f);
        texBuffer.putFloat(-1f);
        texBuffer.putFloat(0f);
        texBuffer.putFloat(1f);

        texBuffer.putFloat(1f);
        texBuffer.putFloat(-1f);
        texBuffer.putFloat(1f);
        texBuffer.putFloat(1f);

        texBuffer.flip();

        gl.glBufferData(GL3.GL_ARRAY_BUFFER, texBuffer.limit(), texBuffer, GL3.GL_STATIC_DRAW);

        ShaderCode fragCode = new ShaderCode(GL3.GL_FRAGMENT_SHADER, 1, new String[][]{{ResourceLoader.loadString("shader/fragment.glsl")}});
        ShaderCode vertCode = new ShaderCode(GL3.GL_VERTEX_SHADER, 1, new String[][]{{ResourceLoader.loadString("shader/vertex.glsl")}});
        program.add(fragCode);
        program.add(vertCode);
        program.link(gl, System.out);
        program.useProgram(gl, true);

        gl.glGenVertexArrays(1, vao, 0);
        gl.glBindVertexArray(vao[0]);

        gl.glEnableVertexAttribArray(0);
        gl.glEnableVertexAttribArray(1);
        gl.glVertexAttribPointer(0, 2, GL3.GL_FLOAT, false, 16, 0);
        gl.glVertexAttribPointer(1, 2, GL3.GL_FLOAT, false, 16, 8);

        Screen screen = game.getScreen();

        gl.glGenTextures(1, tex, 0);
        gl.glBindTexture(GL3.GL_TEXTURE_2D, tex[0]);
        gl.glTexParameteri(GL3.GL_TEXTURE_2D, GL3.GL_TEXTURE_WRAP_S, GL3.GL_CLAMP_TO_BORDER);
        gl.glTexParameteri(GL3.GL_TEXTURE_2D, GL3.GL_TEXTURE_WRAP_T, GL3.GL_CLAMP_TO_BORDER);
        gl.glTexParameteri(GL3.GL_TEXTURE_2D, GL3.GL_TEXTURE_MIN_FILTER, GL3.GL_LINEAR);
        gl.glTexParameteri(GL3.GL_TEXTURE_2D, GL3.GL_TEXTURE_MAG_FILTER, GL3.GL_LINEAR);
        IntBuffer fakeBuffer = GLBuffers.newDirectIntBuffer(new int[screen.width * screen.height]);
        gl.glTexImage2D(GL.GL_TEXTURE_2D, 0, GL3.GL_RGBA, screen.width, screen.height, 0, GL3.GL_BGRA, GL3.GL_UNSIGNED_BYTE, fakeBuffer);

        buffer = GLBuffers.newDirectIntBuffer(screen.width * screen.height);

        gl.glEnable(GL3.GL_BLEND);
        gl.glBlendFunc(GL3.GL_SRC_ALPHA, GL3.GL_ONE_MINUS_SRC_ALPHA);
    }

    @Override
    public void display(GLAutoDrawable drawable) {
        if (buffer == null) {
            return;
        }
        GL3 gl = drawable.getGL().getGL3();
        game.run();
        Screen screen = game.getScreen();
        buffer.clear();
        buffer.put(screen.pixels);
        buffer.flip();
        gl.glTexSubImage2D(GL3.GL_TEXTURE_2D, 0, 0, 0, screen.width, screen.height, GL3.GL_BGRA, GL3.GL_UNSIGNED_BYTE, buffer);
        gl.glClear(GL3.GL_COLOR_BUFFER_BIT);
        gl.glDrawArrays(GL3.GL_TRIANGLES, 0, 6);
    }

    @Override
    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
        GL3 gl = drawable.getGL().getGL3();
        gl.glViewport(x, y, width, height);
    }

    @Override
    public void dispose(GLAutoDrawable drawable) {
        GL3 gl = drawable.getGL().getGL3();
        gl.glDeleteVertexArrays(1, vao, 0);
        program.destroy(gl);
        gl.glDeleteBuffers(1, vbo, 0);
    }
}
