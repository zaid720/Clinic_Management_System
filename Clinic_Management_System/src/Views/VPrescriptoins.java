package Views;

import Controls.CtlPrescriptions;
import Controls.CtlVisit;
import Models.MPrescriptions;
import Models.MVisit;
import cell.TableActionCellEditor;
import cell.TableActionCellRender;
import cell.TableActionEvent;
import direction.Direction;
import java.awt.ComponentOrientation;
import java.awt.Frame;
import javaswingdev.card.ModelCard;
import javaswingdev.system.SystemColor;
import javax.swing.RowFilter;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

public class VPrescriptoins extends javax.swing.JPanel {

    private CtlPrescriptions prescription = new CtlPrescriptions();
    private DefaultTableModel model;
    private VMessage message = new VMessage((Frame) SwingUtilities.getWindowAncestor(VPrescriptoins.this), true);
    private TableRowSorter<DefaultTableModel> rowSorter;

    public VPrescriptoins() {
        initComponents();
        init();
    }

    private void init() {
        Direction.applyComponentOrientationRecursively(this, ComponentOrientation.RIGHT_TO_LEFT);
        model = (DefaultTableModel) tblPrescriptions.getModel();
        rowSorter = new TableRowSorter<>(model);
        tblPrescriptions.setRowSorter(rowSorter);
        roundPanel1.setColors(SystemColor.VPrescriptoins1, SystemColor.VPrescriptoins2);
        TableActionEvent event = new TableActionEvent() {
            @Override
            public void onUpdate(int row) {
                if (row >= 0) {
                    MPrescriptions mPrescriptions = prescription.getById(Integer.parseInt(tblPrescriptions.getValueAt(row, 0).toString()));
                    if (mPrescriptions != null) {
                        VUpdatePrescriptions update = new VUpdatePrescriptions((Frame) SwingUtilities.getWindowAncestor(VPrescriptoins.this), true, mPrescriptions);
                        setLayout(null);
                        update.setVisible(true);
                        setData();
                    }
                }
            }

            @Override
            public void onRemove(int row) {
                if (tblPrescriptions.isEditing()) {
                    tblPrescriptions.getCellEditor().stopCellEditing();
                }
                VCheckOfRemove check = new VCheckOfRemove((Frame) SwingUtilities.getWindowAncestor(VPrescriptoins.this), true);
                check.setLblTitle("هل انت متاكد من الحذف");
                check.setVisible(true);
                if (check.isCheck()) {
                    try {
                        prescription.remove(Integer.parseInt(tblPrescriptions.getValueAt(row, 0).toString()));
                        message.setLblTitle("تم الحذف بنجاح");
                        message.setVisible(true);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                    setData();
                }
            }
        };
        tblPrescriptions.getColumnModel().getColumn(tblPrescriptions.getColumnCount() - 1).setCellRenderer(new TableActionCellRender());
        tblPrescriptions.getColumnModel().getColumn(tblPrescriptions.getColumnCount() - 1).setCellEditor(new TableActionCellEditor(event));

        tblPrescriptions.fixTable(jScrollPane1);
        setData();
    }

    private void setData() {
        try {
            model = (DefaultTableModel) tblPrescriptions.getModel();
            model.setRowCount(0);
            for (MPrescriptions read : prescription.getAll()) {
                model.addRow(new Object[]{read.getId(), read.getNumVisit(), read.getMedication(), read.getDosage(), read.getDuration(), read.getCreated_at().toLocalDate()});
            }
            cardCountPrescriptions.setData(new ModelCard(null, null, null, prescription.count().toString(), "عدد الوصفات الطبية"));
        } catch (Exception ex) {
            System.out.println("Not found data");
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        cardCountPrescriptions = new javaswingdev.card.Card();
        roundPanel1 = new javaswingdev.swing.RoundPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblPrescriptions = new javaswingdev.swing.table.Table();
        btnAdd = new com.raven.swing.Button();
        txtSearch = new com.raven.swing.MyTextField();

        setOpaque(false);

        cardCountPrescriptions.setColor1(new java.awt.Color(38, 142, 205));
        cardCountPrescriptions.setColor2(new java.awt.Color(2, 48, 71));
        cardCountPrescriptions.setIcon(javaswingdev.GoogleMaterialDesignIcon.LOCAL_PHARMACY);

        roundPanel1.setBackground(new java.awt.Color(255, 255, 255));
        roundPanel1.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 5, 5, 5));
        roundPanel1.setRound(10);

        tblPrescriptions.setAutoCreateRowSorter(true);
        tblPrescriptions.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "#", "رقم الزيارة", "الدواء", "الجرعة", "مدة الجرعة", "تاريخ الإنشاء", "الحدث"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tblPrescriptions);

        javax.swing.GroupLayout roundPanel1Layout = new javax.swing.GroupLayout(roundPanel1);
        roundPanel1.setLayout(roundPanel1Layout);
        roundPanel1Layout.setHorizontalGroup(
            roundPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(roundPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 788, Short.MAX_VALUE)
                .addContainerGap())
        );
        roundPanel1Layout.setVerticalGroup(
            roundPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(roundPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        btnAdd.setBackground(new java.awt.Color(38, 142, 205));
        btnAdd.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        btnAdd.setForeground(new java.awt.Color(255, 255, 255));
        btnAdd.setText("إضافة زيارة");
        btnAdd.setFont(new java.awt.Font("Simplified Arabic", 1, 14)); // NOI18N
        btnAdd.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddActionPerformed(evt);
            }
        });

        txtSearch.setForeground(new java.awt.Color(0, 0, 0));
        txtSearch.setPrefixIcon(new javax.swing.ImageIcon(getClass().getResource("/Views/search.png"))); // NOI18N
        txtSearch.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtSearchKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(roundPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(cardCountPrescriptions, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(30, 30, 30))
            .addGroup(layout.createSequentialGroup()
                .addGap(48, 48, 48)
                .addComponent(btnAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(49, 49, 49)
                .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 339, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(13, 13, 13)
                .addComponent(cardCountPrescriptions, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(roundPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(20, 20, 20))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddActionPerformed
        VAddPrescriptions add = new VAddPrescriptions((Frame) SwingUtilities.getWindowAncestor(VPrescriptoins.this), true);
        setLayout(null);
        add.setVisible(true);
        setData();
    }//GEN-LAST:event_btnAddActionPerformed

    private void txtSearchKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSearchKeyReleased
        String searchText = txtSearch.getText();
        if (searchText.trim().length() == 0) {
            rowSorter.setRowFilter(null); // إظهار الكل
        } else {
            rowSorter.setRowFilter(RowFilter.regexFilter("(?i)" + searchText));
        }
    }//GEN-LAST:event_txtSearchKeyReleased

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.raven.swing.Button btnAdd;
    private javaswingdev.card.Card cardCountPrescriptions;
    private javax.swing.JScrollPane jScrollPane1;
    private javaswingdev.swing.RoundPanel roundPanel1;
    private javaswingdev.swing.table.Table tblPrescriptions;
    private com.raven.swing.MyTextField txtSearch;
    // End of variables declaration//GEN-END:variables
}
