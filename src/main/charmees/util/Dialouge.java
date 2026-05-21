package src.main.charmees.util;

public class Dialouge {

    private static Random random = new Random();

    // Returns true if character should break character based on chance
    private static boolean shouldBreakCharacter(double chance) {
        return random.nextDouble() < chance;
    }

    // Returns a random break line based on which character is talking
    private static String getBreakLine(String characterName) {
        switch (characterName) {
            case "Shiera":
                String[] shieraBreaks = {
                        "I think I feel... happy? Is this happy?",
                        "...That was fun. I never say that.",
                        "I don't know what this feeling is. I don't like it.",
                        "My chest feels warm. Is that normal?",
                        "I want to protect them. That's new.",
                        "Don't tell anyone... but I smiled.",
                        "Anger. Yes. That's anger. I recognize it now.",
                        "Lonely. I feel lonely. When did that happen?"
                };
                return shieraBreaks[random.nextInt(shieraBreaks.length)];

            case "Audry":
                String[] audryBreaks = {
                        "I used to be just slime. Now I have feelings. It's weird.",
                        "Do slimes cry? I think I'm crying.",
                        "I miss being simple. Emotions are complicated.",
                        "Why does my goo feel heavy when I'm sad?",
                        "I understand life now. And death. I'm scared."
                };
                return audryBreaks[random.nextInt(audryBreaks.length)];

            case "Lynzi":
                String[] lynziBreaks = {
                        "I'm scared. Don't tell anyone.",
                        "Lazuli... I'm sorry for everything.",
                        "What if I'm not strong enough?",
                        "I act tough but... never mind.",
                        "Promise me we all go home."
                };
                return lynziBreaks[random.nextInt(lynziBreaks.length)];

            case "Lazuli":
                String[] lazuliBreaks = {
                        "I'm tired of being the nice one.",
                        "Sometimes I want to scream too.",
                        "Lynzi... why are you like this?",
                        "I just want peace. Is that too much?",
                        "I'll heal everyone. Even if it kills me."
                };
                return lazuliBreaks[random.nextInt(lazuliBreaks.length)];

            case "Giantha":
                String[] gianthaBreaks = {
                        "I miss home.",
                        "Shiera is stronger than me. I hate admitting that.",
                        "What if I lose? What if I'm weak?",
                        "I don't want to be alone anymore.",
                        "Winning isn't everything... who said that?"
                };
                return gianthaBreaks[random.nextInt(gianthaBreaks.length)];

            default:
                return "...Never mind.";
        }
    }



    // =========================================================================
    // PROLOGUE
    // =========================================================================
    public static String[] getPrologue() {
        return new String[]{
                {"L████", "Welcome to Princess Academy. A place where princesses learn... princessy things."},
                {"L████", "Tea parties. Royal manners. How to rule a kingdom with grace."},
                {"L████", "Five princesses attended this season..."},
                {"L████", "Lynzi Ligh, the Princess of Chaos. Loud. Brave. Reckless."},
                {"L████", "Lazuli Ligh, her twin sister. Kind. Soft. Peace-loving."},
                {"L████", "Audry, a slime who gained a soul. Curious. Clever. Strange."},
                {"L████", "Giantha, from the giant clan. Strong. Proud. Competitive."},
                {"L████", "And Shiera, half-goliath, half-human. Cold. Quiet. Emotionless."},
                {"L████", "One day, a message arrived from the First Princess..."},
                {"L████", "\"The sea near the beach is in chaos. Raging waves. Endless rain.\""},
                {"L████", "\"Go. Find out what's wrong. And come back alive.\""},
                {"L████", "And so, the five princesses set off toward the roaring sea..."},
                {"L████", "They didn't know it yet... but this was only the beginning."}
        };
    }
}