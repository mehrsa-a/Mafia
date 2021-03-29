class NightVoting extends Players{
    int NightVoted=0;

    //constructor
    public NightVoting(String name, Roles role) {
        super(name, role);
    }
}

class Villagers extends NightVoting{

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
                    for(int j=0; j<100; j++){
                        if(MainGame.Player[j].role==Roles.detective){
                            MainGame.Player[j].information[k].name=MainGame.Player[i].name;
                            MainGame.Player[j].information[k].role=Roles.mafia;
                            k++;
                            break;
                        }
                    }
                    System.out.println("yes");
                }
                else{
                    for(int j=0; j<100; j++){
                        if(MainGame.Player[j].role==Roles.detective){
                            MainGame.Player[j].information[k].name=MainGame.Player[i].name;
                            MainGame.Player[j].information[k].role=Roles.villager;
                            k++;
                            break;
                        }
                    }
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
            if(MainGame.Player[i].name==name){
                MainGame.Player[i].lives+=1;
                break;
            }
        }
        return MainGame.Player[i];
    }
}