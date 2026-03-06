
public class Giantha extends Character {

    public Giantha(String name, String charClass, String type, String weapon, int healthPoints, int manaPoints) {
        super(name, charClass, type, weapon, healthPoints, manaPoints);
    }

    @Override
    public void useSkill(int skill, MobNPC target, Character ally, Character[] party) {
        // if this character is stunned, they cannot use skills
        if (this.isStunned()) {
            System.out.println(this.getName() + " is stunned and cannot use skills.");
            return;
        }
        gianthaSkills(skill, target);
    }

    // ----------------- Giantha's Skills -----------------
    private void gianthaSkills(int skill, MobNPC target) {
        int damage = 0;
        switch (skill) {
            case 1: // Giant Punch
                damage = (int)(Math.random() * 6) + 5;
                target.takedamage(damage);
                System.out.println(name + " used Giant Punch! Deals " + damage + " damage.");
                break;
            case 2: // Giant's Roar
                if (manaPoints >= 1) {
                    manaPoints -= 1;
                    // apply taunt to self so enemies prefer this character for 1 turn
                    this.tauntTurns = Math.max(this.tauntTurns, 1);
                    System.out.println(name + " used Giant's Roar! Enemies will target " + name + " for 1 turn.");
                } else System.out.println(name + " doesn't have enough mana!");
                break;
            case 3: // Club Smash
                if (manaPoints >= 9) {
                    manaPoints -= 9;
                    damage = (int)(Math.random() * 21) + 20;
                    target.takedamage(damage);
                    // stun the targeted enemy for 1 turn (they can't act)
                    target.applyStun(1);
                    System.out.println(name + " used Ultimate, Club Smash! Deals " + damage + " damage and stuns the target for 1 turn.");
                } else System.out.println(name + " doesn't have enough mana!");
                break;
        }
    }

    @Override
    public String[] getSkillList() {
        return new String[]{"1) Giant Punch - Cost: 0 MP","2) Giant's Roar - Cost: 1 MP","3) Club Smash - Cost: 9 MP"};
    }

    @Override
    public String getSkillTargetType(int skill) {
        return "ENEMY";
    }
}