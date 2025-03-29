package praktikum.tugas4;

public class Pelanggan {
    private final String ID_PELANGGAN;
    private final String NAMA_PELANGGAN;
    private final String PIN_PELANGGAN;
    private double saldo;
    private int loginAttempt;
    private boolean isBlokir;

    // Constructor Pelanggan
    public Pelanggan(String ID_PELANGGAN, String NAMA_PELANGGAN, String PIN_PELANGGAN, double saldo) {
        if (ID_PELANGGAN.length() != 10 || !ID_PELANGGAN.matches("\\d+") ) {
            throw new IllegalArgumentException("ID Pelanggan harus 10 digit angka");
        }
        this.ID_PELANGGAN = ID_PELANGGAN;
        this.NAMA_PELANGGAN = NAMA_PELANGGAN;
        this.PIN_PELANGGAN = PIN_PELANGGAN;
        this.saldo = saldo;
        this.loginAttempt = 0;
        this.isBlokir = false;
        
    }

    // Getter
    public String getID_PELANGGAN() {
        return ID_PELANGGAN;
    }
    
    public String getNAMA_PELANGGAN() {
        return NAMA_PELANGGAN;
    }

    public double getSaldo() {
        return saldo;
    }

    public int getLoginAttempt() {
        return loginAttempt;
    }

    public boolean isBlokir() {
        return isBlokir;
    }

    public boolean verifikasiPIN(String inputPIN) {
        if (isBlokir) {
            return false;
        }
        
        if (inputPIN.equals(PIN_PELANGGAN)) {
            loginAttempt = 0;
            return true;
        } else {
            loginAttempt++;
            if (loginAttempt >= 3) {
                isBlokir = true;
            }
            return false;
        }
    }

    public double topUp(int nominal) {
        if (nominal <= 0) {
            throw new IllegalArgumentException("Nominal top up harus lebih besar dari 0");
        }

        if (isBlokir) {
            throw new IllegalStateException("Akun diblokir, tidak dapat melakukan top up");
        }

        saldo += nominal;
        return saldo;
    }

    public String pembelian(int nominal) {
        if (isBlokir) {
            throw new IllegalStateException("Akun diblokir, tidak dapat melakukan pembelian");
        }
        if (nominal <= 0) {
            throw new IllegalArgumentException("Nominal pembelian harus lebih besar dari 0");
        }
        if (nominal > saldo) {
            throw new IllegalArgumentException("Saldo tidak cukup untuk melakukan pembelian");
        }

        String tipeAkun = ID_PELANGGAN.substring(0, 2);
        double cashback = 0;

        // Jenis Akun
        switch (tipeAkun) {
            case "38":
                if (nominal > 100000) {
                    cashback = nominal * 0.05;
                }
                break;
            case "56":
                if (nominal > 1000000) {
                    cashback = nominal * 0.07;
                } else {
                    cashback = nominal * 0.02;
                }
                break;
            case "74":
                if (nominal > 1000000) {
                    cashback = nominal * 0.1;
                } else {
                    cashback = nominal * 0.05;
                }
                break;
            default:
                throw new IllegalArgumentException("Tipe akun tidak valid");
        }

        // Perhitungan Akhir
        double totalPotong = nominal - cashback;
        double newSaldo = saldo - totalPotong; 

        if (saldo < 10000) {
            System.out.println("Saldo kurang dari Rp10.000, silakan top up");
        }
        
        // Saldo Baru
        saldo = newSaldo; 
        return String.format("Pembelian berhasil. Saldo baru: Rp%,.2f", saldo);
    }
}
