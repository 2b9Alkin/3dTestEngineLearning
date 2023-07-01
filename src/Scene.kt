abstract class Scene {
    private var mainShader: Shader = Shader
        get() = field

    val shaderId: Int

    init {
        shaderId = mainShader.createShaders(Shader.defaultFragPath, Shader.defaultVertPath)
    }

    
    abstract fun render()
    abstract fun run()
    abstract fun update()
}