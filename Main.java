package praktikum.tugas4;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        // Data Pelanggan
        Pelanggan[] pelanggan = {
            new Pelanggan("3812345678", "Wilopo", "1234", 9000),
            new Pelanggan("5612345678", "Joko", "5678", 1500000),
            new Pelanggan("7412345678", "Agus", "9012", 3000000)
        };

        System.out.println("Selamat datang di Swalayan Tiny");
        Pelanggan currentPelanggan = null;

        // Proses login
        while (currentPelanggan == null) {
            System.out.print("Masukkan nomor pelanggan (10 digit): ");
            String idPelanggan = scanner.nextLine();
            
            System.out.print("Masukkan PIN: ");
            String pin = scanner.nextLine();
            scanner.nextLine();
            
            // Percobaan dan Verifikasi PIN
            for (Pelanggan pelangganItem : pelanggan) {
                if (pelangganItem.getID_PELANGGAN().equals(idPelanggan)) {
                    if (pelangganItem.verifikasiPIN(pin)) {
                        currentPelanggan = pelangganItem;
                        break;
                    } else {
                        System.out.println("PIN salah. Percobaan tersisa: " + 
                            (3 - pelangganItem.getLoginAttempt()));
                        if (pelangganItem.isBlokir()) {
                            System.out.println("Akun Anda telah dibekukan karena terlalu banyak percobaan login gagal.");
                            return;
                        }
                    }
                }
            }
            
            if (currentPelanggan == null) {
                System.out.println("Nomor pelanggan tidak ditemukan atau PIN salah.");
            }
        }

        // Pilihan Menu 
        System.out.println("\nLogin berhasil!");
        System.out.println("Selamat datang, " + currentPelanggan.getNAMA_PELANGGAN());
        System.out.printf("Saldo Anda: Rp%,.2f\n", (double) currentPelanggan.getSaldo());

        for (;;) {
            System.out.println("\nMenu:");
            System.out.println("1. Top Up");
            System.out.println("2. Pembelian");
            System.out.println("3. Cek Saldo");
            System.out.println("4. Keluar");
            System.out.print("Pilih menu (1-4): ");

            int pilihan = scanner.nextInt();
            scanner.nextLine();

            switch (pilihan) {
            case 1:
                System.out.print("Masukkan nominal top up: ");
                int nominalTopUp = scanner.nextInt();
                scanner.nextLine();

                if (nominalTopUp <= 0) {
                    System.out.println("Nominal top up harus lebih dari 0.");
                } else {
                    currentPelanggan.topUp(nominalTopUp);
                    System.out.printf("Top up berhasil. Saldo Anda sekarang: Rp%,.2f\n", (double) currentPelanggan.getSaldo());
                }
                break;
            case 2:
                System.out.print("Masukkan nominal pembelian: ");
                int nominalPembelian = scanner.nextInt();
                scanner.nextLine();

                if (nominalPembelian > currentPelanggan.getSaldo()) {
                    System.out.println("Saldo tidak cukup untuk melakukan pembelian.");
                } else if (nominalPembelian <= 0) {
                    System.out.println("Nominal pembelian harus lebih dari 0.");
                } else {
                    currentPelanggan.topUp(-nominalPembelian);
                    System.out.printf("Pembelian berhasil. Saldo Anda sekarang: Rp%,.2f\n", (double) currentPelanggan.getSaldo());
                }
                break;
            case 3:
                System.out.printf("Saldo Anda: Rp%,.2f\n", (double) currentPelanggan.getSaldo());
                break;
            case 4:
                System.out.println("Terima kasih telah menggunakan layanan kami.");
                return;
            default:
                System.out.println("Pilihan tidak valid. Silakan coba lagi.");
            }
        }
        
    }
    
}
