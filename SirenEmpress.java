
public class SirenEmpress extends MobNPC {

    public SirenEmpress(String name, String charClass, String type, String weapon, int healthPoints, int chapter) {
        super(name, charClass, type, weapon, healthPoints, chapter);
    }

    @Override
    public void useSkill(int skill, Character target) {
        sirenEmpressSkills(skill, target);
    }

    // ----------------- Siren Empress's Skills -----------------
    private void sirenEmpressSkills(int skill, Character target) {
        int damage = 0;
        switch (skill) {
            case 1: // Water Whip
                damage = (int)(Math.random() * 11) + 5;
                target.takedamage(damage);
                System.out.println(name + " used Water Whip! Deals " + damage + " damage.");
                break;
            case 2: // Tidal Wave
                damage = (int)(Math.random() * 21) + 10;
                target.takedamage(damage);
                System.out.println(name + " used Tidal Wave! Deals " + damage + " damage.");
                break;
            case 3: // Abyssal Crush
                damage = (int)(Math.random() * 41) + 20;
                target.takedamage(damage);
                System.out.println(name + " used Abyssal Crush! Deals " + damage + " damage.");
                break;
        }
    }
}