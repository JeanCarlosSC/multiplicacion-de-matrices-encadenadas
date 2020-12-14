package lib.sRAD.swingRAD.sComponents

import lib.sRAD.swingRAD.darkWhite
import lib.sRAD.swingRAD.semiDarkGrayBlue
import lib.sRAD.swingRAD.semiDarkGrayBlueBorder
import javax.swing.JMenuBar
import java.awt.Graphics
import javax.swing.JMenu
import javax.swing.JPopupMenu
import javax.swing.JMenuItem
import java.awt.Color
import java.awt.Component
import javax.swing.border.Border

class SMenuBar (x: Int=0, y: Int=29, width: Int=1280, height: Int=28, background: Color= semiDarkGrayBlue, foreground: Color = darkWhite, border: Border = semiDarkGrayBlueBorder
) : JMenuBar() {

    private var generalBackground = background
    private var generalForeground = foreground
    private var generalBorder = border

    init {
        this.background = generalBackground
        this.border = generalBorder
        this.setBounds(x, y, width, height)
    }

    override fun paintComponent(g: Graphics) {
        super.paintComponent(g)
        for (menuElement in subElements) {
            val menu = menuElement.component as JMenu
            changeComponentColors(menu)
            menu.isOpaque = true
            val menuElements = menu.subElements
            for (popupMenuElement in menuElements) {
                val popupMenu = popupMenuElement.component as JPopupMenu
                popupMenu.border = null
                val menuItems = popupMenuElement.subElements
                for (menuItemElement in menuItems) {
                    val menuItem = menuItemElement.component as JMenuItem
                    changeComponentColors(menuItem)
                    menuItem.isOpaque = true
                }
            }
        }
    }

    private fun changeComponentColors(comp: Component) {
        comp.background = generalBackground
        comp.foreground = generalForeground
    }

}