package gui;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * Created by Administrator on 2019/4/21 0021.
 */
public class MyPanel extends JPanel implements PropertyChangeListener {
    private final MapImp map;
    private JComboBox<Integer> start;
    private JComboBox<Integer> end;
    private MapImp.MyMapNode startNode;
    private MapImp.MyMapNode endNode;

    public MyPanel(MapImp map) {
        this.map = map;
        setPreferredSize(map.getSize());
        map.addObserve(this);
    }

    public MapImp.MyMapNode getStartNode() {
        return startNode;
    }

    public MapImp.MyMapNode getEndNode() {
        return endNode;
    }

    public String getNodeStr_s() {
        return startNode.getLocation();
    }

    public void setStartNode() {
        startNode = map.getNode(start.getSelectedIndex(), end.getSelectedIndex());
    }

    public String getNodeStr_e() {
        return endNode.getLocation();
    }

    public void setEndNode() {
        endNode = map.getNode(start.getSelectedIndex(), end.getSelectedIndex());
    }


    private JComboBox<Integer> getComboBox(int size) {
        JComboBox<Integer> box = new JComboBox<Integer>();
        for (int i = 0; i < size; i++) {
            box.addItem(i);
        }
        return box;
    }

    public JComboBox<Integer> getComboBox_x() {
        if (start == null)
            start = getComboBox((int) map.getW_h().getX());
        return start;
    }

    public JComboBox<Integer> getComboBox_y() {
        if (end == null)
            end = getComboBox((int) map.getW_h().getY());
        return end;
    }

    @Override
    protected void paintComponent(Graphics g) {
//        super.paintComponent(g);
        map.draw((Graphics2D) g);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        repaint(10);
    }

    public void updateMap() {
        map.reload();
    }
}
