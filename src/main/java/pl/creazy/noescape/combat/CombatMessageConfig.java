package pl.creazy.noescape.combat;

import lombok.Getter;
import pl.creazy.creazylib.data.persistence.config.constraints.ConfigFile;
import pl.creazy.creazylib.data.persistence.config.constraints.ConfigVar;

@Getter
@ConfigFile(name = "combat", path = "messages")
class CombatMessageConfig {
  @ConfigVar("time-display")
  private String timeDisplay = "&eCzas do końca walki&b: &a${TIME}";

  @ConfigVar("combat-end")
  private String combatEnd = "&aMożesz się wylogować";
}
