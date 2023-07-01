import org.lwjgl.BufferUtils
import org.lwjgl.opengl.GL11.*
import org.lwjgl.opengl.GL12.GL_CLAMP_TO_EDGE
import org.lwjgl.stb.STBImage.*
import java.nio.ByteBuffer


class Texture(private val filepath: String) {
    var id: Int
        private set
    var width = BufferUtils.createIntBuffer(1)
    var height = BufferUtils.createIntBuffer(1)
    var channels = BufferUtils.createIntBuffer(1)
    var image: ByteBuffer?

    init {
        stbi_set_flip_vertically_on_load(true)
        id = glGenTextures()
        glBindTexture(GL_TEXTURE_2D, id)

        // set texture parameters
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_CLAMP_TO_EDGE) // x
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_CLAMP_TO_EDGE) // y

        // stretching
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST)
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST)

        // load image
        image = stbi_load(filepath, width, height, channels, 0)
        if (image != null) {
            glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, width[0], height[0], 0, GL_RGBA, GL_UNSIGNED_BYTE, image)
        } else {
            println("Could not load image$filepath")
        }
        stbi_image_free(image)
    }

    fun bind() {
        glBindTexture(GL_TEXTURE_2D, id)
    }

    fun unBind() {
        glBindTexture(GL_TEXTURE_2D, 0)
    }

    fun setID(ID: Int) {
        id = ID
    }

    companion object {
        var SPRITE_SHEET_WIDTH = 128
        var SPRITE_SHEET_HEIGHT = 128
        const val MAX_TEXTURES = 4
    }
}