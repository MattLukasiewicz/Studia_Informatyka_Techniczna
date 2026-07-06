public class Date {
    int day;
    int month;
    int year;

    public Date(int day, int month, int year) {
        this.day = day;
        this.month = month;
        this.year = year;
    }

    public void showme() {
        System.out.println("Data przydatności: " + day + "/" + month + "/" + year);
    }
}