package projekuas;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.FileNotFoundException;
import javax.swing.*;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;


public class dataPerpustakaan extends javax.swing.JFrame {
    Connection conn;
    int hitung;
    private DefaultTableModel DSiswa;
    private DefaultTableModel DBuku;
    private DefaultTableModel DPeminjaman;
    private DefaultTableModel DPengembalian;
    private DefaultTableModel Riwayat;
    
    public dataPerpustakaan() {
        initComponents();
        conn = Koneksi.getConnection();
        
        DSiswa = new DefaultTableModel();
        tblSiswa.setModel(DSiswa);
        DSiswa.addColumn("ID SISWA");
        DSiswa.addColumn("NAMA");
        DSiswa.addColumn("KELAS");
        
        DBuku = new DefaultTableModel();
        tblBuku.setModel(DBuku);
        DBuku.addColumn("ID BUKU");
        DBuku.addColumn("JUDUL");
        DBuku.addColumn("PENGARANG");
        DBuku.addColumn("KATEGORI");
        DBuku.addColumn("TAHUN TERBIT");
        
        DPeminjaman = new DefaultTableModel();
        tblDPinjam.setModel(DPeminjaman);
        DPeminjaman.addColumn("KODE PEMINJAMAN");
        DPeminjaman.addColumn("SISWA");
        DPeminjaman.addColumn("JUDUL");
        DPeminjaman.addColumn("TGL PEMINJAMAN");
        DPeminjaman.addColumn("TGL KEMBALI");
        
        DPengembalian = new DefaultTableModel();
        tblDKembali.setModel(DPengembalian);
        DPengembalian.addColumn("KODE PENGEMBALIAN");
        DPengembalian.addColumn("KODE PEMINJAMAN");
        DPengembalian.addColumn("TGL PENGEMBALIAN");
        DPengembalian.addColumn("TGL DIKEMBALIKAN");
        DPengembalian.addColumn("STATUS");
        
        Riwayat = new DefaultTableModel();
        tblRiwayat.setModel(Riwayat); 
        Riwayat.addColumn("ID");
        Riwayat.addColumn("KODE PINJAM");
        Riwayat.addColumn("KODE KEMBALI");
        Riwayat.addColumn("ID SISWA");
        Riwayat.addColumn("ID BUKU");
        Riwayat.addColumn("TGL PINJAM");
        Riwayat.addColumn("TGL PENGEMBALIAN");
        Riwayat.addColumn("TGL DIKEMBALIKAN");
        Riwayat.addColumn("STATUS");
        
        loadDataSiswa();
        loadDataBuku();
        loadDPinjam();
        loadDKembali();

        IdSiswa();
        IdBuku();
        KPeminjaman();
        
        tblRiwayat.setVisible(false);
    }

    private void loadDataSiswa(){
    DSiswa.setRowCount(0);
    try {
      String sql = "SELECT * FROM siswa";
      PreparedStatement ps = conn.prepareStatement(sql);
      ResultSet rs = ps.executeQuery();
      while (rs.next()) {
         // Menambahkan baris ke dalam model tabel
         DSiswa.addRow(new Object[]{
         rs.getInt("id_siswa"),
         rs.getString("nama"),
         rs.getString("kelas"),
       });
      }
  } catch (SQLException e) {
     System.out.println("Error load Data" + e.getMessage());
   }      
    }
    
    private void loadDataBuku(){
    DBuku.setRowCount(0);
    try {
      String sql = "SELECT * FROM buku";
      PreparedStatement ps = conn.prepareStatement(sql);
      ResultSet rs = ps.executeQuery();
      while (rs.next()) {
         // Menambahkan baris ke dalam model tabel
         DBuku.addRow(new Object[]{
         rs.getInt("id_buku"),
         rs.getString("judul"),
         rs.getString("pengarang"),
         rs.getString("kategori"),
         rs.getString("tahun_terbit"),
       });
      }
  } catch (SQLException e) {
     System.out.println("Error load Data" + e.getMessage());
   }      
    }
    
    private void loadDPinjam(){
    DPeminjaman.setRowCount(0);
    try {
      String sql = "SELECT * FROM peminjaman";
      PreparedStatement ps = conn.prepareStatement(sql);
      ResultSet rs = ps.executeQuery();
      while (rs.next()) {
         // Menambahkan baris ke dalam model tabel
         DPeminjaman.addRow(new Object[]{
         rs.getInt("kode"),
         rs.getString("nama_siswa"),
         rs.getString("judul_buku"),
         rs.getDate("tgl_peminjaman"),
         rs.getDate("tgl_kembali"),
       });
      }
  } catch (SQLException e) {
     System.out.println("Error load Data" + e.getMessage());
   }      
    }
    
