package Views;

import Controls.CtlPatient;
import Models.MPatient;
import cell.TableActionCellEditor;
import cell.TableActionCellRender;
import cell.TableActionEvent;
import direction.Direction;
import java.awt.ComponentOrientation;
import java.awt.Frame;
import javaswingdev.card.ModelCard;
import javax.swing.RowFilter;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

public class VPatient extends javax.swing.JPanel {

    private CtlPatient patient = new CtlPatient();
    private VMessage message = new VMessage((Frame) SwingUtilities.getWindowAncestor(VPatient.this), true);
    private DefaultTableModel model;
    private TableRowSorter<DefaultTableModel> rowSorter;

    public VPatient() {
        initComponents();
        init();
    }

    private void init() {
        Direction.applyComponentOrientationRecursively(this, ComponentOrientation.RIGHT_TO_LEFT);
        model = (DefaultTableModel) tblPatients.getModel();
        rowSorter = new TableRowSorter<>(model);
        tblPatients.setRowSorter(rowSorter);
        TableActionEvent event = new TableActionEvent() {
            @Override
            public void onUpdate(int row) {
                if (row >= 0) {
                    MPatient mPatient = patient.getById(Integer.parseInt(tblPatients.getValueAt(row, 0).toString()));
                    VUpdatePatient update = new VUpdatePatient((Frame) SwingUtilities.getWindowAncestor(VPatient.this), true, mPatient);
                    setLayout(null);
                    update.setVisible(true);
                    setData();
                }
            }

            @Override
            public void onRemove(int row) {
                if (row >= 0) {
                    if (tblPatients.isEditing()) {
                        tblPatients.getCellEditor().stopCellEditing();
                    }
                    VCheckOfRemove check = new VCheckOfRemove((Frame) SwingUtilities.getWindowAncestor(VPatient.this), true);
                    check.setLblTitle("هل انت متاكد من الحذف");
                    check.setVisible(true);
                    if (check.isCheck()) {
                        try {
                            patient.remove(Integer.parseInt(tblPatients.getValueAt(row, 0).toString()));
                            message.setLblTitle("تم الحذف بنجاح");
                            message.setVisible(true);
                            setData();
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }
                }
            }
        };
        tblPatients.getColumnModel().getColumn(tblPatients.getColumnCount() - 1).setCellRenderer(new TableActionCellRender());
        tblPatients.getColumnModel().getColumn(tblPatients.getColumnCount() - 1).setCellEditor(new TableActionCellEditor(event));

        tblPatients.fixTable(jScrollPane1);
        setData();
    }

    private void setData() {
        try {
            model = (DefaultTableModel) tblPatients.getModel();
            model.setRowCount(0);
            for (MPatient read : patient.getAll()) {
                model.addRow(new Object[]{read.getId(), read.getFull_name(), read.getAge(), read.getGender(), read.getPhone(), read.getAddress(), read.getMedicat(), read.getCreated_at().toLocalDate()});
            }
            cardCountPatients.setData(new ModelCard(null, null, null, patient.count().toString(), "عدد المرضى"));
        } catch (Exception ex) {
            System.out.println("Not found data");
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        cardCountPatients = new javaswingdev.card.Card();
        roundPanel1 = new javaswingdev.swing.RoundPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblPatients = new javaswingdev.swing.table.Table();
        btnAdd = new com.raven.swing.Button();
        txtSearch = new com.raven.swing.MyTextField();

        setOpaque(false);

        cardCountPatients.setIcon(javaswingdev.GoogleMaterialDesignIcon.LOCAL_HOSPITAL);

        roundPanel1.setBackground(new java.awt.Color(255, 255, 255));
        roundPanel1.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 5, 5, 5));
        roundPanel1.setRound(10);

        tblPatients.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "#", "الاسم", "العمر", "الجنس", "الرقم", "العنوان", "وصفة العلاج", "تاريخ الإنشاء", "الحدث"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tblPatients);

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

        btnAdd.setBackground(new java.awt.Color(102, 153, 255));
        btnAdd.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        btnAdd.setForeground(new java.awt.Color(255, 255, 255));
        btnAdd.setText("إضافة مريض");
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
                .addGap(48, 48, 48)
                .addComponent(btnAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(57, 57, 57)
                .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 339, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(cardCountPatients, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(roundPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(30, 30, 30))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(13, 13, 13)
                .addComponent(cardCountPatients, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
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
        VAddPatient add = new VAddPatient((Frame) SwingUtilities.getWindowAncestor(VPatient.this), true);
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
    private javaswingdev.card.Card cardCountPatients;
    private javax.swing.JScrollPane jScrollPane1;
    private javaswingdev.swing.RoundPanel roundPanel1;
    private javaswingdev.swing.table.Table tblPatients;
    private com.raven.swing.MyTextField txtSearch;
    // End of variables declaration//GEN-END:variables
}
