import org.lwjgl.opengl.GL20.*

object Shader {
    const val defaultVertPath: String = "shaders\\default_vert.glsl"
    const val defaultFragPath: String = "shaders\\default_frag.glsl"


    const val POS_SIZE = 3
    const val TEX_SIZE = 2
    const val VERTEX_SIZE = POS_SIZE + TEX_SIZE

    private fun compileShader(src: String, type: Int): Int {
        val id: Int = glCreateShader(type)

        glShaderSource(id, src)
        glCompileShader(id)

        if (glGetShaderi(id, GL_COMPILE_STATUS) == GL_FALSE) {
             println(glGetShaderInfoLog(id))
        }

        return id
    }

    fun createShaders(f: String, v: String): Int {
        var id: Int = glCreateProgram()

        var vert: Int = compileShader(readFile(v), GL_VERTEX_SHADER)
        var frag: Int = compileShader(readFile(f), GL_FRAGMENT_SHADER)

        glAttachShader(id, vert)
        glAttachShader(id, frag)

        glLinkProgram(id)
        glValidateProgram(id)

        glDeleteShader(vert)
        glDeleteShader(frag)

        return id
    }
}