    private void loadDKembali(){
    DPengembalian.setRowCount(0);
    try {
      String sql = "SELECT * FROM pengembalian";
      PreparedStatement ps = conn.prepareStatement(sql);
      ResultSet rs = ps.executeQuery();
      while (rs.next()) {
         // Menambahkan baris ke dalam model tabel
         DPengembalian.addRow(new Object[]{
         rs.getInt("kode_pengembalian"),
         rs.getInt("kode_peminjaman"),
         rs.getDate("tgl_pengembalian"),
         rs.getDate("tgl_dikembalikan"),
         rs.getString("status"),
       });
      }
  } catch (SQLException e) {
     System.out.println("Error load Data" + e.getMessage());
   }      
    }
    
    
    
private void IdSiswa() {
    try {
        String sql = "SELECT id_siswa, nama AS nama FROM siswa";
        PreparedStatement ps = conn.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        cbNamaSiswa.removeAllItems(); 
        while (rs.next()) {
            int id = rs.getInt("id_siswa");
            cbNamaSiswa.addItem(rs.getString("nama")); 
        }
    } catch (SQLException e) {
        System.out.println("Error loading Student IDs: " + e.getMessage());
    }
}

private void IdBuku() {
    try {
        String sql = "SELECT id_buku, judul AS judul FROM buku";
        PreparedStatement ps = conn.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        cbJudulBuku.removeAllItems();
        hitung = 0; 
        while (rs.next()) {
            int id = rs.getInt("id_buku");
            cbJudulBuku.addItem(rs.getString("judul"));
            hitung++;
        }
    } catch (SQLException e) {
        System.out.println("Error loading Book IDs: " + e.getMessage());
    }
}

private void KPeminjaman() {
    try {
        String sql = "SELECT kode FROM peminjaman ORDER BY kode ASC"; 
        PreparedStatement ps = conn.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        cb_pmnjmn.removeAllItems();
        hitung = 0; 
        while (rs.next()) {
            cb_pmnjmn.addItem(rs.getString("kode"));
            hitung++;
        }

        if (hitung > 0) {
            cb_pmnjmn.setSelectedIndex(0); 
        }
    } catch (SQLException e) {
        System.out.println("Error loading Book IDs: " + e.getMessage());
    }
}

private void loadDataAll() {
    Riwayat.setRowCount(0); 
    try {
        String sql = "SELECT p.kode AS kode_peminjaman, pe.kode_pengembalian AS kode_kembali, p.nama_siswa, p.judul_buku, " +
                     "p.tgl_peminjaman, " +
                     "pe.tgl_pengembalian, " +
                     "pe.tgl_dikembalikan, " +
                     "pe.status " +
                     "FROM peminjaman p " +
                     "LEFT JOIN pengembalian pe ON p.kode = pe.kode_peminjaman";

        PreparedStatement ps = conn.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        
        int autoInc = 1;
        
        while (rs.next()) {
            Riwayat.addRow(new Object[]{
                autoInc++,
                rs.getInt("kode_peminjaman"), 
                rs.getString("kode_kembali") != null ? rs.getString("kode_kembali") : "-", 
                rs.getString("nama_siswa"), 
                rs.getString("judul_buku"), 
                rs.getDate("tgl_peminjaman") != null ? rs.getDate("tgl_peminjaman").toString() : "-", 
                rs.getDate("tgl_pengembalian") != null ? rs.getDate("tgl_pengembalian").toString() : "-", 
                rs.getDate("tgl_dikembalikan") != null ? rs.getDate("tgl_dikembalikan").toString() : "-", 
                rs.getString("status") != null ? rs.getString("status") : "Belum Mengembalikan" 
            });
        }
    } catch (SQLException e) {
        System.out.println("Error loading combined data: " + e.getMessage());
    }
}

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel7 = new javax.swing.JPanel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        tfIDSiswa = new javax.swing.JTextField();
        tfNama = new javax.swing.JTextField();
        CBKelas = new javax.swing.JComboBox<>();
        jScrollPane5 = new javax.swing.JScrollPane();
        tblSiswa = new javax.swing.JTable();
        tfcari = new javax.swing.JTextField();
        btnCari_DS = new javax.swing.JButton();
        btnSave_DS = new javax.swing.JButton();
        btnEdit_DS = new javax.swing.JButton();
        btnHapus_DS = new javax.swing.JButton();
        btnClear_DS = new javax.swing.JButton();
        jLabel21 = new javax.swing.JLabel();
        btnBack_DS = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        tfIDBuku = new javax.swing.JTextField();
        tfJudul = new javax.swing.JTextField();
        tfPengarang = new javax.swing.JTextField();
        cbKategori = new javax.swing.JComboBox<>();
        tfThnTerbit = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblBuku = new javax.swing.JTable();
        tfCari_DB = new javax.swing.JTextField();
        btnCari_DB = new javax.swing.JButton();
        btnSave_DB = new javax.swing.JButton();
        btnEdit_DB = new javax.swing.JButton();
        btnHapus_DB = new javax.swing.JButton();
        btnClear_DB = new javax.swing.JButton();
        btnBack_DB = new javax.swing.JButton();
        jLabel22 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        tglPeminjaman = new com.toedter.calendar.JDateChooser();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblDPinjam = new javax.swing.JTable();
        tfCari_DP = new javax.swing.JTextField();
        btnCari_DPnjm = new javax.swing.JButton();
        btnSave_Dpnjm = new javax.swing.JButton();
        btnEdit_DPnjm = new javax.swing.JButton();
        btnHapus_DPnjm = new javax.swing.JButton();
        btnClear_DPnjm = new javax.swing.JButton();
        jLabel18 = new javax.swing.JLabel();
        tglKembali = new com.toedter.calendar.JDateChooser();
        jLabel20 = new javax.swing.JLabel();
        txtKP = new javax.swing.JTextField();
        cbNamaSiswa = new javax.swing.JComboBox<>();
        cbJudulBuku = new javax.swing.JComboBox<>();
        btnBack_DP = new javax.swing.JButton();
        jLabel23 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        tglPengembalian = new com.toedter.calendar.JDateChooser();
        tf_KP = new javax.swing.JTextField();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblDKembali = new javax.swing.JTable();
        txt_cari = new javax.swing.JTextField();
        btnCari_Dpngm = new javax.swing.JButton();
        btnSave_Dpngm = new javax.swing.JButton();
        btnEdit_Dpngm = new javax.swing.JButton();
        btnHapus_Dpngm = new javax.swing.JButton();
        btnClear_Dpngm = new javax.swing.JButton();
        tglDikembalikan = new com.toedter.calendar.JDateChooser();
        jLabel19 = new javax.swing.JLabel();
        btnBack_DPngmb = new javax.swing.JButton();
        cb_pmnjmn = new javax.swing.JComboBox<>();
        cbStatus = new javax.swing.JComboBox<>();
        jLabel25 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        tblRiwayat = new javax.swing.JTable();
        btnPrint = new javax.swing.JButton();
        btnConfirm = new javax.swing.JButton();
        btnExit = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(51, 51, 51));
        jPanel1.setPreferredSize(new java.awt.Dimension(820, 70));

        jLabel1.setFont(new java.awt.Font("Baskerville Old Face", 3, 30)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("WELCOME TO READING WORLD PLATFORM");

        jLabel24.setIcon(new javax.swing.ImageIcon("A:\\.MINE\\CHAPTER 3\\Visual Programming\\ProjekUAS\\image\\books_3771417 (1) (2).png")); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(728, 728, 728)
                        .addComponent(jLabel14))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(31, 31, 31)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 707, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel24)
                .addContainerGap(36, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel24))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(16, 16, 16)
                        .addComponent(jLabel1)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addGap(18, 18, 18)
                .addComponent(jLabel14)
                .addContainerGap())
        );

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 830, -1));

        jPanel2.setBackground(new java.awt.Color(102, 102, 102));

        jTabbedPane1.setBackground(new java.awt.Color(0, 0, 0));
        jTabbedPane1.setForeground(new java.awt.Color(255, 255, 255));
        jTabbedPane1.setTabPlacement(javax.swing.JTabbedPane.LEFT);
        jTabbedPane1.setFont(new java.awt.Font("Rockwell", 3, 12)); // NOI18N

        jPanel7.setBackground(new java.awt.Color(0, 102, 102));

        jLabel15.setFont(new java.awt.Font("Algerian", 1, 12)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(255, 255, 255));
        jLabel15.setText("ID SISWA :");

        jLabel16.setFont(new java.awt.Font("Algerian", 1, 12)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(255, 255, 255));
        jLabel16.setText("NAMA :");

        jLabel17.setFont(new java.awt.Font("Algerian", 1, 12)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(255, 255, 255));
        jLabel17.setText("KELAS :");

        tfIDSiswa.setFont(new java.awt.Font("Rockwell", 1, 12)); // NOI18N
        tfIDSiswa.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        tfNama.setFont(new java.awt.Font("Rockwell", 1, 12)); // NOI18N
        tfNama.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        CBKelas.setFont(new java.awt.Font("Rockwell", 1, 12)); // NOI18N
        CBKelas.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "--PILIH--", "7A", "7B", "7C", "8A", "8B", "8C", "9A", "9B", "9C" }));

        tblSiswa.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tblSiswa.setEnabled(false);
        jScrollPane5.setViewportView(tblSiswa);

        tfcari.setFont(new java.awt.Font("Rockwell", 1, 12)); // NOI18N
        tfcari.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        btnCari_DS.setBackground(new java.awt.Color(0, 153, 153));
        btnCari_DS.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 12)); // NOI18N
        btnCari_DS.setForeground(new java.awt.Color(255, 255, 255));
        btnCari_DS.setIcon(new javax.swing.ImageIcon("A:\\.MINE\\CHAPTER 3\\Visual Programming\\ProjekUAS\\image\\zoom-out_6680886 (1).png")); // NOI18N
        btnCari_DS.setText("CARI");
        btnCari_DS.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCari_DSActionPerformed(evt);
            }
        });

        btnSave_DS.setBackground(new java.awt.Color(0, 153, 153));
        btnSave_DS.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 12)); // NOI18N
        btnSave_DS.setForeground(new java.awt.Color(255, 255, 255));
        btnSave_DS.setIcon(new javax.swing.ImageIcon("A:\\.MINE\\CHAPTER 3\\Visual Programming\\ProjekUAS\\image\\save_14959110 (1).png")); // NOI18N
        btnSave_DS.setText("SIMPAN");
        btnSave_DS.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSave_DSActionPerformed(evt);
            }
        });

        btnEdit_DS.setBackground(new java.awt.Color(0, 153, 153));
        btnEdit_DS.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 12)); // NOI18N
        btnEdit_DS.setForeground(new java.awt.Color(255, 255, 255));
        btnEdit_DS.setIcon(new javax.swing.ImageIcon("A:\\.MINE\\CHAPTER 3\\Visual Programming\\ProjekUAS\\image\\pen_12080619 (1).png")); // NOI18N
        btnEdit_DS.setText("EDIT");
        btnEdit_DS.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEdit_DSActionPerformed(evt);
            }
        });

        btnHapus_DS.setBackground(new java.awt.Color(0, 153, 153));
        btnHapus_DS.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 12)); // NOI18N
        btnHapus_DS.setForeground(new java.awt.Color(255, 255, 255));
        btnHapus_DS.setIcon(new javax.swing.ImageIcon("A:\\.MINE\\CHAPTER 3\\Visual Programming\\ProjekUAS\\image\\trash_9915690 (1).png")); // NOI18N
        btnHapus_DS.setText("HAPUS");
        btnHapus_DS.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHapus_DSActionPerformed(evt);
            }
        });

        btnClear_DS.setBackground(new java.awt.Color(0, 153, 153));
        btnClear_DS.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 12)); // NOI18N
        btnClear_DS.setForeground(new java.awt.Color(255, 255, 255));
        btnClear_DS.setIcon(new javax.swing.ImageIcon("A:\\.MINE\\CHAPTER 3\\Visual Programming\\ProjekUAS\\image\\loading_8847133 (1).png")); // NOI18N
        btnClear_DS.setText("CLEAR");
        btnClear_DS.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClear_DSActionPerformed(evt);
            }
        });

        jLabel21.setFont(new java.awt.Font("Algerian", 1, 12)); // NOI18N
        jLabel21.setForeground(new java.awt.Color(255, 255, 255));
        jLabel21.setText("MASUKKAN NAMA :");

        btnBack_DS.setBackground(new java.awt.Color(0, 153, 153));
        btnBack_DS.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 12)); // NOI18N
        btnBack_DS.setForeground(new java.awt.Color(255, 255, 255));
        btnBack_DS.setIcon(new javax.swing.ImageIcon("A:\\.MINE\\CHAPTER 3\\Visual Programming\\ProjekUAS\\image\\arrow-left_10023749 (1).png")); // NOI18N
        btnBack_DS.setText("BACK");
        btnBack_DS.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBack_DSActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(65, 65, 65)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel7Layout.createSequentialGroup()
                                .addGap(82, 82, 82)
                                .addComponent(tfIDSiswa))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(CBKelas, javax.swing.GroupLayout.Alignment.TRAILING, 0, 215, Short.MAX_VALUE)
                                .addComponent(tfNama, javax.swing.GroupLayout.Alignment.TRAILING)))
                        .addGap(303, 303, 303))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel16)
                            .addComponent(jLabel15)
                            .addComponent(jLabel17))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(tfcari, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel21, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnBack_DS, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnCari_DS, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addComponent(btnSave_DS)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnEdit_DS)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnHapus_DS)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnClear_DS)))
                .addGap(0, 17, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(tfIDSiswa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel15))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel16)
                            .addComponent(tfNama, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel17)
                            .addComponent(CBKelas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 254, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGap(135, 135, 135)
                        .addComponent(jLabel21)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(tfcari, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnCari_DS)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnBack_DS)))
                .addGap(18, 18, 18)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSave_DS)
                    .addComponent(btnEdit_DS)
                    .addComponent(btnHapus_DS)
                    .addComponent(btnClear_DS))
                .addContainerGap(137, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("DATA SISWA", jPanel7);

        jPanel3.setBackground(new java.awt.Color(0, 102, 102));

        jLabel2.setFont(new java.awt.Font("Algerian", 1, 12)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("ID BUKU :");

        jLabel3.setFont(new java.awt.Font("Algerian", 1, 12)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("JUDUL :");

        jLabel4.setFont(new java.awt.Font("Algerian", 1, 12)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("PENGARANG :");

        jLabel5.setFont(new java.awt.Font("Algerian", 1, 12)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("KATEGORI :");

        jLabel6.setFont(new java.awt.Font("Algerian", 1, 12)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("TAHUN TERBIT :");

        tfIDBuku.setFont(new java.awt.Font("Rockwell", 1, 12)); // NOI18N
        tfIDBuku.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        tfJudul.setFont(new java.awt.Font("Rockwell", 1, 12)); // NOI18N
        tfJudul.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        tfPengarang.setFont(new java.awt.Font("Rockwell", 1, 12)); // NOI18N
        tfPengarang.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        cbKategori.setFont(new java.awt.Font("Rockwell", 1, 12)); // NOI18N
        cbKategori.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "--PILIH--", "CERITA RAKYAT", "NOVEL", "KOMIK", "PUISI", "BIOGRAFI", "KETERAMPILAN & HOBI" }));

        tfThnTerbit.setFont(new java.awt.Font("Rockwell", 1, 12)); // NOI18N
        tfThnTerbit.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        tblBuku.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tblBuku.setEnabled(false);
        jScrollPane1.setViewportView(tblBuku);

        tfCari_DB.setFont(new java.awt.Font("Rockwell", 1, 12)); // NOI18N
        tfCari_DB.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        btnCari_DB.setBackground(new java.awt.Color(0, 153, 153));
        btnCari_DB.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 12)); // NOI18N
        btnCari_DB.setForeground(new java.awt.Color(255, 255, 255));
        btnCari_DB.setIcon(new javax.swing.ImageIcon("A:\\.MINE\\CHAPTER 3\\Visual Programming\\ProjekUAS\\image\\zoom-out_6680886 (1).png")); // NOI18N
        btnCari_DB.setText("CARI");
        btnCari_DB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCari_DBActionPerformed(evt);
            }
        });

        btnSave_DB.setBackground(new java.awt.Color(0, 153, 153));
        btnSave_DB.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 12)); // NOI18N
        btnSave_DB.setForeground(new java.awt.Color(255, 255, 255));
        btnSave_DB.setIcon(new javax.swing.ImageIcon("A:\\.MINE\\CHAPTER 3\\Visual Programming\\ProjekUAS\\image\\save_14959110 (1).png")); // NOI18N
        btnSave_DB.setText("SIMPAN");
        btnSave_DB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSave_DBActionPerformed(evt);
            }
        });

        btnEdit_DB.setBackground(new java.awt.Color(0, 153, 153));
        btnEdit_DB.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 12)); // NOI18N
        btnEdit_DB.setForeground(new java.awt.Color(255, 255, 255));
        btnEdit_DB.setIcon(new javax.swing.ImageIcon("A:\\.MINE\\CHAPTER 3\\Visual Programming\\ProjekUAS\\image\\pen_12080619 (1).png")); // NOI18N
        btnEdit_DB.setText("EDIT");
        btnEdit_DB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEdit_DBActionPerformed(evt);
            }
        });

        btnHapus_DB.setBackground(new java.awt.Color(0, 153, 153));
        btnHapus_DB.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 12)); // NOI18N
        btnHapus_DB.setForeground(new java.awt.Color(255, 255, 255));
        btnHapus_DB.setIcon(new javax.swing.ImageIcon("A:\\.MINE\\CHAPTER 3\\Visual Programming\\ProjekUAS\\image\\trash_9915690 (1).png")); // NOI18N
        btnHapus_DB.setText("HAPUS");
        btnHapus_DB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHapus_DBActionPerformed(evt);
            }
        });

        btnClear_DB.setBackground(new java.awt.Color(0, 153, 153));
        btnClear_DB.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 12)); // NOI18N
        btnClear_DB.setForeground(new java.awt.Color(255, 255, 255));
        btnClear_DB.setIcon(new javax.swing.ImageIcon("A:\\.MINE\\CHAPTER 3\\Visual Programming\\ProjekUAS\\image\\loading_8847133 (1).png")); // NOI18N
        btnClear_DB.setText("CLEAR");
        btnClear_DB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClear_DBActionPerformed(evt);
            }
        });

        btnBack_DB.setBackground(new java.awt.Color(0, 153, 153));
        btnBack_DB.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 12)); // NOI18N
        btnBack_DB.setForeground(new java.awt.Color(255, 255, 255));
        btnBack_DB.setIcon(new javax.swing.ImageIcon("A:\\.MINE\\CHAPTER 3\\Visual Programming\\ProjekUAS\\image\\arrow-left_10023749 (1).png")); // NOI18N
        btnBack_DB.setText("BACK");
        btnBack_DB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBack_DBActionPerformed(evt);
            }
        });

        jLabel22.setFont(new java.awt.Font("Algerian", 1, 14)); // NOI18N
        jLabel22.setForeground(new java.awt.Color(255, 255, 255));
        jLabel22.setText("MASUKKAN JUDUL :");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(29, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(16, 16, 16)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel6)
                            .addComponent(jLabel5)
                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel4)
                                .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING)))
                        .addGap(30, 30, 30)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(tfJudul, javax.swing.GroupLayout.DEFAULT_SIZE, 225, Short.MAX_VALUE)
                                .addComponent(tfPengarang)
                                .addComponent(tfIDBuku))
                            .addComponent(cbKategori, javax.swing.GroupLayout.PREFERRED_SIZE, 226, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(tfThnTerbit, javax.swing.GroupLayout.PREFERRED_SIZE, 225, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(btnSave_DB)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnEdit_DB)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnHapus_DB)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnClear_DB))
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(tfCari_DB, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnCari_DB, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnBack_DB, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel22, javax.swing.GroupLayout.Alignment.LEADING))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(tfIDBuku, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(tfJudul, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(tfPengarang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbKategori, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tfThnTerbit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel22)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(tfCari_DB, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(12, 12, 12)
                        .addComponent(btnCari_DB)
                        .addGap(8, 8, 8)
                        .addComponent(btnBack_DB))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 242, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSave_DB)
                    .addComponent(btnEdit_DB)
                    .addComponent(btnHapus_DB)
                    .addComponent(btnClear_DB))
                .addContainerGap(69, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("DATA BUKU", jPanel3);

        jPanel4.setBackground(new java.awt.Color(0, 102, 102));

        jLabel7.setFont(new java.awt.Font("Algerian", 1, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("NAMA SISWA :");

        jLabel8.setFont(new java.awt.Font("Algerian", 1, 14)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("JUDUL BUKU :");

        jLabel9.setFont(new java.awt.Font("Algerian", 1, 14)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("TGL PEMINJAMAN :");

        tglPeminjaman.setFont(new java.awt.Font("Rockwell", 1, 12)); // NOI18N
        tglPeminjaman.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                tglPeminjamanPropertyChange(evt);
            }
        });

        tblDPinjam.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tblDPinjam.setEnabled(false);
        jScrollPane2.setViewportView(tblDPinjam);

        tfCari_DP.setFont(new java.awt.Font("Rockwell", 1, 12)); // NOI18N
        tfCari_DP.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        btnCari_DPnjm.setBackground(new java.awt.Color(0, 153, 153));
        btnCari_DPnjm.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 12)); // NOI18N
        btnCari_DPnjm.setForeground(new java.awt.Color(255, 255, 255));
        btnCari_DPnjm.setIcon(new javax.swing.ImageIcon("A:\\.MINE\\CHAPTER 3\\Visual Programming\\ProjekUAS\\image\\zoom-out_6680886 (1).png")); // NOI18N
        btnCari_DPnjm.setText("CARI");
        btnCari_DPnjm.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCari_DPnjmActionPerformed(evt);
            }
        });

        btnSave_Dpnjm.setBackground(new java.awt.Color(0, 153, 153));
        btnSave_Dpnjm.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 12)); // NOI18N
        btnSave_Dpnjm.setForeground(new java.awt.Color(255, 255, 255));
        btnSave_Dpnjm.setIcon(new javax.swing.ImageIcon("A:\\.MINE\\CHAPTER 3\\Visual Programming\\ProjekUAS\\image\\save_14959110 (1).png")); // NOI18N
        btnSave_Dpnjm.setText("SIMPAN");
        btnSave_Dpnjm.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSave_DpnjmActionPerformed(evt);
            }
        });

        btnEdit_DPnjm.setBackground(new java.awt.Color(0, 153, 153));
        btnEdit_DPnjm.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 12)); // NOI18N
        btnEdit_DPnjm.setForeground(new java.awt.Color(255, 255, 255));
        btnEdit_DPnjm.setIcon(new javax.swing.ImageIcon("A:\\.MINE\\CHAPTER 3\\Visual Programming\\ProjekUAS\\image\\pen_12080619 (1).png")); // NOI18N
        btnEdit_DPnjm.setText("EDIT");
        btnEdit_DPnjm.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEdit_DPnjmActionPerformed(evt);
            }
        });

        btnHapus_DPnjm.setBackground(new java.awt.Color(0, 153, 153));
        btnHapus_DPnjm.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 12)); // NOI18N
        btnHapus_DPnjm.setForeground(new java.awt.Color(255, 255, 255));
        btnHapus_DPnjm.setIcon(new javax.swing.ImageIcon("A:\\.MINE\\CHAPTER 3\\Visual Programming\\ProjekUAS\\image\\trash_9915690 (1).png")); // NOI18N
        btnHapus_DPnjm.setText("HAPUS");
        btnHapus_DPnjm.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHapus_DPnjmActionPerformed(evt);
            }
        });

        btnClear_DPnjm.setBackground(new java.awt.Color(0, 153, 153));
        btnClear_DPnjm.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 12)); // NOI18N
        btnClear_DPnjm.setForeground(new java.awt.Color(255, 255, 255));
        btnClear_DPnjm.setIcon(new javax.swing.ImageIcon("A:\\.MINE\\CHAPTER 3\\Visual Programming\\ProjekUAS\\image\\loading_8847133 (1).png")); // NOI18N
        btnClear_DPnjm.setText("CLEAR");
        btnClear_DPnjm.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClear_DPnjmActionPerformed(evt);
            }
        });

        jLabel18.setFont(new java.awt.Font("Algerian", 1, 14)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(255, 255, 255));
        jLabel18.setText("MAX PEMINJAMAN:");

        tglKembali.setEnabled(false);
        tglKembali.setFont(new java.awt.Font("Rockwell", 1, 12)); // NOI18N

        jLabel20.setFont(new java.awt.Font("Algerian", 1, 14)); // NOI18N
        jLabel20.setForeground(new java.awt.Color(255, 255, 255));
        jLabel20.setText("KODE PEMINJAMAN :");

        txtKP.setFont(new java.awt.Font("Rockwell", 1, 12)); // NOI18N
        txtKP.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        cbNamaSiswa.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        cbJudulBuku.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        btnBack_DP.setBackground(new java.awt.Color(0, 153, 153));
        btnBack_DP.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 12)); // NOI18N
        btnBack_DP.setForeground(new java.awt.Color(255, 255, 255));
        btnBack_DP.setIcon(new javax.swing.ImageIcon("A:\\.MINE\\CHAPTER 3\\Visual Programming\\ProjekUAS\\image\\arrow-left_10023749 (1).png")); // NOI18N
        btnBack_DP.setText("BACK");
        btnBack_DP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBack_DPActionPerformed(evt);
            }
        });

        jLabel23.setFont(new java.awt.Font("Algerian", 1, 13)); // NOI18N
        jLabel23.setForeground(new java.awt.Color(255, 255, 255));
        jLabel23.setText("MASUKKAN KODE PEMINJAMAN :");

        jLabel26.setFont(new java.awt.Font("Algerian", 1, 14)); // NOI18N
        jLabel26.setForeground(new java.awt.Color(255, 255, 255));
        jLabel26.setText("NB : MAX PEMINJAMAN BUKU SELAMA 5 HARI");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(btnSave_Dpnjm)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnEdit_DPnjm)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnHapus_DPnjm)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnClear_DPnjm))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(7, 7, 7)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 434, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel23)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(btnBack_DP, javax.swing.GroupLayout.PREFERRED_SIZE, 189, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(tfCari_DP, javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(btnCari_DPnjm, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 189, Short.MAX_VALUE))))))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGap(37, 37, 37)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel20)
                                    .addComponent(jLabel7)
                                    .addComponent(jLabel8))
                                .addGap(22, 22, 22))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel18)
                                    .addComponent(jLabel9))
                                .addGap(18, 18, 18)))
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(tglPeminjaman, javax.swing.GroupLayout.PREFERRED_SIZE, 191, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(txtKP)
                                .addComponent(cbNamaSiswa, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(cbJudulBuku, javax.swing.GroupLayout.PREFERRED_SIZE, 191, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(tglKembali, javax.swing.GroupLayout.PREFERRED_SIZE, 191, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(13, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jLabel26, javax.swing.GroupLayout.PREFERRED_SIZE, 332, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(44, 44, 44))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtKP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel20, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(2, 2, 2)))
                .addGap(15, 15, 15)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel7)
                    .addComponent(cbNamaSiswa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(cbJudulBuku, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(tglPeminjaman, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(tglKembali, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel18, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 296, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(btnEdit_DPnjm)
                                    .addComponent(btnSave_Dpnjm))
                                .addComponent(btnHapus_DPnjm, javax.swing.GroupLayout.Alignment.TRAILING))
                            .addComponent(btnClear_DPnjm)))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel23)
                        .addGap(4, 4, 4)
                        .addComponent(tfCari_DP, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnCari_DPnjm, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnBack_DP)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel26, javax.swing.GroupLayout.DEFAULT_SIZE, 32, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("DATA PEMINJAMAN", jPanel4);

        jPanel5.setBackground(new java.awt.Color(0, 102, 102));

        jLabel10.setFont(new java.awt.Font("Algerian", 1, 14)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setText("KODE PENGEMBALIAN :");

        jLabel11.setFont(new java.awt.Font("Algerian", 1, 14)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setText("KODE PEMINJAMAN :");

        jLabel12.setFont(new java.awt.Font("Algerian", 1, 14)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(255, 255, 255));
        jLabel12.setText("TGL PENGEMBALIAN :");

        jLabel13.setFont(new java.awt.Font("Algerian", 1, 14)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(255, 255, 255));
        jLabel13.setText("STATUS :");

        tglPengembalian.setEnabled(false);
        tglPengembalian.setFont(new java.awt.Font("Rockwell", 1, 12)); // NOI18N

        tf_KP.setFont(new java.awt.Font("Rockwell", 1, 12)); // NOI18N
        tf_KP.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        tblDKembali.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tblDKembali.setEnabled(false);
        jScrollPane3.setViewportView(tblDKembali);

        txt_cari.setFont(new java.awt.Font("Rockwell", 1, 12)); // NOI18N
        txt_cari.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txt_cari.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_cariActionPerformed(evt);
            }
        });

        btnCari_Dpngm.setBackground(new java.awt.Color(0, 153, 153));
        btnCari_Dpngm.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 12)); // NOI18N
        btnCari_Dpngm.setForeground(new java.awt.Color(255, 255, 255));
        btnCari_Dpngm.setIcon(new javax.swing.ImageIcon("A:\\.MINE\\CHAPTER 3\\Visual Programming\\ProjekUAS\\image\\zoom-out_6680886 (1).png")); // NOI18N
        btnCari_Dpngm.setText("CARI");
        btnCari_Dpngm.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCari_DpngmActionPerformed(evt);
            }
        });

        btnSave_Dpngm.setBackground(new java.awt.Color(0, 153, 153));
        btnSave_Dpngm.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 12)); // NOI18N
        btnSave_Dpngm.setForeground(new java.awt.Color(255, 255, 255));
        btnSave_Dpngm.setIcon(new javax.swing.ImageIcon("A:\\.MINE\\CHAPTER 3\\Visual Programming\\ProjekUAS\\image\\save_14959110 (1).png")); // NOI18N
        btnSave_Dpngm.setText("SIMPAN");
        btnSave_Dpngm.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSave_DpngmActionPerformed(evt);
            }
        });

        btnEdit_Dpngm.setBackground(new java.awt.Color(0, 153, 153));
        btnEdit_Dpngm.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 12)); // NOI18N
        btnEdit_Dpngm.setForeground(new java.awt.Color(255, 255, 255));
        btnEdit_Dpngm.setIcon(new javax.swing.ImageIcon("A:\\.MINE\\CHAPTER 3\\Visual Programming\\ProjekUAS\\image\\pen_12080619 (1).png")); // NOI18N
        btnEdit_Dpngm.setText("EDIT");
        btnEdit_Dpngm.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEdit_DpngmActionPerformed(evt);
            }
        });

        btnHapus_Dpngm.setBackground(new java.awt.Color(0, 153, 153));
        btnHapus_Dpngm.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 12)); // NOI18N
        btnHapus_Dpngm.setForeground(new java.awt.Color(255, 255, 255));
        btnHapus_Dpngm.setIcon(new javax.swing.ImageIcon("A:\\.MINE\\CHAPTER 3\\Visual Programming\\ProjekUAS\\image\\trash_9915690 (1).png")); // NOI18N
        btnHapus_Dpngm.setText("HAPUS");
        btnHapus_Dpngm.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHapus_DpngmActionPerformed(evt);
            }
        });

        btnClear_Dpngm.setBackground(new java.awt.Color(0, 153, 153));
        btnClear_Dpngm.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 12)); // NOI18N
        btnClear_Dpngm.setForeground(new java.awt.Color(255, 255, 255));
        btnClear_Dpngm.setIcon(new javax.swing.ImageIcon("A:\\.MINE\\CHAPTER 3\\Visual Programming\\ProjekUAS\\image\\loading_8847133 (1).png")); // NOI18N
        btnClear_Dpngm.setText("CLEAR");
        btnClear_Dpngm.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClear_DpngmActionPerformed(evt);
            }
        });

        tglDikembalikan.setFont(new java.awt.Font("Rockwell", 1, 12)); // NOI18N
        tglDikembalikan.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                tglDikembalikanPropertyChange(evt);
            }
        });

        jLabel19.setFont(new java.awt.Font("Algerian", 1, 14)); // NOI18N
        jLabel19.setForeground(new java.awt.Color(255, 255, 255));
        jLabel19.setText("TGL DIKEMBALIKAN :");

        btnBack_DPngmb.setBackground(new java.awt.Color(0, 153, 153));
        btnBack_DPngmb.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 12)); // NOI18N
        btnBack_DPngmb.setForeground(new java.awt.Color(255, 255, 255));
        btnBack_DPngmb.setIcon(new javax.swing.ImageIcon("A:\\.MINE\\CHAPTER 3\\Visual Programming\\ProjekUAS\\image\\arrow-left_10023749 (1).png")); // NOI18N
        btnBack_DPngmb.setText("BACK");
        btnBack_DPngmb.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBack_DPngmbActionPerformed(evt);
            }
        });

        cb_pmnjmn.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cb_pmnjmn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cb_pmnjmnActionPerformed(evt);
            }
        });

        cbStatus.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "TERLAMBAT", "TEPAT WAKTU" }));
        cbStatus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbStatusActionPerformed(evt);
            }
        });

        jLabel25.setFont(new java.awt.Font("Algerian", 1, 12)); // NOI18N
        jLabel25.setForeground(new java.awt.Color(255, 255, 255));
        jLabel25.setText("MASUKKAN KODE PENGEMBALIAN :");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 439, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnBack_DPngmb, javax.swing.GroupLayout.DEFAULT_SIZE, 213, Short.MAX_VALUE)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel25, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txt_cari, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(btnCari_Dpngm, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addContainerGap())))
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(63, 63, 63)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel13)
                            .addComponent(jLabel12)
                            .addComponent(jLabel11)
                            .addComponent(jLabel10)
                            .addComponent(jLabel19))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(tglDikembalikan, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(tglPengembalian, javax.swing.GroupLayout.DEFAULT_SIZE, 216, Short.MAX_VALUE)
                            .addComponent(tf_KP)
                            .addComponent(cb_pmnjmn, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(cbStatus, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addComponent(btnSave_Dpngm)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnEdit_Dpngm)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnHapus_Dpngm)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnClear_Dpngm)))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(tf_KP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(cb_pmnjmn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(11, 11, 11)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(tglPengembalian, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12))
                .addGap(15, 15, 15)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(tglDikembalikan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel19))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(cbStatus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(23, 23, 23)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel25)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txt_cari, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnCari_Dpngm)
                        .addGap(12, 12, 12)
                        .addComponent(btnBack_DPngmb))
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 277, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnSave_Dpngm, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnClear_Dpngm)
                        .addComponent(btnHapus_Dpngm)
                        .addComponent(btnEdit_Dpngm)))
                .addContainerGap(41, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("DATA PENGEMBALIAN", jPanel5);

        jPanel6.setBackground(new java.awt.Color(0, 102, 102));

        tblRiwayat.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane4.setViewportView(tblRiwayat);

        btnPrint.setBackground(new java.awt.Color(0, 153, 153));
        btnPrint.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 12)); // NOI18N
        btnPrint.setForeground(new java.awt.Color(255, 255, 255));
        btnPrint.setIcon(new javax.swing.ImageIcon("A:\\.MINE\\CHAPTER 3\\Visual Programming\\ProjekUAS\\image\\reload_6622423 (1).png")); // NOI18N
        btnPrint.setText("PRINT");
        btnPrint.setEnabled(false);
        btnPrint.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPrintActionPerformed(evt);
            }
        });

        btnConfirm.setBackground(new java.awt.Color(0, 153, 153));
        btnConfirm.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 12)); // NOI18N
        btnConfirm.setForeground(new java.awt.Color(255, 255, 255));
        btnConfirm.setIcon(new javax.swing.ImageIcon("A:\\.MINE\\CHAPTER 3\\Visual Programming\\ProjekUAS\\image\\confirm_4316322 (1).png")); // NOI18N
        btnConfirm.setText("KONFIRMASI");
        btnConfirm.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnConfirmActionPerformed(evt);
            }
        });

        btnExit.setBackground(new java.awt.Color(0, 153, 153));
        btnExit.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 12)); // NOI18N
        btnExit.setIcon(new javax.swing.ImageIcon("A:\\.MINE\\CHAPTER 3\\Visual Programming\\ProjekUAS\\image\\WhatsApp Image 2024-11-15 at 19.52.40_f3707e87.jpg")); // NOI18N
        btnExit.setText("EXIT");
        btnExit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExitActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(86, 86, 86)
                .addComponent(btnConfirm)
                .addGap(18, 18, 18)
                .addComponent(btnPrint, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnExit, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addContainerGap(19, Short.MAX_VALUE)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 626, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(19, 19, 19))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 416, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(41, 41, 41)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnConfirm)
                    .addComponent(btnPrint)
                    .addComponent(btnExit))
                .addContainerGap(68, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("RIWAYAT", jPanel6);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1)
        );

        getContentPane().add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 70, 830, 580));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txt_cariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_cariActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_cariActionPerformed

    private void btnHapus_DBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHapus_DBActionPerformed
        try  {
            String sql = "DELETE FROM buku WHERE id_buku = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, Integer.parseInt(tfIDBuku.getText()));
            ps.executeUpdate();
            conn.prepareStatement("SET @count = 0;").execute();
            conn.prepareStatement("UPDATE buku SET id_buku = @count := @count + 1;").executeUpdate();
            conn.prepareStatement("ALTER TABLE buku AUTO_INCREMENT = 1;").execute();
            JOptionPane.showMessageDialog(this, "Data yang di Hapus Sukses");
            loadDataBuku();
        } catch (SQLException e) {
            System.out.println("Error Save Data" + e.getMessage());
        }
    }//GEN-LAST:event_btnHapus_DBActionPerformed

    private void btnClear_DBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClear_DBActionPerformed
        tfIDBuku.setText("");
        tfJudul.setText("");
        tfPengarang.setText("");
        cbKategori.setSelectedIndex(0);
        tfThnTerbit.setText("");
        tfCari_DB.setText("");
    }//GEN-LAST:event_btnClear_DBActionPerformed

    private void btnClear_DSActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClear_DSActionPerformed
        tfIDSiswa.setText("");
        tfNama.setText("");
        tfcari.setText("");
        CBKelas.setSelectedIndex(0);
    }//GEN-LAST:event_btnClear_DSActionPerformed

    private void btnClear_DPnjmActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClear_DPnjmActionPerformed
        txtKP.setText("");
        cbNamaSiswa.setSelectedIndex(0);
        cbJudulBuku.setSelectedIndex(0);
        tglPeminjaman.setDate(null);
        tglKembali.setDate(null);
    }//GEN-LAST:event_btnClear_DPnjmActionPerformed

    private void btnClear_DpngmActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClear_DpngmActionPerformed
        tf_KP.setText("");
        txt_cari.setText("");
        cb_pmnjmn.setSelectedIndex(0);
        tglPengembalian.setDate(null);
        tglDikembalikan.setDate(null);
        cbStatus.setSelectedIndex(0);
    }//GEN-LAST:event_btnClear_DpngmActionPerformed

    private void btnSave_DSActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSave_DSActionPerformed
      if (tfNama.getText().isEmpty()|| CBKelas.getSelectedItem() == null || CBKelas.getSelectedItem().equals("--PILIH--")) {
        JOptionPane.showMessageDialog(this, "Harap isi Nama dan Kelas terlebih dahulu", "Peringatan", JOptionPane.WARNING_MESSAGE);
        return;
    }
      try{            
            String sql = "INSERT INTO siswa (nama, kelas) VALUES (?, ?)";
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setString(1, tfNama.getText());
                ps.setString(2, CBKelas.getSelectedItem().toString());
                ps.executeUpdate();
             JOptionPane.showMessageDialog(this, "Data yang di Tambahkan Sukses ");
            loadDataSiswa(); 
            IdSiswa();
            
        tfIDSiswa.setText("");
        tfNama.setText("");  
        CBKelas.setSelectedIndex(0);

        } catch (SQLException e) {
            System.out.println("Error Save Data" + e.getMessage());
        }  
    }//GEN-LAST:event_btnSave_DSActionPerformed

    private void btnEdit_DSActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEdit_DSActionPerformed
    if (tfIDSiswa.getText().isEmpty() || tfNama.getText().isEmpty()|| CBKelas.getSelectedItem() == null || CBKelas.getSelectedItem().equals("--PILIH--")) {
        JOptionPane.showMessageDialog(this, "Harap isi ID Siswa, Nama, dan Kelas terlebih dahulu", "Peringatan", JOptionPane.WARNING_MESSAGE);
        return;
    }
        try {
            String sql = "UPDATE siswa SET nama = ?, kelas = ? WHERE id_siswa = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, tfNama.getText());
            ps.setString(2, CBKelas.getSelectedItem().toString());
            ps.setInt(3, Integer.parseInt(tfIDSiswa.getText()));
            ps.executeUpdate();
            JOptionPane.showMessageDialog(this, "Data yang di Update Sukses");   
            loadDataSiswa();
            IdSiswa();
            
        tfIDSiswa.setText("");
        tfNama.setText("");  
        CBKelas.setSelectedIndex(0);
        }  catch (SQLException e) {
            System.out.println("Error Save Data" + e.getMessage());
        }
    }//GEN-LAST:event_btnEdit_DSActionPerformed

    private void btnHapus_DSActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHapus_DSActionPerformed
        try  {
            String sql = "DELETE FROM siswa WHERE id_siswa = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, Integer.parseInt(tfIDSiswa.getText()));
            ps.executeUpdate();
            conn.prepareStatement("SET @count = 0;").execute();
            conn.prepareStatement("UPDATE siswa SET id_siswa = @count := @count + 1;").executeUpdate();
            conn.prepareStatement("ALTER TABLE siswa AUTO_INCREMENT = 1;").execute();
            JOptionPane.showMessageDialog(this, "Data yang di Hapus Sukses");
            loadDataSiswa();
            IdSiswa();
        tfIDSiswa.setText("");
        tfNama.setText("");  
        CBKelas.setSelectedIndex(0);
        } catch (SQLException e) {
            System.out.println("Error Save Data" + e.getMessage());
        }
    }//GEN-LAST:event_btnHapus_DSActionPerformed

    private void btnCari_DSActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCari_DSActionPerformed
    try {
        DefaultTableModel model = (DefaultTableModel) tblSiswa.getModel();
        model.setRowCount(0); 
        String sql = "SELECT * FROM siswa WHERE nama LIKE ?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, "%" + tfcari.getText() + "%");
        ResultSet rs = ps.executeQuery();
        
        if (!rs.isBeforeFirst()) { 
            JOptionPane.showMessageDialog(null, "Data tidak ditemukan", "Informasi", JOptionPane.INFORMATION_MESSAGE);
        } else {
            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getString("id_siswa"),   
                    rs.getString("nama"),
                    rs.getString("kelas")
                });
            }
        tfcari.setText("");
        }
        
    } catch (SQLException e) {
        System.out.println("Error Load Data: " + e.getMessage());
    }
    }//GEN-LAST:event_btnCari_DSActionPerformed

    private void btnBack_DSActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBack_DSActionPerformed
        loadDataSiswa();
    }//GEN-LAST:event_btnBack_DSActionPerformed

    private void btnSave_DBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSave_DBActionPerformed
        if (tfJudul.getText().isEmpty()|| tfPengarang.getText().isEmpty()||  cbKategori.getSelectedItem() == null || cbKategori.getSelectedItem().equals("--PILIH--") || tfThnTerbit.getText().isEmpty()) {
        JOptionPane.showMessageDialog(this, "Harap Lengkapi Data terlebih dahulu", "Peringatan", JOptionPane.WARNING_MESSAGE);
        return;
    }
      try{            
            String sql = "INSERT INTO buku (judul, pengarang, kategori, tahun_terbit) VALUES (?, ?, ?, ?)";
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setString(1, tfJudul.getText());
                ps.setString(2, tfPengarang.getText());
                ps.setString(3, cbKategori.getSelectedItem().toString());
                ps.setString(4, tfThnTerbit.getText());
                ps.executeUpdate();
             JOptionPane.showMessageDialog(this, "Data yang di Tambahkan Sukses ");
            loadDataBuku();
            IdBuku();
            
        tfIDBuku.setText("");
        tfJudul.setText("");  
        tfPengarang.setText("");  
        cbKategori.setSelectedIndex(0);
        tfThnTerbit.setText(""); 

        } catch (SQLException e) {
            System.out.println("Error Save Data" + e.getMessage());
        }  
    }//GEN-LAST:event_btnSave_DBActionPerformed

    private void btnEdit_DBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEdit_DBActionPerformed
           if (tfIDBuku.getText().isEmpty() || tfJudul.getText().isEmpty()|| tfPengarang.getText().isEmpty()||  cbKategori.getSelectedItem() == null || cbKategori.getSelectedItem().equals("--PILIH--") || tfThnTerbit.getText().isEmpty()) {
        JOptionPane.showMessageDialog(this, "Harap Lengkapi Data terlebih dahulu", "Peringatan", JOptionPane.WARNING_MESSAGE);
        return;
    }
        try {
            String sql = "UPDATE buku SET judul = ?, pengarang = ?, kategori = ?, tahun_terbit = ?  WHERE id_buku = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, tfJudul.getText());
            ps.setString(2, tfPengarang.getText());
            ps.setString(3, cbKategori.getSelectedItem().toString());
            ps.setString(4, tfThnTerbit.getText());
            ps.setInt(5, Integer.parseInt(tfIDBuku.getText()));
            ps.executeUpdate();
            JOptionPane.showMessageDialog(this, "Data yang di Update Sukses");   
            loadDataBuku();
            IdBuku();
            
            tfIDBuku.setText("");
            tfJudul.setText("");  
            tfPengarang.setText("");  
            cbKategori.setSelectedIndex(0);
            tfThnTerbit.setText("");
        }  catch (SQLException e) {
            System.out.println("Error Save Data" + e.getMessage());
        }
    }//GEN-LAST:event_btnEdit_DBActionPerformed

    private void btnBack_DBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBack_DBActionPerformed
        loadDataBuku();
    }//GEN-LAST:event_btnBack_DBActionPerformed

    private void btnCari_DBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCari_DBActionPerformed
        try {
        DefaultTableModel model = (DefaultTableModel) tblBuku.getModel();
        model.setRowCount(0); 
        String sql = "SELECT * FROM buku WHERE judul LIKE ?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, "%" + tfCari_DB.getText() + "%");
        ResultSet rs = ps.executeQuery();
        
        if (!rs.isBeforeFirst()) { 
            JOptionPane.showMessageDialog(null, "Data tidak ditemukan", "Informasi", JOptionPane.INFORMATION_MESSAGE);
        } else {
            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getString("id_buku"),   
                    rs.getString("judul"),
                    rs.getString("pengarang"),
                    rs.getString("kategori"),
                    rs.getString("tahun_terbit")
                });
            }
        }
        tfCari_DB.setText("");
        
    } catch (SQLException e) {
        System.out.println("Error Load Data: " + e.getMessage());
    }
    }//GEN-LAST:event_btnCari_DBActionPerformed

    private void btnSave_DpnjmActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSave_DpnjmActionPerformed
    if (tglPeminjaman.getDate() == null|| tglKembali.getDate() == null) {
        JOptionPane.showMessageDialog(this, "Harap Lengkapi Data terlebih dahulu", "Peringatan", JOptionPane.WARNING_MESSAGE);
        return;
    }else{
            try{
                String sql = "INSERT INTO peminjaman (nama_siswa, judul_buku, tgl_peminjaman, tgl_kembali) VALUES (?, ?, ?, ?)";
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setString(1, cbNamaSiswa.getSelectedItem().toString());
                ps.setString(2, cbJudulBuku.getSelectedItem().toString());
                java.util.Date datePeminjaman = tglPeminjaman.getDate();
                java.util.Date dateKembali = tglKembali.getDate();
                if (datePeminjaman != null && dateKembali != null) {
                    ps.setDate(3, new java.sql.Date(datePeminjaman.getTime()));
                    ps.setDate(4, new java.sql.Date(dateKembali.getTime()));           
            }
               
                ps.executeUpdate();
                JOptionPane.showMessageDialog(this, "Data yang di Simpan Sukses");
                loadDPinjam();
                KPeminjaman();
                
                txtKP.setText("");
                tglPeminjaman.setDate(null);
                tglKembali.setDate(null);
                
            } catch (SQLException e) {
                System.out.println("Error Save Data" + e.getMessage());
            }
        }
    }//GEN-LAST:event_btnSave_DpnjmActionPerformed

    private void btnEdit_DPnjmActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEdit_DPnjmActionPerformed
    if (tblDPinjam.getRowCount() == 0) {
        JOptionPane.showMessageDialog(this, "TIDAK ADA DATA", "DISCLAIMER !!!", JOptionPane.WARNING_MESSAGE);
    } else {
        try {
            String sql = "SELECT * FROM peminjaman WHERE kode = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, Integer.parseInt(txtKP.getText())); 
            ResultSet rs = ps.executeQuery();

            if (!rs.next()) {
                JOptionPane.showMessageDialog(this, "TIDAK ADA DATA", "DISCLAIMER !!!", JOptionPane.WARNING_MESSAGE);
                return;  
            }

            java.util.Date tglPinjam = tglPeminjaman.getDate();
            java.util.Date tglKembalian = tglKembali.getDate();
            String upsql = "UPDATE peminjaman SET nama_siswa = ?, judul_buku = ?, tgl_peminjaman = ?, tgl_kembali = ? WHERE kode = ?";
            PreparedStatement upps = conn.prepareStatement(upsql);
            upps.setString(1, cbNamaSiswa.getSelectedItem().toString()); 
            upps.setString(2, cbJudulBuku.getSelectedItem().toString()); 

            if (tglPinjam != null) {
                upps.setDate(3, new java.sql.Date(tglPinjam.getTime()));
            } else {
                upps.setNull(3, java.sql.Types.DATE); 
            }
            if (tglKembalian != null) {
                upps.setDate(4, new java.sql.Date(tglKembalian.getTime()));
            } else {
                upps.setNull(4, java.sql.Types.DATE); 
            }

            upps.setInt(5, Integer.parseInt(txtKP.getText()));
            upps.executeUpdate();
            JOptionPane.showMessageDialog(this, "Data yang di Edit Sukses");
            loadDPinjam();
            cbNamaSiswa.setSelectedIndex(0);
            cbJudulBuku.setSelectedIndex(0);
            
        } catch (SQLException e) {
            System.out.println("Error updating data: " + e.getMessage());
            JOptionPane.showMessageDialog(this, "Terjadi kesalahan saat mengedit data", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    }//GEN-LAST:event_btnEdit_DPnjmActionPerformed

    private void btnHapus_DPnjmActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHapus_DPnjmActionPerformed
    try {
        String sql = "DELETE FROM peminjaman WHERE kode = ?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, Integer.parseInt(txtKP.getText()));
        ps.executeUpdate();

        conn.prepareStatement("SET @count = 0;").execute();
        conn.prepareStatement("UPDATE peminjaman SET kode = @count := @count + 1;").executeUpdate();
        conn.prepareStatement("ALTER TABLE peminjaman AUTO_INCREMENT = 1;").execute();

        JOptionPane.showMessageDialog(this, "Data yang dihapus sukses");
        loadDPinjam();
        
    } catch (SQLException e) {
        System.out.println("Error Save Data: " + e.getMessage());
    }
    }//GEN-LAST:event_btnHapus_DPnjmActionPerformed

    private void btnBack_DPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBack_DPActionPerformed
        loadDPinjam();
    }//GEN-LAST:event_btnBack_DPActionPerformed

    private void btnSave_DpngmActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSave_DpngmActionPerformed
    if (tglPengembalian.getDate() == null || tglDikembalikan.getDate() == null || cbStatus.getSelectedItem() == null || cbStatus.getSelectedItem().equals("--PILIH--")) {
    JOptionPane.showMessageDialog(this, "Harap Lengkapi Data terlebih dahulu", "Peringatan", JOptionPane.WARNING_MESSAGE);
} else {
    try {
        String kodePeminjaman = cb_pmnjmn.getSelectedItem().toString();

        String checkSql = "SELECT COUNT(*) FROM peminjaman WHERE kode = ?";
        try (PreparedStatement checkPs = conn.prepareStatement(checkSql)) {
            checkPs.setString(1, kodePeminjaman);
            try (ResultSet rs = checkPs.executeQuery()) {
                rs.next();
                int count = rs.getInt(1);
                if (count == 0) {
                    JOptionPane.showMessageDialog(this, "Kode Peminjaman tidak valid. Pastikan kode tersebut sudah ada.", "Peringatan", JOptionPane.WARNING_MESSAGE);
                    return; 
                }
            }
        }

        String sql = "INSERT INTO pengembalian (kode_peminjaman, tgl_pengembalian, tgl_dikembalikan, status) VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, kodePeminjaman);

            ps.setDate(2, new java.sql.Date(tglPengembalian.getDate().getTime()));
            ps.setDate(3, new java.sql.Date(tglDikembalikan.getDate().getTime()));

            String statusValue = (String) cbStatus.getSelectedItem();
            System.out.println("Status Value: " + statusValue);
            ps.setString(4, statusValue);

            ps.executeUpdate();
        }

        JOptionPane.showMessageDialog(this, "Data yang di Simpan Sukses");
        loadDKembali();

        tf_KP.setText("");
        tglPengembalian.setDate(null);
        tglDikembalikan.setDate(null);
        cb_pmnjmn.setSelectedIndex(0);
        cbStatus.setSelectedIndex(0);

    }catch (SQLException e) {
        System.out.println("Error Save Data: " + e.getMessage());
    }
}

    }//GEN-LAST:event_btnSave_DpngmActionPerformed

    private void btnCari_DPnjmActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCari_DPnjmActionPerformed
       try {
    DefaultTableModel model = (DefaultTableModel) tblDPinjam.getModel();
    model.setRowCount(0); 
    String sql = "SELECT * FROM peminjaman WHERE kode LIKE ?"; 
    PreparedStatement ps = conn.prepareStatement(sql);
    ps.setString(1, "%" + tfCari_DP.getText().trim() + "%"); 
    ResultSet rs = ps.executeQuery();
    
    if (!rs.next()) { 
        JOptionPane.showMessageDialog(null, "Data tidak ditemukan", "Informasi", JOptionPane.INFORMATION_MESSAGE);
    } else {
        do {
            model.addRow(new Object[]{
                rs.getString("kode"),  
                rs.getString("nama_siswa"),
                rs.getString("judul_buku"),
                rs.getString("tgl_peminjaman"),
                rs.getString("tgl_kembali")
            });
        } while (rs.next());
    }
    tfCari_DP.setText(""); 
} catch (SQLException e) {
    System.out.println("Error Load Data: " + e.getMessage());
}

    }//GEN-LAST:event_btnCari_DPnjmActionPerformed

    private void btnCari_DpngmActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCari_DpngmActionPerformed
        try {
        DefaultTableModel model = (DefaultTableModel) tblDKembali.getModel();
        model.setRowCount(0); 
        String sql = "SELECT * FROM pengembalian WHERE kode_pengembalian LIKE ?"; 
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, "%" + txt_cari.getText() + "%");
        ResultSet rs = ps.executeQuery();
        
        if (!rs.isBeforeFirst()) { 
            JOptionPane.showMessageDialog(null, "Data tidak ditemukan", "Informasi", JOptionPane.INFORMATION_MESSAGE);
        } else {
            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getString("kode_pengembalian"),   
                    rs.getString("kode_peminjaman"),   
                    rs.getString("tgl_pengembalian"),
                    rs.getString("tgl_dikembalikan"),
                    rs.getString("status")
                });
            }
        tfCari_DP.setText("");
        txt_cari.setText("");
        }
    } catch (SQLException e) {
        System.out.println("Error Load Data: " + e.getMessage());
    }
    }//GEN-LAST:event_btnCari_DpngmActionPerformed

    private void btnHapus_DpngmActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHapus_DpngmActionPerformed
   try {
        String sql = "DELETE FROM pengembalian WHERE kode_pengembalian = ?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, Integer.parseInt(tf_KP.getText()));
        ps.executeUpdate();

        conn.prepareStatement("SET @count = 0;").execute();
        conn.prepareStatement("UPDATE pengembalian SET kode_pengembalian = @count := @count + 1;").executeUpdate();
        conn.prepareStatement("ALTER TABLE pengembalian AUTO_INCREMENT = 1;").execute();

        JOptionPane.showMessageDialog(this, "Data yang dihapus sukses");
        loadDKembali();
        
    } catch (SQLException e) {
        System.out.println("Error Save Data: " + e.getMessage());
    }
    }//GEN-LAST:event_btnHapus_DpngmActionPerformed

    private void cb_pmnjmnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cb_pmnjmnActionPerformed
     String item= String.valueOf(this.cb_pmnjmn.getSelectedItem());
    
    String sql = "SELECT tgl_peminjaman FROM peminjaman WHERE kode = ?"; 
    try {
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, item); 
        
        ResultSet rs = ps.executeQuery();
        
        if (rs.next()) {
            java.sql.Date tglPeminjaman = rs.getDate("tgl_peminjaman");
            tglPengembalian.setDate(tglPeminjaman); 
        } else {
            tglPengembalian.setDate(null); 
            System.out.println("No date found for the selected kode_pengembalian.");
        }
    } catch (SQLException e) {
        System.out.println("Error loading date: " + e.getMessage());
    }
    }//GEN-LAST:event_cb_pmnjmnActionPerformed

    private void btnBack_DPngmbActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBack_DPngmbActionPerformed
        loadDKembali();
    }//GEN-LAST:event_btnBack_DPngmbActionPerformed

    private void btnConfirmActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConfirmActionPerformed
        loadDataAll();
        tblRiwayat.setVisible(true);
        btnPrint.setEnabled(true);
    }//GEN-LAST:event_btnConfirmActionPerformed

    private void btnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPrintActionPerformed
    String fileName = JOptionPane.showInputDialog(null, "Masukkan nama file PDF:", "Nama File", JOptionPane.PLAIN_MESSAGE);
    if (fileName == null || fileName.trim().isEmpty()) {
        JOptionPane.showMessageDialog(null, "Pembuatan PDF dibatalkan.");
        return;
    }

    if (!fileName.toLowerCase().endsWith(".pdf")) {
        fileName += ".pdf";
    }

    String filePath = "A:/.MINE/CHAPTER 3/Visual Programming/PDF/" + fileName;
    Document document = new Document (new Rectangle(950, 1500));
    PdfPTable table = new PdfPTable(9); 

    try {
        try {
            PdfWriter.getInstance(document, new FileOutputStream(filePath));
        } catch (FileNotFoundException ex) {
            Logger.getLogger(dataPerpustakaan.class.getName()).log(Level.SEVERE, null, ex);
        }
        document.open();
        Font fontHeader = new Font(Font.FontFamily.HELVETICA, 14, Font.BOLD);
        Paragraph title = new Paragraph("Laporan Riwayat Peminjaman", fontHeader);
        title.setAlignment(Element.ALIGN_CENTER);
        document.add(title);
        document.add(new Paragraph("\n"));

        String[] header = {"ID", "KODE PINJAM", "KODE KEMBALI", "ID SISWA", "ID BUKU", "TGL PINJAM", "TGL PENGEMBALIAN", "TGL DIKEMBALIKAN", "STATUS"};
        for (String column : header) {
            PdfPCell cell = new PdfPCell(new Phrase(column, fontHeader));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell);
        }
        for (int i = 0; i < Riwayat.getRowCount(); i++) {
            for (int j = 0; j < Riwayat.getColumnCount(); j++) {  
                Object cellValue = Riwayat.getValueAt(i, j);
                table.addCell(cellValue != null ? cellValue.toString() : "");
            }
        }

        document.add(table);
        JOptionPane.showMessageDialog(null, "PDF berhasil disimpan di lokasi: " + filePath);
        Tampilan frame = new Tampilan();
        frame.setVisible(true);
        this.dispose();

    } catch (DocumentException e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(null, "Gagal menyimpan PDF: " + e.getMessage());
    } finally {
        document.close();
    }
    }//GEN-LAST:event_btnPrintActionPerformed

    private void cbStatusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbStatusActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbStatusActionPerformed

    private void btnEdit_DpngmActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEdit_DpngmActionPerformed
       String status = cbStatus.getSelectedItem().toString();

    if (tglPengembalian.getDate() == null || tglDikembalikan.getDate() == null || cbStatus.getSelectedItem() == null || cbStatus.getSelectedItem().equals("--PILIH--")) {
        JOptionPane.showMessageDialog(this, "Harap Lengkapi Data terlebih dahulu", "Peringatan", JOptionPane.WARNING_MESSAGE);
        return; 
    }

    if (tblDKembali.getRowCount() == 0) {
        JOptionPane.showMessageDialog(this, "TIDAK ADA DATA", "DISCLAIMER !!!", JOptionPane.WARNING_MESSAGE);
        return; 
    }

    try {
        String sql = "SELECT * FROM pengembalian WHERE kode_pengembalian = ?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, Integer.parseInt(tf_KP.getText()));
        ResultSet rs = ps.executeQuery();

    if (!rs.next()) {
        JOptionPane.showMessageDialog(this, "TIDAK ADA DATA", "DISCLAIMER !!!", JOptionPane.WARNING_MESSAGE);
        return; 
    }
    
    String upsql = "UPDATE pengembalian SET kode_peminjaman = ?, tgl_dikembalikan = ?, status = ? WHERE kode_pengembalian = ?";
    PreparedStatement upps = conn.prepareStatement(upsql);
    int kodePeminjaman = Integer.parseInt(cb_pmnjmn.getSelectedItem().toString());
    upps.setInt(1, kodePeminjaman);

    if (tglDikembalikan.getDate() != null) {
        java.util.Date utilDate = tglDikembalikan.getDate();
        java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
        upps.setDate(2, sqlDate);
    } else {
        upps.setNull(2, java.sql.Types.DATE);
    }

    upps.setString(3, status);
    upps.setInt(4, Integer.parseInt(tf_KP.getText()));
    upps.executeUpdate();
    JOptionPane.showMessageDialog(this, "Data yang di Edit Sukses");
    loadDKembali(); 
   

} catch (SQLException e) {
    JOptionPane.showMessageDialog(this, "Error Save Data: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    e.printStackTrace();
} catch (NumberFormatException e) {
    JOptionPane.showMessageDialog(this, "Kode Pengembalian atau Kode Peminjaman harus berupa angka!", "Error", JOptionPane.ERROR_MESSAGE);
}
    }//GEN-LAST:event_btnEdit_DpngmActionPerformed

    private void tglPeminjamanPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_tglPeminjamanPropertyChange
    if ("date".equals(evt.getPropertyName())) { 
        Date selectedDate = tglPeminjaman.getDate(); 
        System.out.println("Selected Date: " + selectedDate); 
        if (selectedDate != null) {
            Calendar calendar = Calendar.getInstance(); 
            calendar.setTime(selectedDate); 
            calendar.add(Calendar.DAY_OF_MONTH, 5); 
            Date newDate = calendar.getTime(); 
            tglKembali.setDate(newDate); 
        } else {
            System.out.println("No date selected!"); 
        }
    }
    }//GEN-LAST:event_tglPeminjamanPropertyChange

    private void tglDikembalikanPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_tglDikembalikanPropertyChange
    if ("date".equals(evt.getPropertyName())) {
        java.util.Date selectedDate = (java.util.Date) evt.getNewValue();
        java.util.Date pengembalianDate = tglPengembalian.getDate();
        if (selectedDate != null && pengembalianDate != null) {
            if (selectedDate.before(pengembalianDate)) {
                JOptionPane.showMessageDialog(null, "Tanggal yang dimasukkan tidak valid. Harap pilih tanggal setelah tanggal pengembalian.", "Peringatan", JOptionPane.WARNING_MESSAGE);
                tglDikembalikan.setDate(null); 
            } else {
                java.util.Calendar calendar = java.util.Calendar.getInstance();
                calendar.setTime(pengembalianDate);
                calendar.add(java.util.Calendar.DAY_OF_MONTH, 5);
                java.util.Date fiveDaysAfter = calendar.getTime();
                
                if (selectedDate.after(fiveDaysAfter)) {
                    cbStatus.setSelectedItem("TERLAMBAT");
                } else {
                    cbStatus.setSelectedItem("TEPAT WAKTU");
                }
            }
        }
    }
    }//GEN-LAST:event_tglDikembalikanPropertyChange

    private void btnExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExitActionPerformed
        Tampilan frame = new Tampilan();
        frame.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btnExitActionPerformed

    
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(dataPerpustakaan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(dataPerpustakaan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(dataPerpustakaan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(dataPerpustakaan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new dataPerpustakaan().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> CBKelas;
    private javax.swing.JButton btnBack_DB;
    private javax.swing.JButton btnBack_DP;
    private javax.swing.JButton btnBack_DPngmb;
    private javax.swing.JButton btnBack_DS;
    private javax.swing.JButton btnCari_DB;
    private javax.swing.JButton btnCari_DPnjm;
    private javax.swing.JButton btnCari_DS;
    private javax.swing.JButton btnCari_Dpngm;
    private javax.swing.JButton btnClear_DB;
    private javax.swing.JButton btnClear_DPnjm;
    private javax.swing.JButton btnClear_DS;
    private javax.swing.JButton btnClear_Dpngm;
    private javax.swing.JButton btnConfirm;
    private javax.swing.JButton btnEdit_DB;
    private javax.swing.JButton btnEdit_DPnjm;
    private javax.swing.JButton btnEdit_DS;
    private javax.swing.JButton btnEdit_Dpngm;
    private javax.swing.JButton btnExit;
    private javax.swing.JButton btnHapus_DB;
    private javax.swing.JButton btnHapus_DPnjm;
    private javax.swing.JButton btnHapus_DS;
    private javax.swing.JButton btnHapus_Dpngm;
    private javax.swing.JButton btnPrint;
    private javax.swing.JButton btnSave_DB;
    private javax.swing.JButton btnSave_DS;
    private javax.swing.JButton btnSave_Dpngm;
    private javax.swing.JButton btnSave_Dpnjm;
    private javax.swing.JComboBox<String> cbJudulBuku;
    private javax.swing.JComboBox<String> cbKategori;
    private javax.swing.JComboBox<String> cbNamaSiswa;
    private javax.swing.JComboBox<String> cbStatus;
    private javax.swing.JComboBox<String> cb_pmnjmn;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable tblBuku;
    private javax.swing.JTable tblDKembali;
    private javax.swing.JTable tblDPinjam;
    private javax.swing.JTable tblRiwayat;
    private javax.swing.JTable tblSiswa;
    private javax.swing.JTextField tfCari_DB;
    private javax.swing.JTextField tfCari_DP;
    private javax.swing.JTextField tfIDBuku;
    private javax.swing.JTextField tfIDSiswa;
    private javax.swing.JTextField tfJudul;
    private javax.swing.JTextField tfNama;
    private javax.swing.JTextField tfPengarang;
    private javax.swing.JTextField tfThnTerbit;
    private javax.swing.JTextField tf_KP;
    private javax.swing.JTextField tfcari;
    private com.toedter.calendar.JDateChooser tglDikembalikan;
    private com.toedter.calendar.JDateChooser tglKembali;
    private com.toedter.calendar.JDateChooser tglPeminjaman;
    private com.toedter.calendar.JDateChooser tglPengembalian;
    private javax.swing.JTextField txtKP;
    private javax.swing.JTextField txt_cari;
    // End of variables declaration//GEN-END:variables
}
