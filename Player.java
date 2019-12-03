import java.io.*;
import java.util.*;

// Player class

class player{
    container store;
    container[] holes = new container[6];
    player(){
        store = new container(0);
        for (int i = 0; i < 6; i++){
            holes[i] = new container(4);
        }
    }
}

class container{
    int total;
    container(int n){
        total = n;
    }
}

class main{
    public static int sum(container[] l){
        int total = 0;
        for (int i = 0; i < l.length; i++)
            total += l[i].total;
        return total;
    }
    public static void main(String[] args){
        //Controller b = new Controller();
        Server server = new Server();
        Client client = new Client();
        player p1 = new player();
        player p2 = new player();
        int n = 0;
        int hole;
        int amount;
        boolean ch;
        Scanner sc = new Scanner(System.in);
        while (sum(p1.holes) != 0 && sum(p2.holes) != 0){
            ch = false;
            System.out.println("");
            System.out.println("\t\t\t\t\t\t   Player 2 Side");
            System.out.println("\t\t\t\t\t\t\t Holes 6-1");
            System.out.print("                    ");
            for (int i = 5; i >= 0; i--) //for player 1
                System.out.print("  "+ p2.holes[i].total+"  ");
            System.out.println("");
            System.out.print("Player 2 Store: "+p2.store.total);
            System.out.println("\t\t\t\t\t\t\t\t\t Player 1 Store: "+p1.store.total);
            System.out.print("                    ");
            for (int i = 0; i < 6; i++)  //for player 2
                System.out.print("  "+ p1.holes[i].total+"  ");
            System.out.println("");
            System.out.println("\t\t\t\t\t\t\t Holes 1-6");
            System.out.println("\t\t\t\t\t\t   Player 1 Side");
            System.out.println("");

            if (n % 2 == 0){
                System.out.println("Player 1, from which hole do you want to pick up the stones?");
                hole = sc.nextInt();
                amount = p1.holes[hole-1].total;
                if (amount == 0)
                    continue;
                p1.holes[hole-1].total = 0;
                for (int i = hole; i < 6; i++){
                    if (p1.holes[i].total == 0 && amount == 1){
                        p1.store.total += amount + p2.holes[i].total;
                        p2.holes[i].total = 0;
                        break;
                    }
                    else{
                        p1.holes[i].total++;
                        amount--;
                        if (amount == 0) break;
                    }
                }
                if (amount != 0){
                    p1.store.total++;
                    amount--;
                    if (amount == 0)
                        ch = true;
                }
                while (amount != 0){
                    for (int i = 0; i < 6; i++){
                        p2.holes[i].total++;
                        amount--;
                        if (amount == 0) break;
                    }
                    if (amount == 0) break;
                    for (int i = 0; i < 6; i++){
                        if (p1.holes[i].total == 0 && amount == 1){
                            p1.store.total += amount + p2.holes[i].total;
                            p2.holes[i].total = 0;
                            break;
                        }
                        else{
                            p1.holes[i].total++;
                            amount--;
                            if (amount == 0) break;
                        }
                    }
                    if (amount == 0) break;
                    p1.store.total++;
                    amount--;
                    if (amount == 0){
                        ch = true;
                        break;
                    }
                }
                if (ch == false) n = 1;
            }
            else{
                System.out.println("Player 2, from which hole do you want to pick up the stones?");
                hole = sc.nextInt();
                amount = p2.holes[hole-1].total;
                p2.holes[hole-1].total = 0;
                for (int i = hole; i < 6; i++){
                    if (p2.holes[i].total == 0 && amount == 1){
                        p2.store.total += amount + p1.holes[i].total;
                        p1.holes[i].total = 0;
                        break;
                    }
                    else{
                        p2.holes[i].total++;
                        amount--;
                        if (amount == 0) break;
                    }
                }
                if (amount != 0){
                    p2.store.total++;
                    amount--;
                    if (amount == 0)
                        ch = true;
                }
                while (amount != 0){
                    for (int i = 0; i < 6; i++){
                        p1.holes[i].total++;
                        amount--;
                        if (amount == 0) break;
                    }
                    if (amount == 0) break;
                    for (int i = 0; i < 6; i++){
                        if (p2.holes[i].total == 0 && amount == 1){
                            p2.store.total += amount + p1.holes[i].total;
                            p1.holes[i].total = 0;
                            break;
                        }
                        else{
                            p2.holes[i].total++;
                            amount--;
                            if (amount == 0) break;
                        }
                    }
                    if (amount == 0) break;
                    p2.store.total++;
                    amount--;
                    if (amount == 0) {
                        ch = true;
                        break;
                    }
                }
                if (ch == false)   n = 0;
            }
        }
        p1.store.total += sum(p1.holes);
        p2.store.total += sum(p2.holes);
        if (p1.store.total > p2.store.total)
            System.out.println("Player 1 has more Stones and Wins");
        else
            System.out.println("Player 2 has more Stones and Wins");
        System.out.println("Hit Enter to close");
    }
}