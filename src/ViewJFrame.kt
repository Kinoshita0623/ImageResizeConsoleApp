import java.awt.BorderLayout
import java.awt.Label
import java.awt.event.ActionEvent
import java.awt.event.ActionListener
import java.io.File
import javax.swing.*

class ViewJFrame : JFrame{
    private val resize = ResizeController()
    constructor(){

        title = "ImageResize"
        setSize(250,300)
        defaultCloseOperation = JFrame.EXIT_ON_CLOSE




        val radio1 = JRadioButton()
        radio1.text = "倍率（％）"

        val radio2 = JRadioButton()
        radio2.text = "横幅（pixel）"

        val radioGroup = ButtonGroup().apply{
            add(radio1)
            add(radio2)
        }


        val label = Label("サイズ値")

        val fileButton = JButton("フォルダを選択")

        val text = JTextField()
        text.columns = 10
        val bar = JProgressBar(0,100)

        val panel = JPanel().apply{
            add(radio1)
            add(radio2)
            add(label)
            add(text)
            add(fileButton)
            add(bar)
        }

        val contentPane = contentPane
        contentPane.add(panel, BorderLayout.CENTER)

        fileButton.addActionListener {

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
            val inputSize = text.text
            when{
                radio1.isSelected -> {
                    resize.size = inputSize.toInt()
                    resize.whatSizeIt(Size.MAGNIFICATION)
                }
                radio2.isSelected -> {
                    resize.width = inputSize.toDouble()
                    resize.whatSizeIt(Size.WIDTH)
                }
            }


            resize.resize(open.openFile())
            System.exit(0)


        }
    }

}