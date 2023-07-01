import org.lwjgl.glfw.GLFW.*
import org.lwjgl.opengl.GL
import org.lwjgl.opengl.GL11.*


class Window {

    var window: Long = 0
    lateinit var gameScene: GameScene

    companion object {
        var width: Int = 720
        var height: Int = 480
        var fps: Int = 60
        var title = "Game"
    }

    fun createWindow() {
        glfwInit()

        glfwWindowHint(GLFW_DEPTH_BITS, 24) // Specify 24 bits for the depth buffer

        window = glfwCreateWindow(width, height, title, 0, 0)

        glfwMakeContextCurrent(window)
        GL.createCapabilities()

        gameScene = GameScene()
    }


    fun render() {
        glEnable(GL_DEPTH_TEST)

        // Clear color buffer and depth buffer
        glClearColor(0.0f, 0.0f, 0.0f, 1.0f)
        glClear(GL_COLOR_BUFFER_BIT or GL_DEPTH_BUFFER_BIT)

        gameScene.run()

        glfwSwapBuffers(window)
    }


    fun update() {
        glfwPollEvents()
    }

    fun start() {
        // game loop
        // game loop
        val drwInterval: Float = 1000000000.0f / fps
        var delta = 0.0
        var lastTime = System.nanoTime()
        var currentTime: Long
        var timer: Long = 0
        var drawCount = 0

        // game loop (updates fps and graphics)

        // game loop (updates fps and graphics)
        while (!glfwWindowShouldClose(window)) {
            currentTime = System.nanoTime()
            delta += (currentTime - lastTime) / drwInterval
            timer += currentTime - lastTime
            lastTime = currentTime
            if (delta >= 1) {
                update()
                render()
                delta--
                drawCount++
            }
            if (timer >= 1000000000) {
                println("FPS:$drawCount")
                drawCount = 0
                timer = 0
            }
        }
    }
}