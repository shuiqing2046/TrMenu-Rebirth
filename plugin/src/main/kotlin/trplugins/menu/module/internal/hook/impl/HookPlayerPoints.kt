package trplugins.menu.module.internal.hook.impl

import trplugins.menu.module.internal.hook.HookAbstract
import org.black_ixx.playerpoints.PlayerPoints
import org.black_ixx.playerpoints.PlayerPointsAPI
import org.bukkit.Bukkit
import org.bukkit.OfflinePlayer
import taboolib.common.platform.function.submit
import java.util.concurrent.TimeUnit

/**
 * @author Arasple Super_chen520
 * @date 2023/10/22 11:40 AM
 */
class HookPlayerPoints : HookAbstract() {

    private val playerPointsAPI: PlayerPointsAPI? = if (isHooked) (plugin as PlayerPoints).api else null
        get() {
            if (field == null) reportAbuse()
            return field
        }

    fun getPoints(player: OfflinePlayer): Int {
        return playerPointsAPI?.look(player.uniqueId) ?: -1
    }

    fun setPoints(player: OfflinePlayer, amount: Int) {
        playerPointsAPI?.set(player.uniqueId, amount)
    }

    fun hasPoints(player: OfflinePlayer, amount: Int): Boolean {
        return getPoints(player) >= amount
    }

    fun addPoints(player: OfflinePlayer, amount: Int) {
        playerPointsAPI?.give(player.uniqueId, amount)
    }

    fun takePoints(player: OfflinePlayer, amount: Int) {
        playerPointsAPI?.take(player.uniqueId, amount)
    }
}