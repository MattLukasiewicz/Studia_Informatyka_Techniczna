import java.util.ArrayList;

public class Shop {
    public static void main(String[] args) {
        //Kosntruktor domyslny
        Item itemDom = new Item();
        itemDom.show();

        //Konstruktor z parametrem
        Item itemPara = new Item("Maslo",12.4, "karus");
        itemPara.show();

        //Konstruktor z kopiujacy
        Item itemCopy = new Item("Maslo",12.4, "wedel");
        itemCopy.show();

        System.out.println("Kolekcja");
        ArrayList<Item> Sklepik = new ArrayList<>();
        Sklepik.add(new Item());
        Sklepik.add(new Item("Pietruszka", 13.3, "marinera11"));
        Sklepik.add(new Item(itemCopy));
        for( Item item :Sklepik){
            item.show();
        }


        System.out.println("Tablica");
        Item[] tablicaItem = new Item[3];
        tablicaItem[0]= new Item();
        tablicaItem[1] = new Item("Marchew", 1.60, "lubella01");
        tablicaItem[2] = new Item(itemCopy);
        for( Item item :tablicaItem){
            item.show();
        }



        //System.out.println("countStatic_ in Shop: " + Item.countStatic_);
    }
}