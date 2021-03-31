public class Villagers extends Players{
    public static int numberOfVillagers;
    //initialize block to number of villagers
    {
        numberOfVillagers+=1;
    }
    //constructor
    public Villagers(String name, Roles role){
        super(name, role);
    }
}