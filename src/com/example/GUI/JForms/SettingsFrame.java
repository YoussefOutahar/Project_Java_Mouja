package com.example.GUI.JForms;

import javax.swing.JColorChooser;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import com.example.GUI.Components.Buttons.MoujaButton;
import com.example.GUI.Components.Buttons.SwitchButton.EventSwitchSelected;
import com.example.GUI.Components.Buttons.SwitchButton.SwitchButton;
import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatLightLaf;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;

public class SettingsFrame extends JFrame{
    public SettingsFrame() {
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) ((dimension.getWidth() - getWidth()) / 2);
        int y = (int) ((dimension.getHeight() - getHeight()) / 2);
        initComponents();
        setLocation(x, y);
        initIcons();
        if(UIManager.getLookAndFeel() instanceof FlatLightLaf) {
            changeThemeButton.setSelected(false);
        }
        else {
            changeThemeButton.setSelected(true);
        }
        initButtonListeners();
    }

    private void initIcons() {
        exitButton.setIcon(new javax.swing.ImageIcon(
            getClass().getResource("/com/example/GUI/resources/black_icons/cross.png")));
        changeColorsButton.setIcon(new javax.swing.ImageIcon(
            getClass().getResource("/com/example/GUI/resources/black_icons/palette.png")));
        aboutButton.setIcon(new javax.swing.ImageIcon(
            getClass().getResource("/com/example/GUI/resources/black_icons/icons8_info_16px_1.png")));
        // changeThemeButton.setIcon(new javax.swing.ImageIcon(
        //     getClass().getResource("/com/example/GUI/resources/img/theme.png")));
    }

    private void initButtonListeners(){
        exitButton.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dispose();
            }
        });
        changeColorsButton.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Color color = JColorChooser.showDialog(null, "Choose Background Color", Color.WHITE);
                if(color == null) return;
                try {
                    MainFrame.changeColors(color);
                } catch (Exception e) {}
                try {
                    AdminControlFrame.changeColors(color);
                } catch (Exception e) {}
                changeColors(color);
                SwingUtilities.updateComponentTreeUI(SettingsFrame.this);
            }
        });

        changeThemeButton.addEventSelected(new EventSwitchSelected(){
            @Override
            public void onSelected(boolean selected) {
                if(selected){
                    try {
                        UIManager.setLookAndFeel( new FlatDarkLaf() );
                    } catch( Exception ex ) {
                        System.err.println( "Failed to initialize LaF" );
                    }
                    SwingUtilities.updateComponentTreeUI(SettingsFrame.this);
                    try {
                        MainFrame.changeColors(MainFrame.color);
                        MainFrame.updateFrame();
                    } catch (Exception e) {}
                    try {
                        AdminControlFrame.changeColors(AdminControlFrame.color);
                        AdminControlFrame.updateFrame();
                    } catch (Exception e) {}
                }else{
                    try {
                        UIManager.setLookAndFeel( new FlatLightLaf() );
                    } catch( Exception ex ) {
                        System.err.println( "Failed to initialize LaF" );
                    }
                    SwingUtilities.updateComponentTreeUI(SettingsFrame.this);
                    try {
                        MainFrame.changeColors(MainFrame.color);
                        MainFrame.updateFrame();
                    } catch (Exception e) {}
                    try {
                        AdminControlFrame.changeColors(AdminControlFrame.color);
                        AdminControlFrame.updateFrame();
                    } catch (Exception e) {}
                }
            }});
    }

    public static void changeColors(Color color){
        changeThemeButton.changeColor(color.brighter());
        changeColorsButton.changeButtonColor(color.brighter(), color.darker());
        exitButton.changeButtonColor(color.brighter(), color.darker());
        aboutButton.changeButtonColor(color.brighter(), color.darker());
    }

    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        changeColorsButton = new MoujaButton("",45,60,Color.RED,Color.gray);
        changeThemeButton = new SwitchButton();
        exitButton = new MoujaButton("",30,30,Color.RED,Color.gray);
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        aboutButton = new MoujaButton("",30,30,Color.RED,Color.gray);

        setUndecorated(true);

        //setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel1.setText("Settings");

        jLabel2.setText("Change Colors :");
        jLabel3.setText("Change Theme :");

        exitButton.setPreferredSize(new java.awt.Dimension(30, 30));

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        aboutButton.setPreferredSize(new java.awt.Dimension(30, 30));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(exitButton, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, 158, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(changeColorsButton, javax.swing.GroupLayout.DEFAULT_SIZE, 66, Short.MAX_VALUE)
                            .addComponent(changeThemeButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addContainerGap())
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(aboutButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(exitButton, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(3, 3, 3)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(33, 33, 33)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(changeColorsButton, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(changeThemeButton, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 43, Short.MAX_VALUE)
                .addComponent(aboutButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }                   

    public static void createSettingsPage(Color color) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new SettingsFrame().setVisible(true);
                changeColors(color);
            }
        });
    }

    private static MoujaButton changeColorsButton;
    private static SwitchButton changeThemeButton;
    private static MoujaButton exitButton;
    private static MoujaButton aboutButton;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JSeparator jSeparator1;               
}
