package pl.creazy.noescape.combat;

import lombok.Getter;
import pl.creazy.creazylib.data.persistence.config.constraints.ConfigFile;
import pl.creazy.creazylib.data.persistence.config.constraints.ConfigVar;

@Getter
@ConfigFile(name = "combat")
class CombatConfig {
  @ConfigVar("combat-time")
  private Integer combatTime = 600;
}
