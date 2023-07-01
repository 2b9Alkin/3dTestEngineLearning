import org.lwjgl.BufferUtils
import org.lwjgl.opengl.GL15
import org.lwjgl.opengl.GL20
import org.lwjgl.opengl.GL30.*

class BufferObject(vertices: MutableList<Float>, indices: MutableList<Int>) {
    class Vbo(vertices: MutableList<Float>) {
        private var vbo: Int = 0
            get() = field

        private var vertices: MutableList<Float>

        init {
            this.vertices = vertices
            vbo = glGenBuffers()
            bindVbo()
            sendData()
        }


        fun bindVbo() {
            glBindBuffer(GL_ARRAY_BUFFER, vbo)
        }

        fun sendData() {
            val verticesArray: FloatArray = vertices.toFloatArray()
            glBufferData(GL_ARRAY_BUFFER, verticesArray, GL_DYNAMIC_DRAW)
        }
    }

    class Vao {
        private var vao: Int = 0
            get() = field

        init {
            vao = glGenVertexArrays()
            bindVao()

            setAttributePointers()
        }

        private fun setAttributePointers() {
            val sizeInBytes = Shader.VERTEX_SIZE * Float.SIZE_BYTES

            glEnableVertexAttribArray(0)
            glVertexAttribPointer(0, Shader.POS_SIZE, GL_FLOAT, false, sizeInBytes, 0)

            glEnableVertexAttribArray(1)
            glVertexAttribPointer(1, Shader.TEX_SIZE, GL_FLOAT, false, sizeInBytes, (Shader.POS_SIZE * Float.SIZE_BYTES).toLong())
        }

        fun bindVao() {
            glBindVertexArray(vao)
        }
    }

    class Ebo(indices: MutableList<Int>) {
        private var ebo: Int = 0
            get() = field

        private var indices: MutableList<Int>

        init {
            this.indices = indices

            ebo = glGenBuffers()
            bindEbo()
            sendData()
        }


        fun bindEbo() {
            glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, ebo)
        }

        fun sendData() {
            val indicesArray: IntArray = indices.toIntArray()
            val indicesBuffer = BufferUtils.createIntBuffer(indicesArray.size)
            indicesBuffer.put(indicesArray)
            indicesBuffer.flip()
            glBufferData(GL_ELEMENT_ARRAY_BUFFER, indicesBuffer, GL_STATIC_DRAW)
        }
    }

    var vbo: Vbo
    var vao: Vao
    val ebo: Ebo

    init {
        vbo = Vbo(vertices)
        vao = Vao()
        ebo = Ebo(indices)
    }
}