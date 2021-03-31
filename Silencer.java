public class Silencer extends UsefulMafias{
    public Silencer(String name, Roles role) {
        super(name, Roles.silencer);
    }
    //silencer silence a player with this method
    @Override
    public void action(String name) {
        for(int i=0; i<100; i++){
            if(MainGame.Player[i].name==name){
                MainGame.Player[i].silenced=true;
                break;
            }
        }
    }
}