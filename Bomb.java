public class Bomb extends UsefulMafias{
    public Bomb(String name, Roles role) {
        super(name, Roles.bomb);
    }
    @Override
    public void action(String name) {
        for(int i=0; i<MainGame.size; i++){
            if(MainGame.Player[i].name.equals(name)){
                MainGame.kill(MainGame.Player[i]);
            }
        }
    }
}