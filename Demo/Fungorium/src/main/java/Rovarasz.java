public class Rovarasz implements IPlaceable{
    vec2d currentPos;
    int energia=0;
    IFungorium fun;

    Rovarasz(IFungorium fun){
        this.fun = fun;
        currentPos=new vec2d(0,0);
    }
    void lepj(vec2d newpos) throws Exception {
        if(Math.abs(newpos.x-currentPos.x)>1 || Math.abs(newpos.y-currentPos.y)>1) throw new Exception("Túl nagy lépés");
        else {
            fun.ObjektumAtrak(this, newpos);
        }
        energia--;
    }
    void

}
