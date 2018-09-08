import java.awt.Graphics2D
import java.awt.Toolkit
import java.awt.image.*
import java.io.File
import java.io.FilenameFilter
import javax.imageio.ImageIO

class ImageResize{
    private fun resize(img: BufferedImage, width: Int, height: Int): BufferedImage {
        //val img: BufferedImage = ImageIO.read(file)
        val filter: ImageFilter = AreaAveragingScaleFilter(width,height)

        val produce: ImageProducer = FilteredImageSource(img.source, filter)

        val dstImage = Toolkit.getDefaultToolkit().createImage(produce)

        val dst = BufferedImage(dstImage.getWidth(null), dstImage.getHeight(null), BufferedImage.TYPE_INT_RGB)
        val graphic: Graphics2D = dst.createGraphics()
        graphic.drawImage(dstImage,0,0,null)
        graphic.dispose()
        return dst
    }
    fun pixelSizeResize(file: File, widthSize:Double, heightSize:Double):BufferedImage{
        val img: BufferedImage = ImageIO.read(file)
        val nowWidthSize:Double = img.width.toDouble()
        val nowHeightSize:Double = img.height.toDouble()

        val widthScale = widthSize / nowWidthSize
        val heightScale = heightSize / nowHeightSize

        return resize(img,(nowWidthSize * widthScale).toInt(), (nowHeightSize * heightScale).toInt())


    }
    fun magnificationResize(file: File, magnification:Double): BufferedImage {//Height

        val img: BufferedImage = ImageIO.read(file)
        val width = img.width
        val height = img.height

        val widthScale = width * ( magnification / 100)
        val heightScale = height * (magnification / 100)
        return resize(img,widthScale.toInt(), heightScale.toInt())


    }
    fun widthResize(file: File, width:Int):BufferedImage{
        val img: BufferedImage = ImageIO.read(file)
        val nowWidthSize:Double = img.width.toDouble()
        val nowHeightSize:Double = img.height.toDouble()

        val scale = width.toDouble() / nowWidthSize
        return resize(img, width, (nowHeightSize * scale).toInt())

    }
}

class NeedFiles{
    fun imageFilter(file:File):Array<File>{
        val filter = FilenameFilter { dir, filename ->
            if(filename == null){
                return@FilenameFilter false
            }
            var point:Int = filename.lastIndexOf(".")
            if(point != -1){
                val text = filename.substring(point + 1).toUpperCase()
                text.toUpperCase()
                if(text ==  "JPG" ||  text == "PNG" || text == "GIF")
                    return@FilenameFilter true
            }
            return@FilenameFilter false
        }
        return file.listFiles(filter)
    }
}