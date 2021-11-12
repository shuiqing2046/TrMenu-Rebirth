package me.arasple.mc.trmenu.api.action.impl.func

import me.arasple.mc.trmenu.api.action.base.AbstractAction
import me.arasple.mc.trmenu.api.action.base.ActionOption
import me.arasple.mc.trmenu.util.bukkit.ItemHelper
import org.bukkit.Material
import org.bukkit.enchantments.Enchantment
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.EnchantmentStorageMeta
import taboolib.library.xseries.XEnchantment

/**
 * @author Rubenicos
 * @date 2021/11/09 17:46
 */
class ActionEnchantItem(content: String, option: ActionOption) : AbstractAction(content, option) {

    override fun onExecute(player: Player, placeholderPlayer: Player) {
        parseContentSplited(placeholderPlayer, ";").forEach {
            val split = it.split(",", limit = 4).toTypedArray()
            if (split.size >= 3) {
                val l = split[2].split("-").toTypedArray()
                val level = if (l.size > 1) ((l[0].toIntOrNull()?: 0)..(l[1].toIntOrNull()?: 0)).random() else l[0].toIntOrNull()?: 0
                if (level > 0) {
                    if (split[1].startsWith("$ ")) {
                        enchant(ItemHelper.fromPlayerInv(player.inventory, split[0]), split[1].substring(2), level, if (split.size > 3) split[3] else "0")
                    } else {
                        val enchant = XEnchantment.matchXEnchantment(split[1])
                        if (enchant.isPresent) {
                            enchant(ItemHelper.fromPlayerInv(player.inventory, split[0]), enchant.get().parseEnchantment(), level)
                        }
                    }
                }
            }
        }
    }

    companion object {

        private val name = "enchant(-)?items?".toRegex()

        private val parser: (Any, ActionOption) -> AbstractAction = { value, option ->
            ActionEnchantItem(value.toString(), option)
        }

        val registery = name to parser

        fun enchant(any: Any?, customEnchant: String, level: Int, lineIndex: String = "0") {
            if (any is Array<*>) {
                any.forEach { enchantItem(it as ItemStack?, customEnchant, level, lineIndex) }
            } else if (any is ItemStack) enchantItem(any, customEnchant, level, lineIndex)
        }

        fun enchant(any: Any?, enchant: Enchantment?, level: Int) {
            if (any is Array<*>) {
                any.forEach { enchantItem(it as ItemStack?, enchant, level) }
            } else if (any is ItemStack) enchantItem(any, enchant, level)
        }

        fun enchantItem(item: ItemStack?, customEnchant: String, level: Int, lineIndex: String = "0") {
            if (item != null) {
                val meta = item.itemMeta!!
                val line = "$customEnchant " + when(level) {
                    1 -> "I"
                    2 -> "II"
                    3 -> "III"
                    4 -> "IV"
                    5 -> "V"
                    6 -> "VI"
                    7 -> "VII"
                    8 -> "VIII"
                    9 -> "IX"
                    10 -> "X"
                    else -> level.toString()
                }
                val lore = meta.lore?: arrayListOf<String>()
                if (lineIndex.equals("last", true)) {
                    lore.add(line)
                } else {
                    val index = lineIndex.toIntOrNull()?: 0
                    lore.add(if (lore.size >= index) index else 0, line)
                }
                meta.lore = lore
                item.itemMeta = meta
            }
        }

        fun enchantItem(item: ItemStack?, enchant: Enchantment?, level: Int) {
            if (item != null && enchant != null) {
                if (item.type == Material.BOOK) {
                    item.type = Material.ENCHANTED_BOOK
                }
                if (item.hasItemMeta()) {
                    val meta = item.itemMeta!!
                    if (meta is EnchantmentStorageMeta) {
                        meta.addStoredEnchant(enchant, level, true)
                    } else {
                        meta.addEnchant(enchant, level, true)
                    }
                    item.itemMeta = meta
                } else {
                    item.addUnsafeEnchantment(enchant, level)
                }
            }
        }
    }
}