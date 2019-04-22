package gui;

import java.awt.*;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by Administrator on 2019/4/21 0021.
 */
public class MapImp {
    private java.util.List<MyMapNode> nodes;
    public static final int si = 20;
    private int lw = 0;
    private int lh = 0;
    private Point w_h=new Point();
    private PropertyChangeSupport support=new PropertyChangeSupport(this);

    public MapImp(int w, int h) {
        this.lw = w;
        this.lh = h;
        this.nodes = new ArrayList<>(w * h);
        reload();
    }

    public Dimension getSize() {
        return new Dimension(lw * si, lh * si);
    }

    public Point getW_h() {
        w_h.setLocation(lw, lh);
        return w_h;
    }


    public void reload() {
        if (nodes != null) {
            nodes.clear();
        }
        initNode();
        initNeighbor();
    }

    private void initNode() {
        for (int y = 0; y < lh; y++) {
            for (int x = 0; x < lw; x++) {
                nodes.add(new MyMapNode(x, y));
            }
        }
    }
    private void initNeighbor() {
        for (int y = 0; y < lh; y++) {
            for (int x = 0; x < lw; x++) {
                nodes.get(x + y * lh).initNeighbor(x, y);
            }
        }
    }

    public void draw(Graphics2D g2d) {
        for (MyMapNode node : nodes) {
            node.draw(g2d);
        }
    }

    public static double distance(MyMapNode a, MyMapNode b) {
        return Point.distance(a.shape.x, a.shape.y, b.shape.x, b.shape.y);
    }

    public MyMapNode getNode(int x, int y) {
        int index = x + y * lh;
        return nodes.get(index);
    }
    public void addObserve(PropertyChangeListener listener){
        support.addPropertyChangeListener(listener);
    }
    public class MyMapNode {
        private MyMapNode parent;
        private java.util.List<MyMapNode> neighbors = new ArrayList<>();
        private Rectangle shape = new Rectangle();
        private Color color = Color.white;
        private boolean isWall = false;
        private int indexX,indexY;
        private double g = 0;
        private double h = 0;
        private double f = 0;

        public MyMapNode(int x, int y) {
            indexX=x;
            indexY=y;
            this.shape.setBounds(x * si, y * si, si, si);
            this.isWall = Math.random() > 0.8;
            if (isWall)
                this.color = Color.black;

        }

        public void initNeighbor(int x, int y) {
            if (y == 0) {
                if (x == 0) {//左上角三个
                    neighbors.add(MapImp.this.getNode(x, y + 1));
                    neighbors.add(MapImp.this.getNode(x + 1, y + 1));
                    neighbors.add(MapImp.this.getNode(x + 1, y));
                } else if (x == lw - 1) {
                    //右上角三个
                    neighbors.add(MapImp.this.getNode(x - 1, y));
                    neighbors.add(MapImp.this.getNode(x - 1, y + 1));
                    neighbors.add(MapImp.this.getNode(x, y + 1));
                } else {
                    //上边5个
                    neighbors.add(MapImp.this.getNode(x - 1, y));
                    neighbors.add(MapImp.this.getNode(x + 1, y));
                    neighbors.add(MapImp.this.getNode(x, y + 1));
                    neighbors.add(MapImp.this.getNode(x - 1, y + 1));
                    neighbors.add(MapImp.this.getNode(x + 1, y + 1));
                }
            } else if (y == lh - 1) {
                if (x == 0) {
                    //左下角三个
                    neighbors.add(MapImp.this.getNode(x, y - 1));
                    neighbors.add(MapImp.this.getNode(x + 1, y - 1));
                    neighbors.add(MapImp.this.getNode(x + 1, y));
                } else if (x == lw - 1) {
                    //左下角三个
                    neighbors.add(MapImp.this.getNode(x - 1, y));
                    neighbors.add(MapImp.this.getNode(x - 1, y - 1));
                    neighbors.add(MapImp.this.getNode(x, y - 1));
                } else {
                    //下边5个
                    neighbors.add(MapImp.this.getNode(x - 1, y));
                    neighbors.add(MapImp.this.getNode(x + 1, y));
                    neighbors.add(MapImp.this.getNode(x, y - 1));
                    neighbors.add(MapImp.this.getNode(x - 1, y - 1));
                    neighbors.add(MapImp.this.getNode(x + 1, y - 1));
                }
            } else {
                //左边界五个
                if (x == 0) {
                    neighbors.add(MapImp.this.getNode(x, y + 1));
                    neighbors.add(MapImp.this.getNode(x, y - 1));
                    neighbors.add(MapImp.this.getNode(x + 1, y - 1));
                    neighbors.add(MapImp.this.getNode(x + 1, y + 1));
                    neighbors.add(MapImp.this.getNode(x + 1, y));
                } else if (x == lw - 1) {
                    //右边界五个
                    neighbors.add(MapImp.this.getNode(x, y + 1));
                    neighbors.add(MapImp.this.getNode(x, y - 1));
                    neighbors.add(MapImp.this.getNode(x - 1, y - 1));
                    neighbors.add(MapImp.this.getNode(x - 1, y + 1));
                    neighbors.add(MapImp.this.getNode(x - 1, y));
                } else {
                    //中间八个
                    neighbors.add(MapImp.this.getNode(x, y + 1));
                    neighbors.add(MapImp.this.getNode(x, y - 1));
                    neighbors.add(MapImp.this.getNode(x + 1, y));
                    neighbors.add(MapImp.this.getNode(x - 1, y));

                    neighbors.add(MapImp.this.getNode(x + 1, y - 1));
                    neighbors.add(MapImp.this.getNode(x + 1, y + 1));

                    neighbors.add(MapImp.this.getNode(x - 1, y - 1));
                    neighbors.add(MapImp.this.getNode(x - 1, y + 1));
                }
            }
        }

        public double distance(MyMapNode b) {
            return MapImp.distance(this, b);
        }

        public MyMapNode getParent() {
            return parent;
        }

        public boolean isWall() {
            return isWall;
        }

        public double getG() {
            return g;
        }

        public double getH() {
            return h;
        }

        public double getF() {
            return f;
        }

        public void setParent(MyMapNode parent) {
            this.parent = parent;
        }

        public void setColor(Color color) {
            Color oldColor=this.color;
            this.color = color;
            support.firePropertyChange("颜色更改",oldColor,color);
        }

        public void setG(double g) {
            this.g = g;
        }

        public void setH(MyMapNode end) {
            this.h = distance(end);
        }

        public void setF() {
            this.f = g + h;
        }

        public Color getColor() {
            return color;
        }

        public Rectangle getShape() {
            return shape;
        }

        @Override
        public boolean equals(Object obj) {
            boolean equal = false;
            if (obj instanceof MyMapNode) {
                equal = this.shape.equals(((MyMapNode) obj).shape);
            }
            return equal;
        }

        public void draw(Graphics2D g2d) {
            g2d.setPaint(color);
            g2d.fillRect(shape.x, shape.y, shape.width, shape.height);
        }


        @Override
        public String toString() {
            return "shape:\t" + shape.toString() + "\tcolor:\t" + color.toString();
        }
        public String getLocation(){
            return "("+indexX+","+indexY+")";
        }

        public List<MyMapNode> getNeighbors() {
            return neighbors;
        }
    }


}
