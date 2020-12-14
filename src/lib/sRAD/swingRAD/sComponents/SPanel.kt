package lib.sRAD.swingRAD.sComponents

import lib.sRAD.swingRAD.*
import java.awt.Color
import java.awt.LayoutManager
import javax.swing.JPanel
import javax.swing.JScrollPane
import javax.swing.JTable
import javax.swing.border.Border
import javax.swing.table.DefaultTableModel

open class SPanel: JPanel {

    constructor(x: Int=0, y: Int=0, width: Int=0, height: Int=0, background: Color? = semiDarkGrayBlue, border: Border? = semiDarkGray2Border,
                layout: LayoutManager? = null) {
        setProperties(x, y, width, height, background, border, layout)
    }

    constructor()

    fun setProperties(x: Int=0, y: Int=0, width: Int=0, height: Int=0, background: Color? = semiDarkGrayBlue, border: Border? = semiDarkGray2Border,
                             layout: LayoutManager? = null) {
        this.setBounds(x, y, width, height)
        this.background = background
        this.border = border
        this.layout = layout
    }
}