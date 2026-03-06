
public class AstralGlob extends MobNPC {

    public AstralGlob(String name, String charClass, String type, String weapon, int healthPoints, int chapter) {
        super(name, charClass, type, weapon, healthPoints, chapter);
    }

    @Override
    public void useSkill(int skill, Character target) {
        astralGlobSkills(skill, target);
    }

    // ----------------- Astral Glob's Skills -----------------
    private void astralGlobSkills(int skill, Character target) {
        int damage = 0;
        switch (skill) {
            case 1: // Astral Slime
                damage = (int)(Math.random() * 6) + 5;
                target.takedamage(damage);
                System.out.println(name + " used Astral Slime! Deals " + damage + " damage.");
                break;
            case 2: // Void Ooze
                damage = (int)(Math.random() * 11) + 5;
                target.takedamage(damage);
                System.out.println(name + " used Void Ooze! Deals " + damage + " damage.");
                break;
        }
    }
}