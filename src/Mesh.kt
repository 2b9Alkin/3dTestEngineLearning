import org.lwjgl.PointerBuffer
import org.lwjgl.assimp.AIMesh
import org.lwjgl.assimp.AIScene
import org.lwjgl.assimp.AIVector3D
import org.lwjgl.assimp.Assimp
import java.io.File

class Mesh {
    var vertices: MutableList<Float>
    var indices: MutableList<Int>
    var texIndices: MutableList<Int>

    var positions: MutableList<Float>
    var texCoords: MutableList<Float>

    init {
        vertices = mutableListOf()
        indices = mutableListOf()
        texIndices = mutableListOf()
        positions = mutableListOf()
        texCoords = mutableListOf()
    }

    fun loadMesh(filepath: String) {
        val objFile = File(filepath)

        objFile.forEachLine { line ->
            if (line.startsWith("v ")) {
                val vertexComponents = line.substring(2).trim().split(" ")
                for (component in vertexComponents) {
                    positions.add(component.toFloat() - 1)
                }
            }
            if (line.startsWith("vt ")) {
                val vertexComponents = line.substring(2).trim().split(" ")
                for (component in vertexComponents) {
                    texCoords.add(component.toFloat())
                }
            }
            if (line.startsWith("f ")) {
                val indexComponents = line.substring(2).trim().split(" ")
                for (component in indexComponents) {
                    val indicesComponents = component.split("/")

                    indices.add(indicesComponents[0].toInt() - 1)
                    texIndices.add(indicesComponents[1].toInt() - 1)
                }
            }
        }



        var t = 0
        for (v in 0 until (positions.size / Shader.POS_SIZE)) {
            for (pos in 0 until Shader.POS_SIZE) {
                vertices.add(positions[pos + (v * Shader.POS_SIZE)])
            }

            for (tex in 0 until Shader.TEX_SIZE) {
                val texIndex = texIndices[t]  // Subtract 1 to convert to 0-based index
                vertices.add(texCoords[(texIndex * Shader.TEX_SIZE) + tex])
                t++
            }

        }

        println(indices)
    }
}

