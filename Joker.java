public class Joker extends Players{
    private Joker(String name, Roles role) {
        super(name, role);
    }
    private static Joker joker=new Joker(" ", Roles.Joker);
    public static Joker getInstance(){
        return joker;
    }
}