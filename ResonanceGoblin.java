
public class ResonanceGoblin extends MobNPC {

    public ResonanceGoblin(String name, String charClass, String type, String weapon, int healthPoints, int chapter) {
        super(name, charClass, type, weapon, healthPoints, chapter);
    }

    @Override
    public void useSkill(int skill, Character target) {
        resonanceGoblinSkills(skill, target);
    }

    // ----------------- Resonance Goblin's Skills -----------------
    private void resonanceGoblinSkills(int skill, Character target) {
        int damage = 0;
        switch (skill) {
            case 1: // Echo Strike
                damage = (int)(Math.random() * 6) + 5;
                target.takedamage(damage);
                System.out.println(name + " used Echo Strike! Deals " + damage + " damage.");
                break;
            case 2: // Sonic Boom
                damage = (int)(Math.random() * 11) + 5;
                target.takedamage(damage);
                System.out.println(name + " used Sonic Boom! Deals " + damage + " damage.");
                break;
        }
    }
}