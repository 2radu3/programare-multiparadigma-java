public class Tranzactie {
    String Date;
    String Direction;
    String Symbol;
    int Quantity;
    double Price;

    public Tranzactie(String date, String direction, String symbol, int quantity, double price) {
        Date = date;
        Direction = direction;
        Symbol = symbol;
        Quantity = quantity;
        Price = price;
    }

    public Tranzactie() {
    }
}
