import org.joml.Matrix4f
import org.lwjgl.BufferUtils
import org.lwjgl.opengl.GL11
import org.lwjgl.opengl.GL20
import org.lwjgl.opengl.GL20.*
import java.nio.FloatBuffer
import java.util.*

class GameScene: Scene() {
    var test: BufferObject
    var tex: Texture

    // Set up the rotation angles
    val angleX = Math.toRadians(45.0).toFloat()
    val angleZ = Math.toRadians(0.0).toFloat()
    val angleY = Math.toRadians(45.0).toFloat()

    // Create the rotation matrix
    val rotationMatrix = Matrix4f()

    val uProjection: Matrix4f
    val m: Mesh = Mesh()

    init {
        m.loadMesh("test.obj")
        tex = Texture("test.png")

        glUniform1i(glGetUniformLocation(shaderId, "uTexture"), tex.id)


        test = BufferObject(m.vertices, m.indices)

        rotationMatrix.rotateXYZ(angleY, angleX, angleZ)

        uProjection = Matrix4f()
        uProjection.ortho(-720.0f, 720.0f, -480.0f, 480.0f, 1000.0f, -1000.0f)
    }

    override fun render() {
        glUseProgram(shaderId)


//        println(Arrays.toString(m.indices.toIntArray()))
        glDrawElements(GL_TRIANGLES, 15, GL_UNSIGNED_INT, 0)

    }

    override fun run() {
        render()
        update()
    }

    override fun update() {
        // Get the current view matrix
        val viewBuffer = BufferUtils.createFloatBuffer(16)
        glGetFloatv(GL11.GL_MODELVIEW_MATRIX, viewBuffer)
        val viewMatrix = Matrix4f()
        viewMatrix.set(viewBuffer)

        // Multiply the view matrix by the rotation matrix
        viewMatrix.mul(rotationMatrix)

        // Load the updated view matrix to the shader
        val projectionLocation = glGetUniformLocation(shaderId, "uProjection")
        val projMatBuffer: FloatBuffer = BufferUtils.createFloatBuffer(16)
        uProjection.get(projMatBuffer)
        glUniformMatrix4fv(projectionLocation, false, projMatBuffer)

        val rotLocation = glGetUniformLocation(shaderId, "uRot")
        glUniformMatrix4fv(rotLocation, false, viewMatrix.get(viewBuffer))

        // Clear the view buffer and flip it
        viewBuffer.clear()
        viewBuffer.flip()
    }
}