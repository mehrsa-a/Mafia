public class Freemason extends UsefulVillagers{
    public Freemason(String name, Roles role) {
        super(name, role);
    }
    private static Freemason freemason=new Freemason(" ", Roles.freemason);
    public static Freemason getInstance(){
        return freemason;
    }
    @Override
    public void action(String name) {
        for(int i=0; i<MainGame.size; i++){
            if(MainGame.Player[i].name.equals(name)){
                if(MainGame.Player[i].role==Roles.mafia||MainGame.Player[i].role==Roles.silencer||MainGame.Player[i].role==Roles.godfather||MainGame.Player[i].role==Roles.bomb){
                    MainGame.kill(freemason);
                }
            }
        }
    }
}