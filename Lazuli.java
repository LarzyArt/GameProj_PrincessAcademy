
public class Lazuli extends Character {

    public Lazuli(String name, String charClass, String type, String weapon, int healthPoints, int manaPoints) {
        super(name, charClass, type, weapon, healthPoints, manaPoints);
    }

    @Override
    public void useSkill(int skill, MobNPC target, Character ally, Character[] party) {
        // if this character is stunned, they cannot use skills
        if (this.isStunned()) {
            System.out.println(this.getName() + " is stunned and cannot use skills.");
            return;
        }
        lazuliSkills(skill, ally, party);
    }

    // ----------------- Lazuli's Skills -----------------
    private void lazuliSkills(int skill, Character ally, Character[] party) {
        int heal = 0;
        switch (skill) {
            case 1: // Basic Heal
                if (manaPoints >= 10) {
                    manaPoints -= 10;
                    // restore ~25% of current HP (best-effort since no max HP stored) and some MP
                    heal = Math.max(15, ally.healthPoints / 4);
                    ally.healAlly(heal);
                    ally.restoreMP(10);
                    System.out.println(name + " used Basic Heal! Restores " + heal + " HP and 10 MP to " + ally.getName() + ".");
                } else System.out.println(name + " doesn't have enough mana!");
                break;
            case 2: // Ocean's Blessing
                if (manaPoints >= 20) {
                    manaPoints -= 20;
                    // heal and restore MP to all allies
                    for (Character c : party) {
                        if (c != null && c.isAlive()) {
                            int h = Math.max(10, c.healthPoints / 7); // ~15% of current
                            c.healAlly(h);
                            c.restoreMP(10);
                        }
                    }
                    System.out.println(name + " used Ocean's Blessing! Heals allies and restores MP to all allies.");
                } else System.out.println(name + " doesn't have enough mana!");
                break;
            case 3: // Harmonic Wave
                if (manaPoints == 0) {
                    for (Character c : party) {
                        if (c != null && c.isAlive()) {
                            int h = Math.max(20, c.healthPoints / 2);
                            c.healAlly(h);
                            c.restoreMP(100);
                        }
                    }
                    System.out.println(name + " used Ultimate, Harmonic Wave! Restores large MP and HP to all allies.");
                } else System.out.println(name + " must have 0 mana to use Harmonic Wave!");
                break;
            }
        }

    @Override
    public String[] getSkillList() {
        return new String[]{"1) Basic Heal - Cost: 10 MP","2) Ocean's Blessing - Cost: 20 MP","3) Harmonic Wave - Requires 0 MP"};
    }

    @Override
    public String getSkillTargetType(int skill) {
        switch (skill) {
            case 1: return "ALLY"; // Basic Heal
            case 2: return "ALL";  // Ocean's Blessing
            case 3: return "ALL";  // Harmonic Wave
            default: return "ALLY";
        }
    }
}