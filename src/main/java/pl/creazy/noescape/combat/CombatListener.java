package pl.creazy.noescape.combat;

import org.bukkit.entity.Player;
import org.bukkit.entity.SizedFireball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import pl.creazy.creazylib.listener.constraints.EventHandlers;
import pl.creazy.creazylib.part.constraints.Injected;

@EventHandlers
class CombatListener implements Listener {
  @Injected
  private CombatManager combatManager;

  @EventHandler
  void resetCombatTimeOnPlayerDamageByPlayer(EntityDamageByEntityEvent event) {
    if (!(event.getEntity() instanceof Player player)) {
      return;
    }
    if (!(event.getDamager() instanceof Player damager)) {
      return;
    }
    combatManager.resetCombatTime(player);
    combatManager.resetCombatTime(damager);
  }

  @EventHandler
  void resetCombatTimeOnPlayerDamageByProjectile(ProjectileHitEvent event) {
    if (!(event.getHitEntity() instanceof Player hitPlayer)) {
      return;
    }
    if (!(event.getEntity().getShooter() instanceof Player shooter)) {
      return;
    }
    if (combatManager.shouldResetCombatTimeOnProjectileHit(event.getEntity())) {
      combatManager.resetCombatTime(hitPlayer);
      combatManager.resetCombatTime(shooter);
    }
  }

  @EventHandler
  void clearCombatTimeOnDeath(PlayerDeathEvent event) {
    combatManager.setCombatTime(event.getEntity(), 0);
  }

  @EventHandler
  void killPlayerInCombat(PlayerQuitEvent event) {
    var player = event.getPlayer();
    if (combatManager.isInCombat(player)) {
      player.setHealth(0D);
    }
  }
}
