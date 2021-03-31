public abstract class UsefulVillagers extends Villagers{
    public UsefulVillagers(String name, Roles role) {
        super(name, role);
    }
    public abstract void action(String name);
}