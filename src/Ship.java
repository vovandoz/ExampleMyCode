import java.util.ArrayList;
public class Ship {
    /**Имя корабля(Четырехпалубный...Однопалубный) */
    private String name;
    /**Положение коробля на доске(верт./горизнт).0-горизонт.,1-вертик.*/
    private int direction;
    /**Поле для контроля направления установки корабля.Для вертикально установленных 0-шаг установки вверх(1-вниз).
     * Для горизонтально установленных 0-шаг установки вправо(1-влево).
     * step используется для определения варианта создания "ореола" недоступных координат вокруг установленного корабля,
     * используется в методах notAvailableFieldsForLeftRight,notAvailableFieldsForUpDown
     * */
    private  int step;
    /**Поле хранит координаты кораблей,группируя их(координаты) в корабли в зависимости от кол-ва палуб*/
    private ArrayList<Cell> shipCoord=new ArrayList<>();

    ArrayList<Cell> getShipCoord(){
        return  shipCoord;
    }
    int getStep(){
        return step;
    }

    int getDirection(){
        return direction;
    }
    String getName(){
        return name;
    }

    Ship(String name) {
        this.name=name;
    }
/**Метод устанавливает каждому кораблю,его координаты(размещает корабли на игровом поле игрока)
 * @param size - палубность судна
 * @param coordinates - двумерный массив координат(поле из класса Player)
 *@return возвращает true,если корабль успешно установлен
 * (по замыслу,корабль будет всегда установлен,кроме возникновения патовой ситуации)
 */
    boolean setShips(int size,Cell[][] coordinates) {
        int i;
        int j;
        do {
            i = (int) (Math.random() * 10);//задаем стартовую точку от которой будем "шагать" по доске
            j = (int) (Math.random() * 10);
        }
        while((coordinates[i][j].name.equals("XX"))|(coordinates[i][j].name.equals("OO")));//если стартовая точка содержит "ХX" или "OO"(т.е. уже  занята),то новые координаты
        int startPositionI = i;//сохраняем координаты стартовой точки
        int startPositionJ = j;
        ArrayList<Cell> tempList = new ArrayList<>();//служит для временного хранения ячеек
        boolean flag = false;
        /*Каждый из 4 циклов в методе позволяет попытаться установит корабль во всех возможных направлениях */
         try {
            while (true) {
                if ((coordinates[i][j].name.equals("XX"))|(coordinates[i][j].name.equals("OO")))//текущая Cell занята?
                    break;//прерываем цикл шагаем в другом направлении
                Cell cell=new Cell();
                cell.name=coordinates[i][j].name;
                cell.I=i;
                cell.J=j;
                tempList.add(cell);
                i++;
                if (tempList.size() == size) {
                    flag = true;
                    shipCoord.addAll(tempList);//копируем Cell в список координат корабля
                    direction=1;//вертикальное расположение корабля
                    step=1;//"шагаем" вниз
                    break;
                }
            }
        }
        catch (IndexOutOfBoundsException e) {
           // System.out.println("Выход за границы поля");
        }

        if(flag==true) {
            //System.out.println("Корабль успешно добавлен при шаге вниз");
            return flag;
        }else {
            i=startPositionI;//если предыдущий цикл был прерван,то задаем индексам координаты стартовой точки
            j=startPositionJ;//шагаем в другом направлении
            tempList.clear();//очищаем от Cell которые так и не пригодились

            try {
                while (true) {
                    if ((coordinates[i][j].name.equals("XX"))|(coordinates[i][j].name.equals("OO")))
                        break;
                    Cell cell=new Cell();
                    cell.name=coordinates[i][j].name;
                    cell.I=i;
                    cell.J=j;
                    tempList.add(cell);
                    j++;
                    if (tempList.size() == size) {
                        flag = true;
                        shipCoord.addAll(tempList);
                        direction=0;//горизонтальное
                        step=0;//вправо
                        break;
                    }
                }
            }catch (IndexOutOfBoundsException e) {
                //System.out.println("Выход за границы поля");
            }
        }

        if(flag==true){
           // System.out.println("Корабль успешно добавлен при шаге вправо");
            return flag;
        }else {
            i=startPositionI;
            j=startPositionJ;
            tempList.clear();
            try {
                while (true) {
                    if ((coordinates[i][j].name.equals("XX"))|(coordinates[i][j].name.equals("OO")))
                        break;
                    Cell cell=new Cell();
                    cell.name=coordinates[i][j].name;
                    cell.I=i;
                    cell.J=j;
                    tempList.add(cell);
                    i--;
                    if (tempList.size() == size) {
                        flag = true;
                        shipCoord.addAll(tempList);
                        direction=1;//вертикальное расположение корабля
                        step=0;//вверх
                        break;
                    }
                }
            }catch (IndexOutOfBoundsException e) {
               // System.out.println("Выход за границы поля");
            }
        }
        if(flag==true){
            //System.out.println("Корабль успешно добавлен при шаге вверх");
            return flag;
        }else{
            i=startPositionI;
            j=startPositionJ;
            tempList.clear();
            try {
                while (true) {
                    if ((coordinates[i][j].name.equals("XX"))|(coordinates[i][j].name.equals("OO")))
                        break;
                    Cell cell=new Cell();
                    cell.name=coordinates[i][j].name;
                    cell.I=i;
                    cell.J=j;
                    tempList.add(cell);
                    j--;
                    if (tempList.size() == size) {
                        flag = true;
                        shipCoord.addAll(tempList);
                        direction=0;//горизонтальное
                        step=1;//влево
                        break;
                    }
                }
            }catch (IndexOutOfBoundsException e) {
               // System.out.println("Выход за границы поля");
            }

        }
        if(flag==true){
           // System.out.println("Корабль успешно добавлен при шаге влево");
            return flag;
        }
        return flag;
    }
/**Метод делает не доступными координаты вокруг корабля,установленного горизонтально
 * @param coordinates двумерный массив координат(поле из класса Player) */
    void notAvailableFieldsForLeftRight(Cell [][] coordinates) {
            for (int numberOfElement = 0; numberOfElement < shipCoord.size(); numberOfElement++) {//перебираем координаты корабля,вызвавшего метод
                int iStart = shipCoord.get(numberOfElement).I;//сохраняем координату корабля как стартовую точку для хождения по доске
                int jStart = shipCoord.get(numberOfElement).J;
                int i = iStart;//присваиваем координаты стартовой точки индексам
                int j = jStart;
                        coordinates[i][j].name="OO";//Присваиваем "ОО"(условное обозначение ячейки занятой кораблем)
                /* Присваиваем "XX" ячейке,расположенной НАД ячейкой занятой кораблем(условное обозначение ячейки,ставшей недоступной)*/
                        try{
                            coordinates[--i][j].name="XX";
                        }catch (ArrayIndexOutOfBoundsException e){

                        }
                        /* Присваиваем "XX" ячейке,расположенной ПОД ячейкой занятой кораблем*/
                        try {
                            coordinates[i=i+2][j].name="XX";
                        }
                        catch (ArrayIndexOutOfBoundsException e){

                        }
                        if(numberOfElement==0){//если текущая координата корабля крайняя(первая)
                            i=iStart;//переприсваиваем индексам значения стартовой точки,т.к. уже двигались по доске
                            j=jStart;
                            if(step==1){//и если корабль устанавливался с шагом впарво
                                try {
                                    coordinates[i][++j].name="XX";
                                }
                                catch (ArrayIndexOutOfBoundsException e){

                                }
                            }else{//с шагом влево
                                try{
                                    coordinates[i][--j].name="XX";
                                }
                                catch (ArrayIndexOutOfBoundsException e){

                                }
                            }

                            try {
                                coordinates[--i][j].name="XX";
                            }
                            catch (ArrayIndexOutOfBoundsException e){

                            }
                            try {
                                coordinates[i=i+2][j].name="XX";
                            }
                            catch (ArrayIndexOutOfBoundsException e){

                            }
                        }
                        /*Аналогичные действия,если текущая координата корабля крайняя(последняя)*/
                        if(numberOfElement==shipCoord.size()-1){
                            i=iStart;
                            j=jStart;
                            if(step==1){
                                try{
                                    coordinates[i][--j].name="XX";
                                }
                                catch (ArrayIndexOutOfBoundsException e){

                                }
                            }else{
                                try {
                                    coordinates[i][++j].name="XX";
                                }
                                catch (ArrayIndexOutOfBoundsException e){

                                }
                            }

                            try {
                                coordinates[--i][j].name="XX";
                            }
                            catch (ArrayIndexOutOfBoundsException e){

                            }
                            try {
                                coordinates[i=i+2][j].name="XX";
                            }
                            catch (ArrayIndexOutOfBoundsException e){

                            }
                        }
            }
    }
    /**Метод делает не доступными координаты вокруг корабля,установленного вертикально
     * @param coordinates двумерный массив координат(поле из класса Player) */
    void notAvailableFieldsForUpDown(Cell[][] coordinates){
        for (int numberOfElement = 0; numberOfElement < shipCoord.size(); numberOfElement++) {
            int iStart = shipCoord.get(numberOfElement).I;
            int jStart = shipCoord.get(numberOfElement).J;
            int i = iStart;
            int j = jStart;
            coordinates[i][j].name = "OO";
            try {
                coordinates[i][--j].name = "XX";
            } catch (ArrayIndexOutOfBoundsException e) {

            }
            try {
                coordinates[i][j = j + 2].name = "XX";
            } catch (ArrayIndexOutOfBoundsException e) {

            }

            if (numberOfElement == 0) {
                i = iStart;
                j = jStart;
                if (step == 1) {
                    try {
                        coordinates[--i][j].name = "XX";
                    } catch (ArrayIndexOutOfBoundsException e) {

                    }
                } else {
                    try {
                        coordinates[++i][j].name = "XX";
                    } catch (ArrayIndexOutOfBoundsException e) {

                    }
                }

                try {
                    coordinates[i][--j].name = "XX";
                } catch (ArrayIndexOutOfBoundsException e) {

                }
                try {
                    coordinates[i][j = j + 2].name = "XX";
                } catch (ArrayIndexOutOfBoundsException e) {

                }
            }
            if (numberOfElement == shipCoord.size() - 1) {
                i = iStart;
                j = jStart;
                if (step == 1) {
                    try {
                        coordinates[++i][j].name = "XX";
                    } catch (ArrayIndexOutOfBoundsException e) {

                    }
                } else {
                    try {
                        coordinates[--i][j].name = "XX";
                    } catch (ArrayIndexOutOfBoundsException e) {

                    }
                }

                try {
                    coordinates[i][--j].name = "XX";
                } catch (ArrayIndexOutOfBoundsException e) {

                }
                try {
                    coordinates[i][j = j + 2].name = "XX";
                } catch (ArrayIndexOutOfBoundsException e) {

                }
            }
        }
    }
}