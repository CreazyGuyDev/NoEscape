package pl.creazy.noescape.combat;

import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;
import pl.creazy.creazylib.part.constraints.Injected;
import pl.creazy.creazylib.task.TaskRun;
import pl.creazy.creazylib.task.constraints.Task;
import pl.creazy.creazylib.util.message.Message;
import pl.creazy.creazylib.util.message.Placeholder;

@Task(run = TaskRun.TIMER, delay = 0L, period = 10L)
class CombatTask extends BukkitRunnable {
  @Injected
  private CombatManager combatManager;

  @Injected
  private CombatMessageConfig messages;

  @Override
  public void run() {
    Bukkit.getOnlinePlayers().forEach(player -> {
      if (!combatManager.isInCombat(player)) {
       return;
      }

      combatManager.decreaseCombatTime(player, 10);

      if (combatManager.isInCombat(player)) {
        var combatTime = combatManager.getCombatTime(player) / 20;
        Message.create(messages.getTimeDisplay(), Placeholder.create("\\$\\{TIME\\}", combatTime)).sendActionBar(player);
      } else {
        Message.sendActionBar(player, messages.getCombatEnd());
      }
    });
  }
}
