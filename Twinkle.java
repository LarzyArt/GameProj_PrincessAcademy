
public class Twinkle extends MobNPC {

    public Twinkle(String name, String charClass, String type, String weapon, int healthPoints, int chapter) {
        super(name, charClass, type, weapon, healthPoints, chapter);
    }

    @Override
    public void useSkill(int skill, Character target) {
        twinkleSkills(skill, target);
    }

    // ----------------- Twinkle's Skills -----------------
    private void twinkleSkills(int skill, Character target) {
        //Boss fight skills
        int damage = 0;
        switch (skill) {
            case 1: // Puppet Slash
                    damage = (int)(Math.random() * 26) + 5;
                    target.takedamage(damage);
                    System.out.println(name + " used Puppet Slash! Deals " + damage + " damage.");
                    break;
            case 2: // Lazer Devastation
                    int hits = (int)(Math.random() * 2) + 1;
                    damage = 0;
                    for (int i = 0; i < hits; i++) {
                        damage += 20;
                    }
                    target.takedamage(damage);
                    System.out.println(name + " used Lazer Devastation! Hits " + hits + " times for " + damage + " damage.");
                break;
            case 3: // Corruption
                    damage = 60;
                    target.takedamage(damage);
                    System.out.println(name + " used Corruption! Deals " + damage + " fixed damage.");
                break;
        }
    }
}