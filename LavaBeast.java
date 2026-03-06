
public class LavaBeast extends MobNPC {

    public LavaBeast(String name, String charClass, String type, String weapon, int healthPoints, int chapter) {
        super(name, charClass, type, weapon, healthPoints, chapter);
    }

    @Override
    public void useSkill(int skill, Character target) {
        lavaBeastSkills(skill, target);
    }

    // ----------------- Lava Beast's Skills -----------------
    private void lavaBeastSkills(int skill, Character target) {
        int damage = 0;
        switch (skill) {
            case 1: // Fire Breath
                damage = (int)(Math.random() * 16) + 10;
                target.takedamage(damage);
                System.out.println(name + " used Fire Breath! Deals " + damage + " damage.");
                break;
            case 2: // Lava Eruption
                damage = (int)(Math.random() * 26) + 15;
                target.takedamage(damage);
                System.out.println(name + " used Lava Eruption! Deals " + damage + " damage.");
                break;
            case 3: // Volcanic Fury
                damage = (int)(Math.random() * 51) + 30;
                target.takedamage(damage);
                System.out.println(name + " used Volcanic Fury! Deals " + damage + " damage.");
                break;
        }
    }
}