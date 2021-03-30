class Villagers extends Players{

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

class  Detective extends Villagers{

    int k=0;

    //constructor
    public Detective(String name, Roles role) {
        super(name, role);
    }

    public void asking(String name){
        for(int i=0; i<100; i++){
            if(MainGame.Player[i].name==name){
                if(MainGame.Player[i].role==Roles.mafia||MainGame.Player[i].role==Roles.silencer){
                    System.out.println("yes");
                }
                else{
                    System.out.println("no");
                }
                break;
            }
        }
    }

}

class Doctor extends Villagers{

    //constructor
    public Doctor(String name, Roles role) {
        super(name, role);
    }

    public Players choose(String name){
        int i;
        for(i=0; i<100; i++){
            if(MainGame.Player[i].name.equals(name)){
                break;
            }
        }
        return MainGame.Player[i];
    }
}