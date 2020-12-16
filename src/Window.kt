import lib.sRAD.logicRAD.isDouble
import lib.sRAD.swingRAD.fontTitle
import lib.sRAD.swingRAD.sComponents.SLabel
import lib.sRAD.swingRAD.sComponents.SMenuBar
import lib.sRAD.swingRAD.sComponents.SPanel
import lib.sRAD.swingRAD.sComponents.STable
import lib.sRAD.swingRAD.setMainBar
import lib.sRAD.swingRAD.setProperties
import javax.swing.*

class Window: JFrame() {

    private val pSuperior = SPanel(50, 80, 1170, 205)
    private val tfNumMatrices = JTextField()
    private val lMessage = SLabel(40, 140, 1, 1)
    private val mTfNums = mutableListOf<JTextField>()
    private var tTabla = STable()

    init {
        createTopPanel()
        createMenuBar()

        setMainBar("Multiplicación de matrices encadenadas")
        setProperties()
    }

    private fun createTopPanel() {
        add(pSuperior)

        val lTitle = SLabel(305, 25, 700, 25, "Multiplicación de matrices encadenadas", fontTitle)
        pSuperior.add(lTitle)

        val lText = SLabel(40, 70, 1000, str = "Digite el orden de cada matriz. Por ejemplo: 3,4 4,8 8,5. Máximo 6 matrices.")
        pSuperior.add(lText)

        val lNumMatrices = SLabel(40, 105, 200, str = "Digite número de matrices:")
        pSuperior.add(lNumMatrices)

        pSuperior.add(lMessage)

        tfNumMatrices.setProperties(255, 102, 50, hAlignment = JTextField.RIGHT)
        tfNumMatrices.addActionListener { crearCampos() }
        pSuperior.add(tfNumMatrices)
    }

    private fun crearCampos() {
        while(mTfNums.isNotEmpty()) {
            pSuperior.remove(mTfNums.last())
            mTfNums.removeLast()
        }

        if(tfNumMatrices.text.isDouble()) {
            val numMatrices = tfNumMatrices.text.toDouble().toInt()
            if(numMatrices>6) {
                lMessage.setSize(400, 32)
                lMessage.text = "Cantidad máxima de matrices superada ($numMatrices)"
            }
            else if(numMatrices<2){
                lMessage.setSize(400, 32)
                lMessage.text = "Se requiere al menos dos matrices"
            }
            else {
                lMessage.setSize(1, 1)
                lMessage.text = ""
                for (i in 0 until numMatrices) {
                    val tfCampo = JTextField()
                    tfCampo.setProperties(40+120*i, 145, 100)
                    tfCampo.addActionListener { calcular() }
                    mTfNums.add(tfCampo)
                    pSuperior.add(mTfNums.last())
                }
            }
        }
        else {
            lMessage.setSize(400, 32)
            lMessage.text = "Por favor ingrese valores válidos"
        }

        repaint()
    }

    private fun calcular() {
        val resultado = evaluar(mTfNums)

        remove(tTabla)
        tTabla.setProperties(50, 310, 1170, 380, resultado, 300, 95*(mTfNums.size - 1))
        add(tTabla)
    }

    private fun createMenuBar() {
        val menuBar = SMenuBar()
        add(menuBar)

        val help = JMenu("Help")
        menuBar.add(help)

        val about = JMenuItem("About")
        about.addActionListener {
            JOptionPane.showMessageDialog(null, "" +
                    "This software has been developed by Jean Carlos Santoya Cabrera." +
                    "\nReferences:" +
                    "\n - https://porcomputador.com/", "About", JOptionPane.INFORMATION_MESSAGE
            )
        }
        help.add(about)
    }

}