package pl.creazy.noescape.combat;

import org.bukkit.Bukkit;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.scheduler.BukkitRunnable;
import pl.creazy.creazylib.part.constraints.Injected;
import pl.creazy.creazylib.screen.bar.ScreenBar;
import pl.creazy.creazylib.task.TaskRun;
import pl.creazy.creazylib.task.constraints.Task;
import pl.creazy.creazylib.util.message.Message;
import pl.creazy.creazylib.util.message.Placeholder;
import pl.creazy.creazylib.util.text.Text;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Task(run = TaskRun.TIMER, delay = 0L, period = 5L)
class CombatTask extends BukkitRunnable {
  @Injected
  private CombatManager combatManager;

  @Injected
  private CombatMessageConfig messages;

  @Injected
  private CombatConfig config;

  private final Map<UUID, ScreenBar> bars = new HashMap<>();

  @Override
  public void run() {
    Bukkit.getOnlinePlayers().forEach(player -> {
      if (!combatManager.isInCombat(player)) {
       return;
      }

      combatManager.decreaseCombatTime(player, 5);

      if (combatManager.isInCombat(player)) {
        var combatTime = combatManager.getCombatTime(player) / 20;
        var message = Message.create(messages.getTimeDisplay(), Placeholder.create("\\$\\{TIME\\}", combatTime));

        if (config.getActionBar()) {
          message.sendActionBar(player);
        }
        if (config.getBossBar()) {
          var bar = bars.get(player.getUniqueId());

          if (bar == null) {
            bar = ScreenBar.create(message.getContent(), BarColor.valueOf(config.getBossBarColor()), BarStyle.valueOf(config.getBossBarStyle()));
            bar.addPlayers(player);
            bars.put(player.getUniqueId(), bar);
          }

          bar.setTitle(message.getContent());
        }
      } else {
        if (config.getActionBar()) {
          Message.sendActionBar(player, messages.getCombatEnd());
        }
        if (config.getBossBar()) {
          var bar = bars.get(player.getUniqueId());
          if (bar != null) {
            bar.setTitle(Text.color(messages.getCombatEnd()));
            bar.hide();
            bar.removePlayers();
          }
          bars.remove(player.getUniqueId());
        }
      }
    });
  }
}
