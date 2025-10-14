package engine;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Manages the list of achievements for a player,
 * including loading from and saving to the FileManager.
 */
public class AchievementManager {

    private List<Achievement> achievements;

    public AchievementManager() {
        this.achievements = createDefaultAchievements();
    }

    /** Defines the default achievements available in the game. */
    private List<Achievement> createDefaultAchievements() {
        List<Achievement> list = new ArrayList<>();
        list.add(new Achievement("First Blood", "Defeat your first enemy."));
        list.add(new Achievement("Survivor", "Clear a round without losing a life."));
        list.add(new Achievement("Clear", "Clear 5 levels."));
        return list;
    }

    /**
     * Loads the achievements from FileManager using a boolean list
     * and converts them into Achievement objects.
     */
    public void loadFromBooleans(String userName) throws IOException {
        List<Boolean> flags = FileManager.getInstance().searchAchievementsByName(userName);
        this.achievements = createDefaultAchievements();
        for (int i = 0; i < flags.size() && i < achievements.size(); i++) {
            if (flags.get(i)) {
                achievements.get(i).unlock();
            }
        }
    }

    /**
     * Converts the achievements into a boolean list and
     * saves them using FileManager.
     */
    public void saveToFile(String userName) throws IOException {
        List<Boolean> flags = new ArrayList<>();
        for (Achievement a : achievements) {
            flags.add(a.isUnlocked());
        }
        FileManager.getInstance().unlockAchievement(userName, flags);
    }

    /** Returns the current achievement list. */
    public List<Achievement> getAchievements() {
        return achievements;
    }

    /** Unlocks the achievement by name. */
    public void unlock(String name) {
        for (Achievement a : achievements) {
            if (a.getName().equals(name) && !a.isUnlocked()) {
                a.unlock();
                System.out.println("Achievement unlocked: " + a);
            }
        }
    }
}
