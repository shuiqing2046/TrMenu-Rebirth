package trplugins.menu.module.internal.hook.impl

import net.skinsrestorer.api.SkinsRestorerProvider
import net.skinsrestorer.api.SkinsRestorer
import trplugins.menu.module.internal.hook.HookAbstract

/**
 * @author MSCAoMe
 * @date 2023/11/11 14:12
 */
class HookSkinsRestorer : HookAbstract() {

    private val skinsRestorer: SkinsRestorer? =
        if (isHooked) {
            runCatching { SkinsRestorerProvider.get() }.getOrNull()
        } else {
            null
        }
        get() {
            if (field == null) reportAbuse()
            return field
        }

    fun getPlayerSkinTexture(name: String): String? {
        skinsRestorer?.let {
            if (it.skinStorage.findOrCreateSkinData(name) == null) {
                return null
            }

            val skinData = it.skinStorage.findOrCreateSkinData(name)
            return skinData.get().property.value
        }
        return null
    }

}
