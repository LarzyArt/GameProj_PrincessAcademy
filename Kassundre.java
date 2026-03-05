
public class Kassundre extends MobNPC {

    public Kassundre(String name, String charClass, String type, String weapon, int healthPoints, int chapter) {
        super(name, charClass, type, weapon, healthPoints, chapter);
    }

    @Override
    public void useSkill(int skill, Character target) {
        kassundreSkills(skill, target);
    }

    // ----------------- Kassundre's Skills -----------------
    private void kassundreSkills(int skill, Character target) {
        //Miniboss fight skills
        int damage = 0;
        switch (skill) {
            case 1: // Corrupted tears
                    int hits = (int)(Math.random() * 4) + 1;
                    damage = 0;
                    for (int i = 0; i < hits; i++) {
                        damage += (int)(Math.random() * 6) + 5;
                    }
                    target.takedamage(damage);
                    System.out.println(name + " used Corrupted Tears! Hits " + hits + " times for " + damage + " damage.");
                break;
            case 2: // Corrupted Hug
                    damage = (int)(Math.random() * 26) + 10;
                    target.takedamage(damage);
                    System.out.println(name + " used Corrupted Hug! Deals " + damage + " damage.");
                break;
            case 3: // Corrupted Flora
                    damage = (int)(Math.random() * 21) + 20;
                    target.takedamage(damage);
                    System.out.println(name + " used Corrupted Flora! Deals " + damage + " damage.");
                break;
        }
    }
}