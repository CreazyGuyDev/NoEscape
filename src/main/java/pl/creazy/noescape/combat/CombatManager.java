package pl.creazy.noescape.combat;

import org.bukkit.entity.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.jetbrains.annotations.NotNull;
import pl.creazy.creazylib.data.persistence.config.Config;
import pl.creazy.creazylib.manager.constraints.Manager;
import pl.creazy.creazylib.part.constraints.Injected;
import pl.creazy.creazylib.part.constraints.OnDisable;
import pl.creazy.creazylib.part.constraints.OnEnable;
import pl.creazy.creazylib.util.mc.Mc;
import pl.creazy.noescape.NoEscape;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Manager
class CombatManager {
  private final Map<UUID, Integer> combatTime = new HashMap<>();

  @Injected
  private CombatConfig config;

  @OnEnable
  private void restore(NoEscape plugin) {
    var data = getConfig(plugin);

    for (var key : data.getKeys()) {
      combatTime.put(UUID.fromString(key), data.getInt(key));
    }
  }

  @OnDisable
  private void save(NoEscape plugin) {
    var data = getConfig(plugin);

    for (var entry : combatTime.entrySet()) {
      data.set(entry.getKey().toString(), entry.getValue());
    }

    data.save();
  }

  private Config getConfig(NoEscape plugin) {
    return Config.getConfig("combatTime", "data", plugin);
  }


  boolean isInCombat(@NotNull UUID playerId) {
    return getCombatTime(playerId) > 0;
  }

  boolean isInCombat(@NotNull Player player) {
    return isInCombat(player.getUniqueId());
  }

  int getCombatTime(@NotNull Player player) {
    return getCombatTime(player.getUniqueId());
  }

  int getCombatTime(@NotNull UUID playerId) {
    return combatTime.getOrDefault(playerId, 0);
  }

  void decreaseCombatTime(@NotNull Player player, int amount) {
    setCombatTime(player, getCombatTime(player) - amount);
  }

  void setCombatTime(@NotNull Player player, int combatTime) {
    setCombatTime(player.getUniqueId(), combatTime);
  }

  void setCombatTime(@NotNull UUID playerId, int combatTime) {
    this.combatTime.put(playerId, Math.max(combatTime, 0));
  }

  void resetCombatTime(@NotNull UUID playerId) {
    setCombatTime(playerId, config.getCombatTime());
  }

  void resetCombatTime(@NotNull Player player) {
    resetCombatTime(player.getUniqueId());
  }

  @SuppressWarnings("UnstableApiUsage")
  boolean shouldResetCombatTimeOnProjectileHit(@NotNull Projectile projectile) {
    if (projectile instanceof ThrownPotion) {
      return config.getResetOnPotion();
    } else if (projectile instanceof WindCharge) {
      return config.getResetOnWindCharge();
    } else if (projectile instanceof Egg) {
      return config.getResetOnEgg();
    } else if (projectile instanceof Snowball) {
      return config.getResetOnSnowball();
    } else if (projectile instanceof Arrow || projectile instanceof SpectralArrow) {
      return config.getResetOnArrow();
    } else if (projectile instanceof EnderPearl) {
      return config.getResetOnEnderPearl();
    } else if (projectile instanceof FishHook) {
      return config.getResetOnFishingRod();
    } else {
      return true;
    }
  }
}
