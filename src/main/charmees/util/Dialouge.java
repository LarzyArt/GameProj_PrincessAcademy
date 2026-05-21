package src.main.charmees.util;

public class Dialouge {

    private static Random random = new Random();

    // =========================================================================
    // SPECIAL FEATURE: Characters break character (ONLY in chapters 1, 2, 3)
    // =========================================================================

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

    // Normal say function, just prints dialogue
    private static void say(String speaker, String line) {
        System.out.println(speaker + ": " + line);
    }



    // =========================================================================
    // PROLOGUE
    // =========================================================================
    public static String[][] getPrologue() {
        return new String[][]{
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



    // =========================================================================
    // CHAPTER 1: The Sea at Dusk
    // =========================================================================
    public static String[][] getChapter1PreBattle() {
        return new String[][]{
                {"Lynzi", "The sea looks really angry."},
                {"Lazuli", "The waves are huge. And the rain won't stop."},
                {"Audry", "Something feels wrong. The magic here is too strong."},
                {"Giantha", "Look! Something is rising from the water!"},
                {"Shiera", "...I see a figure."},
                {"Siren Empress", "Who dares to come to my sea?"},
                {"Lynzi", "We're from Princess Academy. The First Princess sent us."},
                {"Siren Empress", "I don't care. Leave now."},
                {"Lazuli", "We can't. Not until we fix the sea."},
                {"Siren Empress", "Then you leave me no choice. FIGHT!"}
        };
    }

    public static String[][] getChapter1PostBattle() {
        return new String[][]{
                {"Siren Empress", "I... I lost..."},
                {"Lazuli", "Are you okay? What happened to you?"},
                {"Siren Empress", "I don't... remember..."},
                {"Shiera", "You forgot everything?"},
                {"Siren Empress", "Only a song... I remember a song..."},
                {"Giantha", "A song? From where?"},
                {"Siren Empress", "The World Tree... it came from the World Tree..."},
                {"Audry", "The World Tree?"},
                {"Siren Empress", "The garden... behind the academy... find it..."},
                {"Siren Empress", "Stop the song... before it takes others..."},
                {"L████", "Then the Siren Empress turned into foam and vanished."},
                {"Lazuli", "The World Tree... we need to find it."},
                {"Lynzi", "Agreed. Let's go back."}
        };
    }


    
    // =========================================================================
    // CHAPTER 2: L'arbre de Vie Garden
    // =========================================================================
    public static String[][] getChapter2PreBattle() {
        return new String[][]{
                {"Giantha", "This garden feels wrong."},
                {"Audry", "That tree... so much dark magic."},
                {"Resonara", "I can't stop... the song won't stop..."},
                {"Lynzi", "She's covered in black goo."},
                {"Resonara", "I don't want to fight! But I can't stop myself!"},
                {"Lazuli", "We have to help her. But she won't let us near."}
        };
    }

    public static String[][] getChapter2PostBattle() {
        return new String[][]{
                {"Resonara", "The voice... it's gone now..."},
                {"Audry", "Who did this to you?"},
                {"Resonara", "Princess Twinkle. The First Princess."},
                {"Lazuli", "The First Princess?"},
                {"Resonara", "She wanted the tree's power to stay young forever."},
                {"Resonara", "Behind her throne... there's a secret door..."},
                {"Resonara", "Go there. Stop her."},
                {"L████", "Then her eyes closed. She was finally free."},
                {"Lynzi", "A secret door. Let's go."},
                {"Lazuli", "Wait. We need to be careful."}
        };
    }


    // =========================================================================
    // CHAPTER 3: True Love, Fake Pain
    // =========================================================================
    public static String[][] getChapter3PreBattle() {
        return new String[][]{
                {"Shiera", "This place is full of princess dolls."},
                {"Lynzi", "They look too real..."},
                {"Lazuli", "These were real princesses. Their life was taken."},
                {"Kassundre", "She said she'd help me find love again..."},
                {"Kassundre", "Instead she made me this monster."},
                {"Kassundre", "Love is a lie! Now you all die!"}
        };
    }

    public static String[][] getChapter3MidBattle() {
        return new String[][]{
                {"Kassundre", "Why won't you die?!"},
                {"Lynzi", "Because we're stronger than you."},
                {"Princess Twinkle", "Well done. You beat my puppet."},
                {"Princess Twinkle", "But now you face ME. The one who built this school."}
        };
    }

    public static String[][] getChapter3Boss50() {
        return new String[][]{
                {"Princess Twinkle", "You hurt me?! HOW?!"},
                {"Lazuli", "Your power comes from stolen lives. It's fake."},
                {"Princess Twinkle", "I did what I had to! I won't die!"}
        };
    }

    public static String[][] getChapter3Boss25() {
        return new String[][]{
                {"Princess Twinkle", "My power... it's fading... NO!"},
                {"Giantha", "Your spell is breaking. The souls are fighting back."},
                {"Princess Twinkle", "I was nothing before! I won't go back to nothing!"}
        };
    }

    public static String[][] getChapter3Boss10() {
        return new String[][]{
                {"Princess Twinkle", "PLEASE! I'll give you anything! Power! Immortality!"},
                {"Lazuli", "Being scared of death doesn't give you the right to kill."}
        };
    }

    public static String[][] getChapter3PostBattle() {
        return new String[][]{
                {"Princess Twinkle", "So this is death... cold and alone..."},
                {"Lynzi", "It's over. I'm ending this."},
                {"Lazuli", "Wait! If you kill her, you're no better—"},
                {"Audry", "Lynzi... calm down. We can still stop this peacefully."},
                {"L████", "Lynzi's hands trembled with rage."},
                {"L████", "Before anyone could react..."},
                {"L████", "Lynzi struck Audry's chest."},
                {"Audry", "...Ah."},
                {"L████", "Audry's core shattered instantly."},
                {"Lazuli", "AUDRY!!"},
                {"Princess Twinkle", "...You destroyed your own friend."}
        };
    }



    // =========================================================================
    // EPILOGUE
    // =========================================================================
    public static String[][] getEpilogue() {
        return new String[][]{
                {"L████", "Audry fell. No scream. Just silence."},
                {"L████", "Lazuli screamed. Lynzi froze."},
                {"L████", "Something inside Lynzi broke."},
                {"L████", "She attacked Twinkle again and again until she stopped moving."},
                {"L████", "The others didn't see justice. They saw murder."},
                {"L████", "They ran. Lynzi chased them."},
                {"L████", "She was scared. If they talked, everyone would call her a killer."},
                {"L████", "One by one, in the dark, they fell."},
                {"L████", "Only Lazuli got away. Bleeding. Broken. Alone."},
                {"L████", "When morning came, the survivors lied."},
                {"L████", "They said Lazuli was the murderer."},
                {"L████", "She ran into the forest. And disappeared."},
                {"", ""},
                {"", "✦ ✦ ✦"},
                {"", ""},
                {"L████", "The air grows cold. Something feels different..."},
                {"", ""},
                {"L████", "Oh... a child still has a spark..."},
                {"L████", "This should be fun..."}
        };
    }



    // =========================================================================
    // SPECIAL EPILOGUE
    // =========================================================================
    public static String[][] getSpecialEpilogue() {
        return new String[][]{
                {"L████", "..."},
                {"L████", "Ohh... I see you.."},
                {"L████", "You were watching, weren't you?"},
                {"L████", "I See... HA HA"},
                {"L████", "YOU'RE AN IRREGULAR LIKE ME!!"},
                {"L████", "Write this down... L.E.Z.L.I"},
                {"L████", "I'll see you at the Arcade."},
                {"L████", "IRREGULAR"}
        };
    }
}