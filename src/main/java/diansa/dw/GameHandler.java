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
    private final int[] pbo = new int[2];
    private final ShaderProgram program = new ShaderProgram();
    private final int[] tex = new int[1];
    private int pboIdx = 0;
    private int pboNextIdx = 1;

    public GameHandler(Game game) {
        this.game = game;
    }

    @Override
    public void init(GLAutoDrawable drawable) {
        GL3 gl = drawable.getGL().getGL3();

        /* Setup VBO */
        gl.glGenBuffers(vbo.length, vbo, 0);
        gl.glBindBuffer(GL3.GL_ARRAY_BUFFER, vbo[0]);
        ByteBuffer texBuffer = GLBuffers.newDirectByteBuffer(1024);

        /* First triangle */
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

        /* Second triangle */
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

        /* Upload position & texture coordinates */
        gl.glBufferData(GL3.GL_ARRAY_BUFFER, texBuffer.limit(), texBuffer, GL3.GL_STATIC_DRAW);

        /* Create shaders and program */
        ShaderCode fragCode = new ShaderCode(GL3.GL_FRAGMENT_SHADER, 1, new String[][]{{ResourceLoader.loadString("shader/fragment.glsl")}});
        ShaderCode vertCode = new ShaderCode(GL3.GL_VERTEX_SHADER, 1, new String[][]{{ResourceLoader.loadString("shader/vertex.glsl")}});
        program.add(fragCode);
        program.add(vertCode);
        program.link(gl, System.out);
        program.useProgram(gl, true);

        /* Setup VAO */
        gl.glGenVertexArrays(vao.length, vao, 0);
        gl.glBindVertexArray(vao[0]);
        gl.glEnableVertexAttribArray(0);
        gl.glEnableVertexAttribArray(1);
        gl.glVertexAttribPointer(0, 2, GL3.GL_FLOAT, false, 16, 0);
        gl.glVertexAttribPointer(1, 2, GL3.GL_FLOAT, false, 16, 8);

        Screen screen = game.getScreen();

        /* Setup texture */
        gl.glGenTextures(1, tex, 0);
        gl.glBindTexture(GL3.GL_TEXTURE_2D, tex[0]);
        gl.glTexParameteri(GL3.GL_TEXTURE_2D, GL3.GL_TEXTURE_WRAP_S, GL3.GL_CLAMP_TO_BORDER);
        gl.glTexParameteri(GL3.GL_TEXTURE_2D, GL3.GL_TEXTURE_WRAP_T, GL3.GL_CLAMP_TO_BORDER);
        gl.glTexParameteri(GL3.GL_TEXTURE_2D, GL3.GL_TEXTURE_MIN_FILTER, GL3.GL_LINEAR);
        gl.glTexParameteri(GL3.GL_TEXTURE_2D, GL3.GL_TEXTURE_MAG_FILTER, GL3.GL_LINEAR);
        IntBuffer fakeBuffer = GLBuffers.newDirectIntBuffer(new int[screen.width * screen.height]);
        gl.glTexImage2D(GL.GL_TEXTURE_2D, 0, GL3.GL_RGBA, screen.width, screen.height, 0, GL3.GL_BGRA, GL3.GL_UNSIGNED_BYTE, fakeBuffer);

        /* Create PBO */
        gl.glGenBuffers(pbo.length, pbo, 0);
        for (int i = 0; i < pbo.length; i++) {
            gl.glBindBuffer(GL3.GL_PIXEL_UNPACK_BUFFER, pbo[i]);
            gl.glBufferData(GL3.GL_PIXEL_UNPACK_BUFFER, 4 * screen.width * screen.height, null, GL3.GL_STREAM_DRAW);
        }

        /* Other GL init stuff here */
        gl.glClearColor(1f, 0f, 0f, 1f);
        gl.glEnable(GL3.GL_BLEND);
        gl.glBlendFunc(GL3.GL_SRC_ALPHA, GL3.GL_ONE_MINUS_SRC_ALPHA);
        gl.setSwapInterval(1);
    }

    @Override
    public void display(GLAutoDrawable drawable) {
        Screen screen = game.getScreen();
        GL3 gl = drawable.getGL().getGL3();

        /* Update game */
        game.run();
        /* Transfer PBO 0 to texture */
        gl.glBindBuffer(GL3.GL_PIXEL_UNPACK_BUFFER, pbo[pboIdx]);
        gl.glTexSubImage2D(GL3.GL_TEXTURE_2D, 0, 0, 0, screen.width, screen.height, GL3.GL_BGRA, GL3.GL_UNSIGNED_BYTE, 0);
        /* Transfer image to PBO 1 */
        gl.glBindBuffer(GL3.GL_PIXEL_UNPACK_BUFFER, pbo[pboNextIdx]);
        gl.glBufferData(GL3.GL_PIXEL_UNPACK_BUFFER, 4 * screen.width * screen.height, null, GL3.GL_STREAM_DRAW);
        IntBuffer buffer = gl.glMapBuffer(GL3.GL_PIXEL_UNPACK_BUFFER, GL3.GL_WRITE_ONLY).asIntBuffer();
        buffer.put(screen.pixels);
        buffer.flip();
        gl.glUnmapBuffer(GL3.GL_PIXEL_UNPACK_BUFFER);
        gl.glBindBuffer(GL3.GL_PIXEL_UNPACK_BUFFER, 0);
        /* Swap PBO */
        pboIdx = (pboIdx + 1) % pbo.length;
        pboNextIdx = (pboIdx + 1) % pbo.length;

        /* Draw scene */
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
        gl.glDeleteVertexArrays(vao.length, vao, 0);
        program.destroy(gl);
        gl.glDeleteBuffers(vbo.length, vbo, 0);
        gl.glDeleteBuffers(pbo.length, pbo, 0);
    }
}
