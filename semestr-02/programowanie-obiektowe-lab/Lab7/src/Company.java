public class Company {
    public static void main(String[] args){
        //Programmer 1
        Employee programmer1= new Programmer("Jan","Student",2000,1);
        System.out.println("Programmer " + programmer1.imie +" "+ programmer1.nazwisko+" zarabia: "+programmer1.ObliczWyplatke());
        // ITemployee 1
        Employee employeeIt = new ITEmployee("Marzena","Bogata", 15000,3);
        System.out.println("ITEemployee " + employeeIt.imie +" "+ employeeIt.nazwisko+" zarabia: "+ employeeIt.ObliczWyplatke());
        //Programmer 2
        Employee programmer2 = new Programmer("Oskar","Przerwa-Tetmajer",8000,8);
        System.out.println("Programmer " + programmer2.imie +" "+ programmer2.nazwisko+" zarabia: "+programmer2.ObliczWyplatke());
        //Accountant 1
        Employee accountant = new Accountant("Konrad","Zielony",3500);
        System.out.println("Accountant " + accountant.imie +" "+ accountant.nazwisko+" zarabia: "+accountant.ObliczWyplatke());
    }

}