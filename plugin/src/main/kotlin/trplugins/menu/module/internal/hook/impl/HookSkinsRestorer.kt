package trplugins.menu.module.internal.hook.impl

import net.skinsrestorer.api.SkinsRestorer
import net.skinsrestorer.api.exception.DataRequestException;
import net.skinsrestorer.api.exception.MineSkinException;
import net.skinsrestorer.api.SkinsRestorerProvider
import net.skinsrestorer.api.storage.PlayerStorage
import net.skinsrestorer.api.storage.SkinStorage
import trplugins.menu.module.internal.hook.HookAbstract

/**
 * @author AoMe
 * @date 2023/11/02 14:12
 */
class HookSkinsRestorer : HookAbstract() {

    private val SkinsRestorer: SkinsRestorerAPI? =
        if (isHooked) {
            SkinsRestorerAPI.getPlayerStorage()
        } else {
            null
        }
        get() {
            if (field == null) reportAbuse()
            return field
        }

    fun getPlayerSkinTexture(name: String): String? {
        skinsRestorerAPI?.let {
            if (it.getSkinForPlayer(name) == null) {
                return null
            }

            val skinData = it.getSkinForPlayer(name)
            if (skinData != null) {
                return skinData.value
            }
        }
        return null
    }

}
