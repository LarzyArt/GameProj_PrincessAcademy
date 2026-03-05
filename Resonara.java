
public class Resonara extends MobNPC {

    public Resonara(String name, String charClass, String type, String weapon, int healthPoints, int chapter) {
        super(name, charClass, type, weapon, healthPoints, chapter);
    }

    @Override
    public void useSkill(int skill, Character target) {
        resonaraSkills(skill, target);
    }

    // ----------------- Resonara's Skills -----------------
    private void resonaraSkills(int skill, Character target) {
        int damage = 0;
        switch (skill) {
            case 1: // Sonic Blast
                damage = (int)(Math.random() * 11) + 5;
                target.takedamage(damage);
                System.out.println(name + " used Sonic Blast! Deals " + damage + " damage.");
                break;
            case 2: // Echo Strike
                damage = (int)(Math.random() * 21) + 10;
                target.takedamage(damage);
                System.out.println(name + " used Echo Strike! Deals " + damage + " damage.");
                break;
            case 3: // Harmonic Destruction
                damage = (int)(Math.random() * 41) + 20;
                target.takedamage(damage);
                System.out.println(name + " used Harmonic Destruction! Deals " + damage + " damage.");
                break;
        }
    }
}