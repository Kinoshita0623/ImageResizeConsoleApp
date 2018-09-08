import java.awt.image.*
import java.io.File
import java.util.*
import javax.imageio.ImageIO
import javax.swing.JFileChooser
import javax.swing.JFrame

fun main(args:Array<String>){

    val open = object : JFrame() {
        fun openFile() : File {
            val fileChooser = JFileChooser()
            fileChooser.fileSelectionMode = JFileChooser.DIRECTORIES_ONLY
            val selected = fileChooser.showOpenDialog(this)
            if(selected == JFileChooser.CANCEL_OPTION || selected == JFileChooser.ERROR_OPTION){
                System.exit(0)
            }
            return fileChooser.selectedFile

        }

    }

    val selectedFile = open.openFile()

    println("1:倍率リサイズ(%),2:横幅の画素数に合わせる,3:縦の画素数に合わせる,4:縦横リサイズ")
    val whatSize = Scanner(System.`in`)
    val whatSizeIt = whatSize.nextInt()

    println("サイズを指定")
    var width = 10.0
    var height = 10.0
    if(whatSizeIt == 4){
        println("横(Width)")
        val widthScan = Scanner(System.`in`)
        width = widthScan.nextInt().toDouble()
        println("縦(Height)")
        val heightScan = Scanner(System.`in`)
        height = heightScan.nextInt().toDouble()
    }

    val sizeScanner = Scanner(System.`in`)
    val size:Int = sizeScanner.nextInt()

    val filter = NeedFiles()
    val list = filter.imageFilter(selectedFile)

    fun editFile(file:File):BufferedImage?{
        val resize = ImageResize()
        when(whatSizeIt){
            1 -> {

                return resize.magnificationResize(file.absoluteFile,size.toDouble())
            }
            2 -> {
                return resize.widthResize(file.absoluteFile,size)
            }

            3 -> {
                return resize.pixelSizeResize(file.absoluteFile,width,height)

            }
            else -> {
                return resize.pixelSizeResize(file.absoluteFile,width,height)
            }
        }
        return null
    }

    val current:File = File(selectedFile.absolutePath + "\\resize")
    current.mkdir()
    list.forEach{ it ->
        ImageIO.write(editFile(it),"jpeg", File(selectedFile.absolutePath + "\\resize\\" + it.name))
        print("#")
    }
    System.exit(0)
}













