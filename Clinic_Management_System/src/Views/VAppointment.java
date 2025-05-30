package Views;

import Controls.CtlAppointment;
import Models.MAppointment;
import cell.TableActionCellEditor;
import cell.TableActionCellRender;
import cell.TableActionEvent;
import direction.Direction;
import java.awt.ComponentOrientation;
import java.awt.Frame;
import javaswingdev.card.ModelCard;
import javaswingdev.system.SystemColor;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

public class VAppointment extends javax.swing.JPanel {

    private CtlAppointment appointment = new CtlAppointment();
    private DefaultTableModel model;
    private VMessage message = new VMessage((Frame) SwingUtilities.getWindowAncestor(VAppointment.this), true);
    MAppointment mAppointment;

    public VAppointment() {
        initComponents();
        init();
    }

    private void init() {
        Direction.applyComponentOrientationRecursively(this, ComponentOrientation.RIGHT_TO_LEFT);
        roundPanel1.setColors(SystemColor.VAPPOINTMENT1, SystemColor.VAPPOINTMENT2);
        TableActionEvent event = new TableActionEvent() {
            @Override
            public void onUpdate(int row) {
                if (row >= 0) {
                    mAppointment = appointment.getById(Integer.parseInt(tblAppointments.getValueAt(row, 0).toString()));
//                    mAppointment.setPatient_name(tblAppointments.getValueAt(row, 1).toString());
//                    mAppointment.setDoctor_name(tblAppointments.getValueAt(row, 2).toString());
//                    String dateStr = tblAppointments.getValueAt(row, 3).toString();
//                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
//                    LocalDate date = LocalDate.parse(dateStr, formatter);
//                    mAppointment.setAppointment_date(date);
//                    Object value = tblAppointments.getValueAt(row, 4);
//                    DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("HH:mm:ss");
//                    mAppointment.setAppointment_time(LocalTime.parse(value.toString(), formatter1));
//                    mAppointment.setStatus(tblAppointments.getValueAt(row, 5).toString());
                    VUpdateAppointment update = new VUpdateAppointment((Frame) SwingUtilities.getWindowAncestor(VAppointment.this), true, mAppointment);
                    setLayout(null);
                    update.setVisible(true);
                    setData();
                }
            }

            @Override
            public void onRemove(int row) {
                if (tblAppointments.isEditing()) {
                    tblAppointments.getCellEditor().stopCellEditing();
                }
                VCheckOfRemove check = new VCheckOfRemove((Frame) SwingUtilities.getWindowAncestor(VAppointment.this), true);
                check.setLblTitle("هل انت متاكد من الحذف");
                check.setVisible(true);
                if (check.isCheck()) {
                    try {
                        appointment.remove(Integer.parseInt(tblAppointments.getValueAt(row, 0).toString()));
                        message.setLblTitle("تم الحذف بنجاح");
                        message.setVisible(true);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                    setData();
                }
            }
        };
        tblAppointments.getColumnModel().getColumn(tblAppointments.getColumnCount() - 1).setCellRenderer(new TableActionCellRender());
        tblAppointments.getColumnModel().getColumn(tblAppointments.getColumnCount() - 1).setCellEditor(new TableActionCellEditor(event));

        tblAppointments.fixTable(jScrollPane1);
        setData();
    }

    private void setData() {
        try {
            model = (DefaultTableModel) tblAppointments.getModel();
            model.setRowCount(0);
            for (MAppointment read : appointment.getAll()) {
                model.addRow(new Object[]{read.getId(), read.getPatient_name(), read.getDoctor_name(), read.getAppointment_date(), read.getAppointment_time(), read.getStatus(), read.getCreated_at().toLocalDate()});
            }
            cardCountAppointments.setData(new ModelCard(null, null, null, appointment.count().toString(), "عدد المواعيد"));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        cardCountAppointments = new javaswingdev.card.Card();
        roundPanel1 = new javaswingdev.swing.RoundPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblAppointments = new javaswingdev.swing.table.Table();
        btnAdd = new com.raven.swing.Button();

        setOpaque(false);

        cardCountAppointments.setColor1(new java.awt.Color(95, 211, 226));
        cardCountAppointments.setColor2(new java.awt.Color(26, 166, 170));
        cardCountAppointments.setIcon(javaswingdev.GoogleMaterialDesignIcon.ACCESS_TIME);

        roundPanel1.setBackground(new java.awt.Color(255, 255, 255));
        roundPanel1.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 5, 5, 5));
        roundPanel1.setRound(10);

        tblAppointments.setAutoCreateRowSorter(true);
        tblAppointments.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "#", "اسم المريض", "اسم الدكتور", "تاريخ الموعد", "وقت الموعد", "حالة الموعد", "تاريخ الإنشاء", "الحدث"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tblAppointments);

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

        btnAdd.setBackground(new java.awt.Color(26, 166, 170));
        btnAdd.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        btnAdd.setForeground(new java.awt.Color(255, 255, 255));
        btnAdd.setText("إضافة موعد");
        btnAdd.setFont(new java.awt.Font("Simplified Arabic", 1, 14)); // NOI18N
        btnAdd.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddActionPerformed(evt);
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
                    .addComponent(cardCountAppointments, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(30, 30, 30))
            .addGroup(layout.createSequentialGroup()
                .addGap(48, 48, 48)
                .addComponent(btnAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(13, 13, 13)
                .addComponent(cardCountAppointments, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(roundPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(20, 20, 20))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddActionPerformed
        VAddAppointment add = new VAddAppointment((Frame) SwingUtilities.getWindowAncestor(VAppointment.this), true);
        setLayout(null);
        add.setVisible(true);
        setData();
    }//GEN-LAST:event_btnAddActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.raven.swing.Button btnAdd;
    private javaswingdev.card.Card cardCountAppointments;
    private javax.swing.JScrollPane jScrollPane1;
    private javaswingdev.swing.RoundPanel roundPanel1;
    private javaswingdev.swing.table.Table tblAppointments;
    // End of variables declaration//GEN-END:variables
}
