public abstract class UsefulVillagers extends Villagers{
    //constructor
    public UsefulVillagers(String name, Roles role) {
        super(name, role);
    }
    public abstract void action(String name);
}