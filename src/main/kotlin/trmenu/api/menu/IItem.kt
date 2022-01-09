package trmenu.api.menu

import trmenu.module.display.MenuSession
import trmenu.module.display.item.Item
import trmenu.module.display.item.Meta
import trmenu.module.display.texture.Texture
import org.bukkit.inventory.ItemStack
import taboolib.platform.util.buildItem
import taboolib.platform.util.isAir

/**
 * @author Arasple
 * @date 2021/1/25 13:00
 * @see Item
 */
interface IItem {

    /**
     * 生成材质物品
     */
    fun generate(session: MenuSession, texture: Texture, name: String?, lore: List<String>?, meta: Meta): ItemStack {
        val item = texture.generate(session)

        if (item.isAir) {
            return item
        }

        val itemStack = buildItem(item) {
            if (item.itemMeta != null) {
                name?.let { this.name = it }
                lore?.let { this.lore.addAll(it) }
            }
            meta.flags(this)
            meta.shiny(session, this)

            if (meta.hasAmount()) this.amount = meta.amount(session)
        }

        meta.nbt(session, itemStack)?.run {
            itemStack.itemMeta = this
        }

        return itemStack
    }

    /**
     * 更新材质
     */
    fun updateTexture(session: MenuSession)

    /**
     * 更新物品显示名称
     */
    fun updateName(session: MenuSession)

    /**
     * 更新物品显示 Lore
     */
    fun updateLore(session: MenuSession)

}