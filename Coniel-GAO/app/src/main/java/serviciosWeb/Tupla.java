package serviciosWeb;
/**
 * Created by Jhonsson on 25/10/2014.
 */
public class Tupla<X, Y> {
    public final X x;
    public final Y y;
    public Tupla(X x, Y y) {
        this.x = x;
        this.y = y;
    }

    public X getKey(){
        return this.x;
    }

    public Y getValue(){
        return this.y;
    }
}
