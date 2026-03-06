// Interface for entities that can have status effects applied
public interface StatusEffectable {
    boolean isStunned();
    void applyStun(int turns);
    void applyTaunt(int turns);
    void applyDamageReduction(double percent, int turns);
    void tickStatus();
}