package pl.creazy.noescape.combat;

import lombok.Getter;
import pl.creazy.creazylib.data.persistence.config.constraints.ConfigFile;
import pl.creazy.creazylib.data.persistence.config.constraints.ConfigVar;

@Getter
@ConfigFile(name = "combat")
class CombatConfig {
  @ConfigVar("combat-time")
  private Integer combatTime = 600;

  @ConfigVar("reset.snowball")
  private Boolean resetOnSnowball = true;

  @ConfigVar("reset.egg")
  private Boolean resetOnEgg = true;

  @ConfigVar("reset.arrow")
  private Boolean resetOnArrow = true;

  @ConfigVar("reset.ender-pearl")
  private Boolean resetOnEnderPearl = false;

  @ConfigVar("reset.fishing-rod")
  private Boolean resetOnFishingRod = true;

  @ConfigVar("reset.potion")
  private Boolean resetOnPotion = false;

  @ConfigVar("reset.wind-charge")
  private Boolean resetOnWindCharge = true;
}
