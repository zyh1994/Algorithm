package gui;

import algorithm.A_Star;
import algorithm.Algorithm;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Administrator on 2019/4/20 0020.
 */
public class MyFrame extends JFrame {
    private Algorithm algorithm=new A_Star();
    public MyFrame() throws HeadlessException {
        init();
    }

    private void init() {
        MyPanel panel = new MyPanel(new MapImp(30, 30));
        setLayout(new BorderLayout());
        add(panel, BorderLayout.CENTER);
        setSize(1024, 768);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        add(toolBar(panel), BorderLayout.NORTH);
        setTitle("当前算法为:\t"+algorithm.toString());
    }

    private JComponent toolBar(MyPanel panel) {
        JPanel rootPanel = new JPanel();
        JButton btn = new JButton("更新地图");
        btn.addActionListener((e) -> {
            System.out.println("更新地图...");
            panel.updateMap();
            repaint();
        });
        rootPanel.add(btn);
        btn = new JButton("切换算法");
        btn.addActionListener((e) -> {
            switchAlgorithm();
        });
        rootPanel.add(btn);

        JToolBar bar=new JToolBar();
        JLabel label=new JLabel("坐标x:");
        bar.add(label);
        bar.add(panel.getComboBox_x());

        label=new JLabel("坐标y:");
        bar.add(label);
        bar.add(panel.getComboBox_y());
        rootPanel.add(bar);
        String a="起点:",b="终点:";
        JButton start = new JButton(a);
        start.addActionListener((e) -> {
            panel.setStartNode();
            start.setText(a+panel.getNodeStr_s());
        });
        rootPanel.add(start);

        JButton end = new JButton(b);
        end.addActionListener((e) -> {
            panel.setEndNode();
            end.setText(b+panel.getNodeStr_e());
        });
        rootPanel.add(end);
        btn = new JButton("执行");
        btn.addActionListener((e) -> {
            System.out.println("采用算法:\t"+algorithm);
            System.out.println("起点:\t"+panel.getNodeStr_s());
            System.out.println("终点:\t"+panel.getNodeStr_e());
            algorithm.calculate(panel.getStartNode(),panel.getEndNode());
            repaint();
        });
        rootPanel.add(btn);
        return rootPanel;
    }

    private void switchAlgorithm(){
        System.out.println("切换算法为："+algorithm);
        setTitle("当前算法为:\t"+algorithm.toString()+Math.random());
    }

}
