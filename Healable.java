// Interface for entities that can be healed or have MP restored
public interface Healable {
    void healAlly(int heal);
    void restoreMP(int mp);
}