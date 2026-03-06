
public class MoonSprite extends MobNPC {

    public MoonSprite(String name, String charClass, String type, String weapon, int healthPoints, int chapter) {
        super(name, charClass, type, weapon, healthPoints, chapter);
    }

    @Override
    public void useSkill(int skill, Character target) {
        moonSpriteSkills(skill, target);
    }

    // ----------------- Moon Sprite's Skills -----------------
    private void moonSpriteSkills(int skill, Character target) {
        int damage = 0;
        switch (skill) {
            case 1: // Lunar Beam
                damage = (int)(Math.random() * 6) + 5;
                target.takedamage(damage);
                System.out.println(name + " used Lunar Beam! Deals " + damage + " damage.");
                break;
            case 2: // Moonlight Burst
                damage = (int)(Math.random() * 11) + 5;
                target.takedamage(damage);
                System.out.println(name + " used Moonlight Burst! Deals " + damage + " damage.");
                break;
        }
    }
}