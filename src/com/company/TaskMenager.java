package com.company;

import org.apache.commons.lang3.ArrayUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Scanner;

public class TaskMenager {

    public static void main(String[] args){

        String[][] data = new String[0][0];
        data = readFile(data, "tasks.csv");
        Scanner scan = new Scanner(System.in);


        while(true){
            menu();
            String UserInput = scan.nextLine();
            if (UserInput.equals("add")){
                data = add(data);
            } else if (UserInput.equals("remove")){
                data = remove(data);

            } else if (UserInput.equals("list")){
                list(data);
            } else if (UserInput.equals("exit")){
                WriteFile(data, "tasks.csv");
                System.out.println("Succes");
                System.out.println(ConsoleColors.RED + "Bye Bye");
                break;
            }
        }

    }

    public static void menu(){
        System.out.println(ConsoleColors.BLUE + "Please select an option: ");
        System.out.println(ConsoleColors.RESET + "add");
        System.out.println("remove");
        System.out.println("list");
        System.out.println("exit");
    }

    public static String[][] readFile(String[][] data, String fileName){
        int index =0;
        File file = new File(fileName);

        try {
            Scanner scan = new Scanner(file);
            while(scan.hasNextLine()){
                String[] s = scan.nextLine().split(", ");
                data = Arrays.copyOf(data, data.length+1);
                data[index] = s;
                index++;
            }
        } catch (FileNotFoundException e){
            System.out.println("Nie ma takiego pliku");
        }
        return data;
    }

    public static void WriteFile(String[][] arr, String fileName){

        try(PrintWriter printWriter = new PrintWriter(fileName)){
            for (int i=0; i<arr.length; i++){
                for (int j=0; j<(arr[i].length-1); j++){
                    printWriter.print(arr[i][j]+", ");
                }
                printWriter.print(arr[i][(arr[i].length-1)]+ "\n");
            }

        } catch (FileNotFoundException e){
            System.out.println("Błąd zapisu do pliku");
        }


    }

    public static String[][] add(String[][] arr){

        arr = Arrays.copyOf(arr, arr.length+1);
        arr[arr.length-1] = new String[3];
        Scanner scan = new Scanner(System.in);
        System.out.println("Please add task description");
        arr[arr.length-1][0] = scan.nextLine();
        System.out.println("Please add task due date(yyyy-mm-dd)");
        arr[arr.length-1][1] = scan.nextLine();
        System.out.println("Is your task important: true/false");


        while (!scan.hasNextBoolean()){
            scan.next();
            System.out.println("Wrong data format");
        }
        arr[arr.length-1][2] = Boolean.toString(scan.nextBoolean());


        return arr;
    }

    public static void list(String[][] arr){

        for (int i = 0; i<arr.length; i++){
            System.out.print(i+" : ");
            for (int j = 0; j<arr[i].length; j++){
                System.out.print(arr[i][j]+" ");
            }
            System.out.println();
        }
    }

    public static String[][] remove(String[][] arr){
        System.out.println("Please select number to remove");
        int x = arr.length-1;
        Scanner scan = new Scanner(System.in);
        int index = 0;
            while(true) {
                try {
                    index = scan.nextInt();
                    if (index < 0) {

                        throw new IllegalArgumentException();
                    } else {
                        arr = ArrayUtils.remove(arr, index);
                        System.out.println("Data was removed successfully");
                        break;
                    }

                } catch (InputMismatchException e) {
                    scan.next();
                    System.out.println("Provide correct number");
                } catch (IllegalArgumentException | IndexOutOfBoundsException e){
                    System.out.println("Provide correct number");
                }
            }

        return arr;




    }

}
