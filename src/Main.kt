import java.awt.image.*
import java.io.File
import java.util.*
import javax.imageio.ImageIO
import javax.swing.JFileChooser
import javax.swing.JFrame
import kotlin.coroutines.experimental.coroutineContext

fun main(args:Array<String>){

    println("ファイル名を含む絶対パスを入力してください")


    var selected:Int = 0
    val open = object : JFrame() {
        fun openFile() : File{
            val fileChooser = JFileChooser()
            fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY)
            selected = fileChooser.showOpenDialog(this)
            return fileChooser.selectedFile
        }

    }
    val selectedFile = open.openFile()

    /*val scanner = Scanner(System.`in`)
    val passInput:String = scanner.nextLine()*/

    //println("PASS $passInput")
    println("1:倍率リサイズ(%),2:横幅の画素数に合わせる,3:縦の画素数に合わせる,4:縦横リサイズ")
    val whatSize = Scanner(System.`in`)
    val whatSizeIt = whatSize.nextInt()

    println("サイズを指定")
    var width = 10.0
    var height = 10.0
    if(whatSizeIt == 4){
        println("横(Width)")
        val widthScan = Scanner(System.`in`)
        width = widthScan.nextInt() as Double
        println("縦(Height)")
        val heightScan = Scanner(System.`in`)
        height = heightScan.nextInt() as Double
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
    var count:Int = 0
    for(file in list){
        ImageIO.write(editFile(file),"jpeg", File(selectedFile.absolutePath + "\\resize" + "\\" + file.name))
        print("#")
        count ++
    }
}













