package gui;

import java.util.Comparator;

/**
 * Created by Administrator on 2019/4/21 0021.
 */
public class MyMapNodeComparator implements Comparator<MapImp.MyMapNode> {
    @Override
    public int compare(MapImp.MyMapNode o1, MapImp.MyMapNode o2) {
        return o1.getF() > o2.getF() ? 1 : -1;
    }


}
