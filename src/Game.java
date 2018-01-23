import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Game {
    static  void enterName(Player p)throws IOException{
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String n = reader.readLine();
        p.setName(n);
        System.out.println("Корабли игрока "+p.getName()+" установлены");
    }
/**Помечает символом "**" выстрелы "мимо" на игровом поле игрока
 * @param coord координата,введеная игроком в консоль
 * @param p параметр для предоставления доступа к игровому полю игрока p.coordinates
 * */
    static void markEmptyCell(String coord,Player p){
        /*делим введеную координату на символ и цифру*/
        String i=coord.substring(0,1);
        String j=coord.substring(1);
        if(i.equals("A")){
            int indexI=0;//в зависимости от символа присваиваем номер индекса
            int indexJ=Integer.parseInt(j);
            p.getCoordinates()[indexI][indexJ].name="**";
        }
        if(i.equals("B")){
            int indexI=1;
            int indexJ=Integer.parseInt(j);
            p.getCoordinates()[indexI][indexJ].name="**";
        }
        if(i.equals("C")){
            int indexI=2;
            int indexJ=Integer.parseInt(j);
            p.getCoordinates()[indexI][indexJ].name="**";
        }
        if(i.equals("D")){
            int indexI=3;
            int indexJ=Integer.parseInt(j);
            p.getCoordinates()[indexI][indexJ].name="**";
        }
        if(i.equals("E")){
            int indexI=4;
            int indexJ=Integer.parseInt(j);
            p.getCoordinates()[indexI][indexJ].name="**";
        }
        if(i.equals("F")){
            int indexI=5;
            int indexJ=Integer.parseInt(j);
            p.getCoordinates()[indexI][indexJ].name="**";
        }
        if(i.equals("G")){
            int indexI=6;
            int indexJ=Integer.parseInt(j);
            p.getCoordinates()[indexI][indexJ].name="**";
        }
        if(i.equals("H")){
            int indexI=7;
            int indexJ=Integer.parseInt(j);
            p.getCoordinates()[indexI][indexJ].name="**";
        }
        if(i.equals("I")){
            int indexI=8;
            int indexJ=Integer.parseInt(j);
            p.getCoordinates()[indexI][indexJ].name="**";
        }
        if(i.equals("J")){
            int indexI=9;
            int indexJ=Integer.parseInt(j);
            p.getCoordinates()[indexI][indexJ].name="**";
        }    }
/**Возвращает true,если выстрелил в цель,false-мимо
 * @param coord координата,введеная игроком в консоль
 * @param makeHit игрок деляющий выстрел
 * @param tableProvider игрок в таблице которго ведется поиск координаты
 * */
    static boolean searchingShips(String coord,Player makeHit,Player tableProvider){
        coord=coord.toUpperCase();
        boolean flag=false;
        /*Перебираем каждую координату каждого корабля на предмет совпадения*/
        for (int i = 0; i <tableProvider.shipList.size() ; i++) {
            for (int j = 0; j < tableProvider.shipList.get(i).getShipCoord().size(); j++) {
                if (tableProvider.shipList.get(i).getShipCoord().get(j).name.equals(coord)) {//если совпадение
                    makeHit.setHitCounter(1);
                    /*Помечем на поле игрока(tableProvider)символом "ХХ" попадание*/
                    tableProvider.getCoordinates()[tableProvider.shipList.get(i).getShipCoord().get(j).I][tableProvider.shipList.get(i).getShipCoord().get(j).J].name="XX";
                    tableProvider.shipList.get(i).getShipCoord().remove(j);//удаляем координату корабля
                    if (tableProvider.shipList.get(i).getShipCoord().isEmpty()) {//если удалены все координаты для конкретного корабля
                        System.out.println("Вы потопили " + tableProvider.shipList.get(i).getName() + " корабль противника");
                        tableProvider.shipList.remove(i);//удаляем корабль
                    }
                    if(tableProvider.shipList.isEmpty()){//проверям не удален ли последный корабль игрока
                        System.out.println(makeHit.getName()+" Вы потопили все корабли противника за "+makeHit.getHitCounter()+" ходов");
                    }
                    flag = true;
                    return flag;
                }
            }
        }
        makeHit.setHitCounter(1);
        markEmptyCell(coord,tableProvider);
        return flag;
    }
/**Метод для отображения игрового поля игрока(отображает текущее состояние игрового поля игрока)*/
    static void showTable(Player p){
        for(int k=0;k<10;k++){
            System.out.print("\t"+k);
        }
        System.out.println();
        for (int i = 0; i < 10; i++) {
            char x=(char)(65+i);
            System.out.print(x+"\t");
            for (int j = 0; j < 10; j++) {
                System.out.print(p.getCoordinates()[i][j].name +"\t");
            }
            System.out.println();
        }
        System.out.println();
    }
/**очищает игровое поле игрока(возвращает к первоначальному виду) */
    static void showEmptyTable(Player p){
        for(int k=0;k<10;k++){
            System.out.print("\t"+k);
        }
        System.out.println();
        for (int i = 0; i < 10; i++) {
            char x=(char)(65+i);
            System.out.print(x+"\t");
            for (int j = 0; j < 10; j++) {
                p.getCoordinates()[i][j].name="--";
                System.out.print(p.getCoordinates()[i][j].name +"\t");
            }
            System.out.println();
        }
        System.out.println();
    }
/**тестовый метод для отображения координат каждого корабля игрока */
    static  void showCoordinates(Player p){
        for(int i=0;i<p.shipList.size();i++){
            for (int j = 0; j <p.shipList.get(i).getShipCoord().size() ; j++) {
                    System.out.print(p.shipList.get(i).getShipCoord().get(j).name+" ");
            }
            System.out.println();
        }
    }
/**Управляет игровым процессом
 */
    static void game(Player one,Player two)throws IOException{
        int playerName=1;//управляющая переменная для switch(1-игрок one,2-игрок two)
        boolean result;
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String coord;
        while (one.shipList.size()!=0 && two.shipList.size()!=0){//пока не потопим все корабли одного из игроков
            switch (playerName){
                case 1://всегда начинает 1 игрок
                    System.out.println(one.getName()+" Ваш ход,введите координату");
                    showTable(two);//отображает игровое поле игрока 1
                    System.out.println();
                    /*в цикле проверяем корректность введеных координат*/
                    while (true) {
                        coord = reader.readLine();
                        coord = coord.toUpperCase();
                        int x;
                        int y;
                            if ((coord.length() == 2)) {//координата всегда 2 символа
                                x = coord.charAt(0);
                                y = coord.charAt(1);
                                if ((x < 65 | x > 74) || (y < 48 | y > 57)) {//символы от A до J и от 0 до 9
                                    System.out.println("Недопустимая координата!Повторите ввод!");
                                } else break;
                            }else System.out.println("Недопустимая координата!Повторите ввод!");
                    }
                    result=searchingShips(coord,one,two);
                    if(result){
                        System.out.println("Вы попали в корабль противника!");

                    }else{
                        System.out.println("Вы промахнулись!!!");
                        playerName=2;//переход хода
                    }
                 break;

                case 2:
                    System.out.println(two.getName()+" Ваш ход,введите координату");
                    showTable(one);
                    System.out.println();
                    while (true){
                        coord = reader.readLine();
                        coord = coord.toUpperCase();
                        int x;
                        int y;
                        if ((coord.length() == 2)) {
                            x = coord.charAt(0);
                            y = coord.charAt(1);
                            if ((x < 65 | x > 74) || (y < 48 | y > 57)) {
                                System.out.println("Недопустимая координата!Повторите ввод!");
                            } else break;
                        }else System.out.println("Недопустимая координата!Повторите ввод!");
                    }
                    result=searchingShips(coord,two,one);
                    if(result){
                        System.out.println("Вы попали в корабль противника!");

                    }else{
                        System.out.println("Вы промахнулись!!!");
                        playerName=1;
                    }
                    break;
            }
        }
    }

    public static void main(String[] args) throws IOException {
        Player one = new Player();
        Player two = new Player();
        System.out.println("Ведите имя 1 игрока");
        enterName(one);
        one.createShips();
        showCoordinates(one);
        System.out.println("Ведите имя 2 игрока");
        enterName(two);
        two.createShips();
        showCoordinates(two);
        game(one, two);

    }
}
