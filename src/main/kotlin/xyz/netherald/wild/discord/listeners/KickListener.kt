package xyz.netherald.wild.discord.listeners

import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.entities.MessageEmbed
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerKickEvent
import xyz.netherald.wild.discord.WildDiscord

class KickListener(private val plugin: WildDiscord): Listener {

    @EventHandler
    fun onPlayerKick(event: PlayerKickEvent) {
        if (!plugin.config.getBoolean("kickEnable")) return
        val channel = WildDiscord.jda?.getTextChannelById(plugin.config.getString("channelId")!!)
        val format: String = plugin.config.getString("kickFormat")?:
        "**<player>님이 추방 되었습니다.**"

        if (plugin.config.getBoolean("deathEmbed")) {
            val title: String? = plugin.config.getString("kickEmbedTitle")
            val description: String = DiscordListener.replaceAccessFormat(event, format, true)
            val color: Int = plugin.config.getInt("kickEmbedColor")
            val builder = EmbedBuilder().setDescription(description)
                .setColor(color)

            if (!(title == null || title == "")) {
                builder.setTitle(title)
            }

            val embed: MessageEmbed = builder.build()
            channel?.sendMessage(embed)?.queue()
        } else {
            channel?.sendMessage(DiscordListener.replaceAccessFormat(event, format, true))?.queue()
        }
    }
}