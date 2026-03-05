
public class WaterSprite extends MobNPC {

    public WaterSprite(String name, String charClass, String type, String weapon, int healthPoints, int chapter) {
        super(name, charClass, type, weapon, healthPoints, chapter);
    }

    @Override
    public void useSkill(int skill, Character target) {
        waterSpriteSkills(skill, target);
    }

    // ----------------- Water Sprite's Skills -----------------
    private void waterSpriteSkills(int skill, Character target) {
        int damage = 0;
        switch (skill) {
            case 1: // Water Jet
                damage = (int)(Math.random() * 6) + 5;
                target.takedamage(damage);
                System.out.println(name + " used Water Jet! Deals " + damage + " damage.");
                break;
            case 2: // Bubble Burst
                damage = (int)(Math.random() * 11) + 5;
                target.takedamage(damage);
                System.out.println(name + " used Bubble Burst! Deals " + damage + " damage.");
                break;
        }
    }
}