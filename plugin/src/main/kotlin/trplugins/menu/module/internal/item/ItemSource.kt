package trplugins.menu.module.internal.item

import trplugins.menu.module.display.MenuSession
import trplugins.menu.module.internal.hook.HookPlugin
import trplugins.menu.module.internal.hook.impl.HookSkulls
import trplugins.menu.module.internal.script.js.JavaScriptAgent
import org.bukkit.inventory.ItemStack
import trplugins.menu.api.event.CustomItemSourceEvent
import trplugins.menu.module.internal.script.asItemStack

/**
 * @author Arasple Super_chen520
 * @date 2023/8/18
 */
object ItemSource {

    fun fromSource(session: MenuSession, string: String): ItemStack? {
        val identifier = string.split(":", "=", limit = 2)
        val name = identifier[0].replace("-", "").uppercase()
        val id = identifier[1]

        return when (name) {
            "HEADDATABASE", "HDB" -> {
                if (id.equals("RANDOM", true)) HookPlugin.getHeadDatabase().getRandomHead()
                else HookPlugin.getHeadDatabase().getHead(id)
            }
            "SKULLS" -> {
                if (id.equals("RANDOM", true)) HookPlugin[HookSkulls::class.java].getRandomSkull()
                else HookPlugin[HookSkulls::class.java].getSkull(id)
            }
            "JAVASCRIPT", "JS" -> JavaScriptAgent.eval(session, id).asItemStack()
            "ITEMSADDER", "IA" -> HookPlugin.getItemsAdder().getItem(id)
            "ZAPHKIEL", "ZL" -> HookPlugin.getZaphkiel().getItem(id)
            else -> CustomItemSourceEvent(name, id, session).also { it.call() }.source
        }
    }

}