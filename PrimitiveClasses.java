enum Roles{

    Joker,

    villager,
    detective,
    doctor,
    bulletproof,

    mafia,
    godfather,
    silencer
}

class Players{

    String name;
    Roles role;
    int DayVoted=0;

    //constructor
    public Players(){

    }
    //every player got a vote everyday
    public void DayVoting(Players name){
        name.DayVoted+=1;
    }

    //players has no features in common except DayVoting

}
