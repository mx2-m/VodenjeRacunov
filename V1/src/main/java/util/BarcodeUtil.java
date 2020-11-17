package util;

public final class BarcodeUtil {

    private BarcodeUtil() {
    }

    static public String getCompanyCountryFromBarcode(String ean13) {  //ne moze biti private
        String country;
        country = "unknown";
        int n = 0;
        char[] c = ean13.toCharArray();
        String vmes = "" + c[0] + "" + c[1] + "" + c[2];
        n = Integer.valueOf(vmes);
        if (000 == n) country = "ZDA";
        if (100 == n) country = "ZDA";
        if (300 <= n && n <= 370) country = "Francija";
        if (380 == n) country = "Bulgarija";
        if (383 == n) country = "Slovenija";
        if (385 == n) country = "Hrvaška";
        if (387 == n) country = "BIH";
        if (389 == n) country = "Črna Gora";
        if (400 <= n && n <= 440) country = "Nemčija";
        if (460 == n) country = "Rusija";
        if (470 == n) country = "Kirgistan";
        if (n == 471) country = "Taivan";
        if (n == 474) country = "Estonija";
        if (n == 475) country = "Latvija";
        if (n == 476) country = "Azerbejdžan";
        if (n == 477) country = "Litva";
        if (n == 478) country = "Uzbekistan";
        if (n == 479) country = "Šri Lanka";
        if (n == 480) country = "Filipini";
        if (n == 481) country = "Belorusija";
        if (n == 482) country = "Ukrajina";
        if (n == 484) country = "Moldavija";
        if (n == 485) country = "Armenija";
        if (n == 486) country = "Gruzija";
        if (n == 487) country = "Kazahstan";
        if (n == 489) country = "Hong Kong";
        if (490 == n) country = "Japonska";
        if (500 == n) country = "VB";
        if (n == 520) country = "Grčija";
        if (n == 528) country = "Libanon";
        if (n == 529) country = "Ciper";
        if (n == 530) country = "Albanija";
        if (n == 531) country = "Makedonija";
        if (n == 535) country = "Malta";
        if (n == 539) country = "Irska";
        if (540 == n) country = "Belgija & Luksemburg";
        if (n == 560) country = "Portugalska";
        if (n == 569) country = "Islandija";
        if (570 == n) country = "Danska";
        if (n == 590) country = "Polska";
        if (n == 594) country = "Romunija";
        if (n == 599) country = "Madžarska";
        if (n == 600 || n == 601) country = "Islandija";
        if (n == 608) country = "Bahrain";
        if (n == 609) country = "Mauricius";
        if (n == 611) country = "Maroko";
        if (n == 613) country = "Alžirija";
        if (n == 616) country = "Kenija";
        if (n == 619) country = "Tunizija";
        if (n == 621) country = "Sirija";
        if (n == 622) country = "Egipt";
        if (n == 624) country = "Libija";
        if (n == 625) country = "Jordanija";
        if (n == 626) country = "Iran";
        if (n == 627) country = "Kuvait";
        if (n == 628) country = "Saudova Arabija";
        if (n == 629) country = "Emirati";
        if (640 == n) country = "Finska";
        if (690 == n) country = "Kitajska";
        if (700 == n) country = "Norveška";
        if (n == 729) country = "Izrael";
        if (730 <= n && n <= 739) country = "Švedska";
        if (n == 740) country = "Gvatemala";
        if (n == 741) country = "El Salvador";
        if (n == 742) country = "Honduras";
        if (n == 743) country = "Nikaragva";
        if (n == 744) country = "Kostarika";
        if (n == 745) country = "Panama";
        if (n == 746) country = "Dom. republika";
        if (n == 750) country = "Mehika";
        if (754 == n || n == 755) country = "Kanada";
        if (n == 759) country = "Venezuela";
        if (760 == n) country = "Švica";
        if (n == 770) country = "Kolumbija";
        if (n == 773) country = "Kolumbija";
        if (n == 775) country = "Peru";
        if (n == 775) country = "Bolivija";
        if (n == 779) country = "Argentina";
        if (n == 780) country = "Čile";
        if (n == 784) country = "Paragvaj";
        if (n == 786) country = "Ekvador";
        if (789 == n || n == 790) country = "Brazilija";
        if (800 <= n && n <= 839) country = "Italija";
        if (840 == n) country = "Španija";
        if (n == 850) country = "Kuba";
        if (n == 858) country = "Slovaška";
        if (n == 859) country = "Češka";
        if (n == 860) country = "Srbija";
        if (n == 865) country = "Mongolija";
        if (n == 867) country = "Severna Koreja";
        if (n == 869) country = "Turčija";
        if (870 == n) country = "Nizozemska";
        if (n == 880) country = "Južna Koreja";
        if (n == 884) country = "Kambodja";
        if (n == 885) country = "Tajska";
        if (n == 888) country = "Singapur";
        if (n == 890) country = "Indija";
        if (n == 893) country = "Vietnam";
        if (n == 899) country = "Indonezija";
        if (900 <= n && n <= 910) country = "Avstrija";
        if (930 == n) country = "Avstralija";
        if (n == 955) country = "Malezija";
        if (n == 958) country = "Makao";

        return country;
    }

    public static boolean isBarcodeValid(String ean) {
        if (ean.length() != 13)
            return false;
        String[] d = ean.split("");
        short[] digits = new short[14];
        for (short i = 0; i < 13; i++) {
            digits[i] = Short.parseShort(d[i]);
            if (i % 2 == 1 && i < 12)
                digits[i] *= 3;
        }
        int sum = 0;
        for (int i = 0; i < 12; i++)
            sum = sum + digits[i];
        short lastDigit = (short) ((10 - (sum % 10)) % 10);
        if (digits[12] == lastDigit)
            return true;
        return false;
    }
}
