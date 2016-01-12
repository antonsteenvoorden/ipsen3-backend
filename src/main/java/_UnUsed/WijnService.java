//package UnUsed;
//
//import model.Wijn;
//import service.BaseService;
//
//import java.util.Collection;
//
///**
// * Created by roger on 10-1-2016.
// */
//public class WijnService extends BaseService<Wijn> {
//    private final WijnDao dao;
//
//    public WijnService(WijnDao wijnDAO) {
//        this.dao = wijnDAO;
//    }
//
//    public Collection<Wijn> getAll() {
//        return dao.getAll();
//    }
//
//    public Wijn get(int id) {
//        return requireResult(dao.get(id));
//    }
//}
