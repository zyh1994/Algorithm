package algorithm;

import gui.MapImp.MyMapNode;
import gui.MyMapNodeComparator;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Administrator on 2019/4/21 0021.
 */
public class A_Star implements Algorithm {
    private List<MyMapNode> openList = new LinkedList<>();
    private List<MyMapNode> closeList = new ArrayList<>();
    private MyMapNodeComparator comparator = new MyMapNodeComparator();

    @Override
    public MyMapNode calculate(MyMapNode start, MyMapNode end) {
        addToOpen(start);
        while (!openList.isEmpty()) {
            //获取f最小的节点
            MyMapNode current = getTheBestNode();
            if (current.equals(end)) {
                over(current);
                return current;
            }
            openList.remove(current);
            addToClose(current);
            List<MyMapNode> neighbors = current.getNeighbors();
            for (MyMapNode neighbor : neighbors) {
                if(closeList.contains(neighbor)||neighbor.isWall())
                    continue;
                double temG = current.getG() + dist_between(current, neighbor);
                if (!openList.contains(neighbor)) {
                    addToOpen(neighbor);
                } else if (temG >= neighbor.getG()) {
                    continue;
                }
                neighbor.setG(temG);
                neighbor.setParent(current);
                neighbor.setH(end);
                neighbor.setF();
            }
        }
        if(openList.isEmpty()){
            System.out.println("没有结果");
        }
        return end;

    }

    private double dist_between(MyMapNode current, MyMapNode neighbor) {
        return 1;
    }

    private MyMapNode getTheBestNode() {
        Collections.sort(openList, comparator);
        return openList.get(0);
    }

    @Override
    public String toString() {
        return "A*算法";
    }

    private void addToClose(MyMapNode node) {
        node.setColor(Color.red);
        closeList.add(node);
    }

    private void addToOpen(MyMapNode node) {
        node.setColor(Color.green);
        openList.add(node);
    }

    private void over(MyMapNode node) {
        while(node!=null){
            node.setColor(Color.BLUE);
            node=node.getParent();
        }
    }
}
