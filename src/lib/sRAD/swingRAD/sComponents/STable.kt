package lib.sRAD.swingRAD.sComponents

import lib.sRAD.swingRAD.*
import java.awt.Color
import java.awt.Dimension
import javax.swing.JScrollPane
import javax.swing.JTable
import javax.swing.border.Border
import javax.swing.table.DefaultTableModel

class STable: JScrollPane {

    constructor(x: Int, y: Int, width: Int, height: Int, matriz: Array< Array<String> >, cellWidth: Int = 200, cellHeight:Int = 30) {
        setProperties(x, y, width, height, matriz, cellWidth, cellHeight)
    }

    constructor()

    fun setProperties(x: Int, y: Int, width: Int, height: Int, matriz: Array< Array<String> >, cellWidth: Int = 200, cellHeight:Int = 30) {
        val placeholdes = arrayListOf<String>()
        for(element in matriz[0])
            placeholdes.add(element)
        val cabecera: Array<String> = placeholdes.toArray(arrayOfNulls<String>(0))

        val modelo = DefaultTableModel()
        modelo.setColumnIdentifiers(cabecera)
        for (i in 1 until matriz.size) {
            modelo.addRow(arrayOf<Any>(""))

            for (j in matriz[0].indices) {
                modelo.setValueAt(matriz[i][j], i-1, j)
            }
        }

        val tabla = JTable()
        tabla.setProperties(modelo, rowHeight = cellHeight)
        tabla.autoResizeMode = JTable.AUTO_RESIZE_OFF
        tabla.rowHeight = cellHeight
        for (i in matriz[0].indices) {
            tabla.columnModel.getColumn(i).preferredWidth = cellWidth
            tabla.columnModel.getColumn(i).maxWidth = cellWidth
            tabla.columnModel.getColumn(i).minWidth = cellWidth
        }
        tabla.setSize(cellWidth*matriz[0].size, cellHeight*matriz.size)
        tabla.preferredSize = Dimension(cellWidth*matriz[0].size, cellHeight*(matriz.size-1))

        val header = tabla.tableHeader
        header.background = semiDarkGray2
        header.reorderingAllowed = false
        header.size = Dimension(cellWidth*matriz[0].size, 30)
        header.preferredSize = Dimension(cellWidth*matriz[0].size, 30)
        header.defaultRenderer = getCustomTable(semiDarkGray2, null, null, white, fontText)

        setViewportView(tabla)
        setLocation(x, y)
        setSize(width, height)

        background = semiDarkGray2
        viewport.background = semiDarkGrayBlue
        border = semiDarkGray2Border
        verticalScrollBar.setUI(getCustomScroll())
    }

}

fun JTable.getPanelBar(x: Int, y: Int, width: Int, height: Int, background: Color = semiDarkGrayBlue, border: Border? = null): JScrollPane {
    val panelScroll = JScrollPane(this)
    panelScroll.setLocation(x, y)
    panelScroll.setSize(width, height)
    panelScroll.viewport.background = background
    panelScroll.border = border
    return panelScroll
}

fun JTable.setProperties(modelo: DefaultTableModel, gridColor:Color = semiDarkGray2, rowHeight: Int = 40) {
    this.model = modelo
    this.rowHeight = rowHeight
    this.setDefaultRenderer(Any::class.java, getCustomTable())
    this.gridColor = gridColor
}
