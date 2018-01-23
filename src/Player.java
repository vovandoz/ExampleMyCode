import java.util.ArrayList;

public class Player {
    private  String name;
    ArrayList<Ship> shipList=new ArrayList<>();//Список кораблей игрока
    private Cell[][] coordinates=setAlphabet();//координатная сетка(Доска игрока)
    private int hitCounter=0;

    public String getName(){
        return name;
    }

    public Cell[][] getCoordinates() {
        return coordinates;
    }

    public void setName(String n){
        name=n;
    }

    public int getHitCounter(){
        return hitCounter;
    }

    public void setHitCounter(int h){
        hitCounter=hitCounter+h;
    }
/**Метод присваивает игровому полю игрока(Cell элементы) */
    Cell[][] setAlphabet() {
        Cell[][] coordinates = new Cell[10][10];        //Внешний цикл задает  символы от A  до J
        for (int i = 0; i < coordinates.length; i++) {
            char x = (char) (65 + i);
            for (int j = 0; j < coordinates[i].length; j++) { //Внутренний цикл добавляет к каждому символу цифру
                Cell cell=new Cell();                         //Присваевает каждый полученный символ(A0...A9)полю cell.name
                String s = String.valueOf(x);                 //cell.I,J хранят координаты ячейки
                s = s + j;
                cell.name=s;
                cell.I=i;
                cell.J=j;
                coordinates[i][j]=cell;
            }
        }

        return coordinates;
    }


    void addShipToList(Ship s){
        shipList.add(s);
    }
/**Метод создает корабли игрока и добавляет созданные корабли в список кораблей игрока(shipList) */
    void createShips(){
            Ship ship4=new Ship("Четырехпалубный");
            ship4.setShips(4,coordinates);
            if(ship4.getDirection()==1){//проверка типа установки корабля
                ship4.notAvailableFieldsForUpDown(coordinates);//исключаем доступные  координаты(вокруг установленного корабля)
            }
            else ship4.notAvailableFieldsForLeftRight(coordinates);
            addShipToList(ship4);
         for (int i = 1; i < 3; i++) {
             Ship ship=new Ship("Трехпалубный");
             addShipToList(ship);
             /*цикл обрабытывает  патовую ситуацию*/
             while (!(shipList.get(i).setShips(3,coordinates)));//цикл прекращается как только координаты будут присвоенны
             if(shipList.get(i).getDirection()==1){
                 shipList.get(i).notAvailableFieldsForUpDown(coordinates);
             }
             else shipList.get(i).notAvailableFieldsForLeftRight(coordinates);
         }

         for (int i = 3; i < 6; i++) {
             Ship ship=new Ship("Двухпалубный");
             addShipToList(ship);
             while (!(shipList.get(i).setShips(2,coordinates)));
             if(shipList.get(i).getDirection()==1){
                 shipList.get(i).notAvailableFieldsForUpDown(coordinates);
             }
             else shipList.get(i).notAvailableFieldsForLeftRight(coordinates);
         }
         for (int i = 6; i < 10; i++) {
             Ship ship=new Ship("Однопалубный");
             addShipToList(ship);
             while (!(shipList.get(i).setShips(1,coordinates)));
             if(shipList.get(i).getDirection()==1){
                 shipList.get(i).notAvailableFieldsForUpDown(coordinates);
             }
             else shipList.get(i).notAvailableFieldsForLeftRight(coordinates);
         }
         Game.showEmptyTable(this);
    }
}
