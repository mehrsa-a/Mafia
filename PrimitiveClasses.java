enum Roles{

    Joker,

    villager,
    detective,
    doctor,
    bulletproof,

    mafia,
    godfather,
    silencer,

    unknown
}

class Players{

    String name;
    Roles role;
    int DayVoted=0;
    boolean silenced=false;
    Players[] information= new Players[100];
    int lives;
    {
        for(int i=0; i<100; i++){
            if(MainGame.Player[i].role==Roles.bulletproof){
                MainGame.Player[i].lives=2;
            }
            else{
                MainGame.Player[i].lives=1;
            }
        }
    }

    //constructor
    public Players(){

    }

    //mafia's should recognize each other but villagers don't khow each other
    {
        int k=0;
        for(int i=0; i<100; i++){
            if(MainGame.Player[i].role==Roles.mafia||MainGame.Player[i].role==Roles.godfather||MainGame.Player[i].role==Roles.silencer){
                for(int j=0; j<100; j++){
                    if(MainGame.Player[j].role==Roles.mafia||MainGame.Player[j].role==Roles.godfather||MainGame.Player[j].role==Roles.silencer){
                        MainGame.Player[i].information[k].name=MainGame.Player[i].name;
                        MainGame.Player[i].information[k].role=Roles.mafia;
                        k++;
                    }
                    else{
                        MainGame.Player[i].information[k].name=MainGame.Player[i].name;
                        MainGame.Player[i].information[k].role=Roles.villager;
                        k++;
                    }
                }
            }
        }
    }

    //every player got a vote everyday
    public void DayVoting(Players name){
        name.DayVoted+=1;
    }

    //players has no features in common except DayVoting

}