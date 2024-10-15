import GKH.Khachhang;
import GKH.XLKH;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.border.EmptyBorder;

public class GUI_updateKH extends JFrame implements ActionListener {
    private JTextField tfSoTK, tfHoten, tfGT, tfSodu;
    private JComboBox<String> cbDiachi;
    private JButton btSearch, btUpdate;
    private final Connection cn;

    public GUI_updateKH() {
        setTitle("DAKH");
        setResizable(true);
        setSize(500, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        BuildGUI();
        XLKH xl = new XLKH();
        cn = xl.getCon();
    }

    private void BuildGUI() {
        JPanel pnLeft = new JPanel(new GridBagLayout());
        pnLeft.setBorder(new EmptyBorder(10, 10, 10, 10));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        // SoTK
        gbc.gridx = 0;
        gbc.gridy = 0;
        JLabel lbSoTK = new JLabel("Số tài khoản: ");
        pnLeft.add(lbSoTK, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        tfSoTK = new JTextField();
        tfSoTK.setPreferredSize(new Dimension(300, 30));
        pnLeft.add(tfSoTK, gbc);

        // Hoten
        gbc.gridx = 0;
        gbc.gridy = 1;
        JLabel lbHoten = new JLabel("Họ và tên: ");
        pnLeft.add(lbHoten, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        tfHoten = new JTextField();
        tfHoten.setPreferredSize(new Dimension(300, 30));
        pnLeft.add(tfHoten, gbc);

        // GT
        gbc.gridx = 0;
        gbc.gridy = 2;
        JLabel lbGT = new JLabel("Giới tính: ");
        pnLeft.add(lbGT, gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        tfGT = new JTextField();
        tfGT.setPreferredSize(new Dimension(300, 30));
        pnLeft.add(tfGT, gbc);

        // Diachi
        gbc.gridx = 0;
        gbc.gridy = 3;
        JLabel lbDiachi = new JLabel("Địa chỉ: ");
        pnLeft.add(lbDiachi, gbc);

        gbc.gridx = 1;
        gbc.gridy = 3;
        cbDiachi = new JComboBox<>(new String[]{"Hà Nội", "Đà Nẵng", "Cần Thơ"});
        cbDiachi.setPreferredSize(new Dimension(300, 30));
        pnLeft.add(cbDiachi, gbc);

        // Sodu
        gbc.gridx = 0;
        gbc.gridy = 4;
        JLabel lbSodu = new JLabel("Số dư: ");
        pnLeft.add(lbSodu, gbc);

        gbc.gridx = 1;
        gbc.gridy = 4;
        tfSodu = new JTextField();
        tfSodu.setPreferredSize(new Dimension(300, 30));
        pnLeft.add(tfSodu, gbc);

        // btSearch, btUpdate
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        JPanel pnLeftBottom = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        btSearch = new JButton("Tìm kiếm khách hàng");
        btSearch.addActionListener(this);
        btUpdate = new JButton("Cập nhật khách hàng");
        btUpdate.addActionListener(this);
        pnLeftBottom.add(btSearch);
        pnLeftBottom.add(btUpdate);
        pnLeft.add(pnLeftBottom, gbc);

        this.add(pnLeft);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btSearch) {
            searchCustomer();
        } else if (e.getSource() == btUpdate) {
            updateCustomer();
        }
    }
    //Tìm kiếm
    private void searchCustomer() {
        String searchSoTK = tfSoTK.getText().trim().toLowerCase();
        if (searchSoTK.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập số tài khoản!");
            return;
        }

        try {
            String query = "SELECT Hoten, GT, Diachi, Sodu FROM tbKH WHERE SoTK = ?";
            PreparedStatement pst = cn.prepareStatement(query);
            pst.setString(1, searchSoTK);

            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                tfHoten.setText(rs.getString("Hoten"));
                tfGT.setText(rs.getString("GT"));
                cbDiachi.setSelectedItem(rs.getString("Diachi"));
                tfSodu.setText(rs.getString("Sodu"));
            } else {
                JOptionPane.showMessageDialog(this, "Không tìm thấy khách hàng có số tài khoản: " + searchSoTK);
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Lỗi tìm kiếm khách hàng." + ex.getMessage());
        }
    }
    //Cập nhật
    private void updateCustomer() {
        String SoTK = tfSoTK.getText().trim().toLowerCase();
        if (SoTK.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập số tài khoản!");
            return;
        }
        String Hoten = tfHoten.getText().trim();
        String GT = tfGT.getText().trim();
        String Diachi = cbDiachi.getSelectedItem().toString();
        String Sodu = tfSodu.getText().trim();
        
        XLKH xl = new XLKH();
        boolean isUpdate = xl.updateKH(SoTK, new Khachhang(SoTK, Hoten, GT, Diachi, Sodu));
        
        if (isUpdate) {
            JOptionPane.showMessageDialog(null, "Cập nhật thành công!");
        } else {
            JOptionPane.showMessageDialog(null, "Cập nhật thất bại!");
        }
    }

    public static void main(String[] args) {
        new GUI_updateKH().setVisible(true);
    }
}
