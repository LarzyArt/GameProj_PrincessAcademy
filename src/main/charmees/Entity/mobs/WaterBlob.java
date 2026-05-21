package charmees.Entity.mobs;
import charmees.util.MobNPC;
import charmees.util.Character;

public class WaterBlob extends MobNPC {

    public WaterBlob(String name, String charClass, String type, String weapon, int healthPoints, int chapter) {
        super(name, charClass, type, weapon, healthPoints, chapter);
    }

    @Override
    public void useSkill(int skill, Character target) {
        waterBlobSkills(skill, target);
    }

    // ----------------- Water Blob's Skills -----------------
    private void waterBlobSkills(int skill, Character target) {
        int damage = 0;
        switch (skill) {
            case 1: // Water Splash
                damage = (int)(Math.random() * 6) + 5;
                System.out.println(name + " used Water Splash! Deals " + damage + " damage.");
                target.takedamage(damage);
                break;
            case 2: // Aqua Burst
                damage = (int)(Math.random() * 11) + 5;
                System.out.println(name + " used Aqua Burst! Deals " + damage + " damage.");
                target.takedamage(damage);
                break;
        }
    }
}