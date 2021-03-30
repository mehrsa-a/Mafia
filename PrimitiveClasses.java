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

    String name="";
    Roles role;
    int DayVoted=0;
    boolean silenced=false;
    Players[] information= new Players[100];
    int lives=2;

    //constructor
    public Players(String name, Roles role){
        this.name=name;
        this.role=role;

    }

    //every player got a vote everyday
    public void DayVoting(Players name){
        name.DayVoted+=1;
    }

    //players has no features in common except DayVoting

}