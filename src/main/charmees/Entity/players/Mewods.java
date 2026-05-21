package charmees.Entity.players;
import charmees.util.Character;
import charmees.util.MobNPC;

public class Mewods extends Character {

    public Mewods(String name, String charClass, String type, String weapon, int healthPoints, int manaPoints) {
        super(name, charClass, type, weapon, healthPoints, manaPoints);
    }

    @Override
    public void useSkill(int skill, MobNPC target, Character ally, Character[] party) {
        mewodsSkills(skill, target);
    }

    // ----------------- Mewods's Skills -----------------
    private void mewodsSkills(int skill, MobNPC target) {
        int damage = 0;
        switch (skill) {
            case 1: // Unrated Warmup
                if (manaPoints >= 2) {
                    manaPoints -= 2;
                    damage = (int)(Math.random() * 10) + 7;
                    System.out.println(name + " used Unrated Warmup! Fires a shot with the confidence of someone who definitely belongs in this rank. Deals " + damage + " damage.");
                    target.takedamage(damage);
                } else System.out.println(name + " doesn't have enough mana! Can't buy abilities — spent all creds on the Vandal skin.");
                break;
            case 2: // Spike Rush Panic
                if (manaPoints >= 5) {
                    manaPoints -= 5;
                    int hits = (int)(Math.random() * 3) + 1;
                    damage = 0;
                    for (int i = 0; i < hits; i++) {
                        damage += (int)(Math.random() * 10) + 6;
                    }
                    System.out.println(name + " used Spike Rush Panic! Sprays " + hits + " shot(s) wildly for " + damage + " damage. No crosshair placement, just vibes and prayers.");
                    target.takedamage(damage);
                } else System.out.println(name + " doesn't have enough mana! Saving for next round like a true eco warrior.");
                break;
            case 3: // Ace or Throw
                if (manaPoints >= 11) {
                    manaPoints -= 11;
                    damage = (int)(Math.random() * 31) + 80;
                    System.out.println(name + " used Ultimate, Ace or Throw! Activates ult at full charge and somehow clutches — " + damage + " damage. The team said nothing. They know.");
                    target.takedamage(damage);
                } else System.out.println(name + " doesn't have enough mana! Ult isn't up. Of course it isn't. It never is when you need it.");
                break;
        }
    }

    @Override
    public String[] getSkillList() {
        return new String[]{
                "[1] Unrated Warmup  - Cost: 2 MP  | A confident shot from someone suspiciously hardstuck (7-16 dmg)",
                "[2] Spike Rush Panic - Cost: 5 MP  | Multi-hit spray with zero crosshair discipline (6-15 per hit)",
                "[3] Ace or Throw    - Cost: 11 MP | All-or-nothing ult that somehow works (80-110 dmg)"
        };
    }

    @Override
    public String getSkillTargetType(int skill) {
        return "ENEMY";
    }
}