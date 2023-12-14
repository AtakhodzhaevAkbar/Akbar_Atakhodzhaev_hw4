import java.util.Random;

public class Main {
    public static int bossHealth = 700;
    public static int bossDamage = 50;
    public static String bossDefence;
    public static int[] heroesHealth = {280, 270, 300, 230, 200, 260};
    public static int[] heroesDamage = {30, 25, 20, 0, 20, 25};
    public static String[] heroesAttackType = {"Physical", "Magical", "Kinetic", "Medic", "Lucky", "Thor"};
    public static int roundNumber;
    public static Random random = new Random();
    public static boolean thorAttack;

    public static void main(String[] args) {
        showStatistics();
        while (!isGameOver()) {
            playRound();
        }
    }

    public static void playRound() {
        roundNumber++;
        chooseBossDefence();
        thorAttack();
        bossAttacks();
        heroesAttack();
        showStatistics();
    }

    public static void chooseBossDefence() {
        int randomIndex = random.nextInt(heroesHealth.length - 2);
        bossDefence = heroesAttackType[randomIndex];
    }

    public static void heroesAttack() {
        for (int i = 0; i < heroesDamage.length; i++) {
            if (heroesHealth[i] > 0 && bossHealth > 0) {
                int damage = heroesDamage[i];
                if (heroesAttackType[i] == bossDefence) {
                    int coeff = random.nextInt(9) + 2;
                    damage = heroesDamage[i] * coeff;
                    System.out.println("Critical damage: " + damage);
                }
                if (heroesAttackType[i].equals("Medic")) {
                    int targetIndex;
                    do {
                        targetIndex = random.nextInt(heroesHealth.length - 2);
                    } while (targetIndex == i);
                    heroesHealth[targetIndex] = Math.min(heroesHealth[targetIndex] + 50, 100);
                    System.out.println("Medic healed " + heroesAttackType[targetIndex] + " for 50 health.");
                }
                if (heroesAttackType[i].equals("Lucky") && random.nextBoolean()) {
                    System.out.println("Lucky dodged the boss's attack!");
                    continue;
                }
                if (bossHealth - damage < 0) {
                    bossHealth = 0;
                } else {
                    bossHealth = bossHealth - damage;
                }
            }
        }
    }

    public static void bossAttacks() {
        for (int i = 0; i < heroesHealth.length; i++) {
            if (heroesHealth[i] > 0) {
                if (heroesAttackType[i].equals("Thor") && thorAttack) {
                    System.out.println("Thor stunned the boss! The boss skips this round.");
                    continue;
                }
                if (heroesHealth[i] - bossDamage < 0) {
                    heroesHealth[i] = 0;
                } else {
                    heroesHealth[i] = heroesHealth[i] - bossDamage;
                }
            }
        }
    }

    public static boolean isGameOver() {
        if (bossHealth <= 0) {
            System.out.println("Heroes won!!!");
            return true;
        }

        boolean allHeroesDead = true;
        for (int i = 0; i < heroesHealth.length; i++) {
            if (heroesHealth[i] > 0) {
                allHeroesDead = false;
                break;
            }
        }
        if (allHeroesDead) {
            System.out.println("Boss won!!!");
        }
        return allHeroesDead;
    }

    public static void showStatistics() {
        System.out.println("ROUND " + roundNumber + " ---------------");
        System.out.println("Boss health: " + bossHealth + " damage: " + bossDamage + " defence: " +
                (bossDefence == null ? "No defence" : bossDefence));
        for (int i = 0; i < heroesHealth.length; i++) {
            System.out.println(heroesAttackType[i] + " health: " + heroesHealth[i] + " damage: " + heroesDamage[i]);
        }
    }

    public static void thorAttack() {
        thorAttack = random.nextBoolean();
    }
}
