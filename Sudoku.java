
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;

class Data{
    ArrayList<Integer> posSol= new ArrayList<Integer>();
}

public class Sudoku implements ActionListener{
    JFrame frame=new JFrame();
    JFrame comFrame=new JFrame();
    JLabel winIndic=new JLabel("Sudoku has been solved!");
    JPanel sudokuPanel=new JPanel();
    //JPanel panel[][]=new JPanel[3][3];
    JTextField tf[]=new JTextField[81];
    JButton submitButton=new JButton("Submit");
    JButton readButton=new JButton("Import File");
    Data[][] posSolution=new Data[9][9];
    public Sudoku(){
        frame.setLayout(new GridLayout(10,9));
        //sudokuPanel.setLayout(new GridLayout(9,9));
        int c=0;
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                tf[c]=new JTextField();
                tf[c].setHorizontalAlignment(JLabel.CENTER);
                tf[c].setFont( new Font(tf[c].getText(), Font.PLAIN, 20));
                frame.add(tf[c]);
                c++;
            }
        }
        Color lb = Color.decode("#64b2cd");
        Color db = Color.decode("#3c70a4");
        int sw=0;
        for (int i = 0; i < 81; i++) {
            if(i%3==0){
                //switch if i==3 but not if i==9 but do if 27 or 54
                if(i%9!=0){
                    if(sw==0)
                        sw=1;
                    else
                        sw=0;
                }
                if(i==27||i==54){
                    if(sw==0)
                        sw=1;
                    else
                        sw=0;
                }
            }
            if(sw==0)
                tf[i].setBackground(lb);
            else
                tf[i].setBackground(db);
//            if(i==26||i==53)
//                continue;
            
        }
        //frame.setLayout(new BorderLayout());
        //frame.add(sudokuPanel,BorderLayout.NORTH);
        submitButton.addActionListener(this);
        readButton.addActionListener(this);
        //frame.add(submitButton,BorderLayout.SOUTH);
        frame.add(submitButton);
        frame.add(readButton);
        frame.setSize(500,550);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        
        comFrame.setLayout(new BorderLayout());
        comFrame.add(winIndic,BorderLayout.CENTER);
        comFrame.setSize(300,100);
        comFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }
    
    public void fillSudoku(int[][] sudoku){
        
        int xpos=0;
        int ypos=0;
        int flag=0;
        int cflag=0;
        int finFlag=0;
//        for (int i = 0; i < 9; i++) {
//            for (int j = 0; j < 9; j++) {
//                solArray[i][j]=new Data();
//            }
//        }
        do{
            finFlag=0;
            for (int i = 1; i <= 9; i++) {
                cflag=0;
                do{
                    
                //put checking condition for boundary of xpos and ypos here
                flag=0;
                for (int j = xpos; j < xpos+3; j++) {
                    for (int k = ypos; k < ypos+3; k++) {
                        if(sudoku[j][k]==i){
                            flag=1;
                            break;
                        }
                    }
                    if(flag==1)
                        break;
                    }
                    if(flag==0){
                        int c=0;
                        int x = 0,y = 0;
                        for (int j = xpos; j < xpos+3; j++) {
                            for (int k = ypos; k < ypos+3; k++) {
                                //check validity for each number if not filled
                                if(sudoku[j][k]!=0)
                                    continue;
                                else{
                                    if(checkValidity(sudoku,j,k,i)){
                                        //solArray[j][k].posSol.add(i);
                                        c++;
                                        x=j;
                                        y=k;
                                    }
                                }
                            }
                        }
                        if(c==1){
                            finFlag++;
                            sudoku[x][y]=i;
                            for (int j = 0; j < 9; j++) {
                                for (int k = 0; k < 9; k++) {
                                    System.out.print(sudoku[j][k]+" ");
                                }
                                System.out.println("");
                            }
                            System.out.println("");
                            int u=0;
                            for (int j = 0; j < 9; j++) {
                                for (int k = 0; k < 9; k++) {
                                    tf[u++].setText(""+sudoku[j][k]);
                                }
                            }
                        }
                    }
                    if(xpos==6&&ypos==6){
                        xpos=0;
                        ypos=0;
                        cflag++;
                    }

                    else if(ypos==6){
                        ypos=0;
                        xpos+=3;
                    }
                    else{
                        ypos+=3;
                    }
                }while(cflag<9);
        }
        }while(finFlag!=0);
        int comFlag=0;
        for (int i = 0; i < 81; i++) {
            if(tf[i].getText().equals("0")){
                fillOnlyPosSolution(sudoku);
                comFlag=1;
                break;
            }
        }
        if(comFlag==0){
            comFrame.setVisible(true);
        }
    }
    
    public void fillOnlyPosSolution(int[][] sudoku){
        System.out.println("Entered fillOnlyPosSolution function");
        int cflag,flag;
        int xpos=0;
        int ypos=0;
        int n=0;
        int count=0;
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if(sudoku[i][j]==0)
                    posSolution[i][j]=new Data();
            }
        }
        do{
            System.out.println("Entered do while loop");
            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 9; j++) {
                    if(sudoku[i][j]==0){
                        posSolution[i][j].posSol.clear();
                    }
                }
            }
            count=0;
            for (int i = 1; i <= 9; i++) {
                cflag=0;
                do{
                flag=0;
                for (int j = xpos; j < xpos+3; j++) {
                    for (int k = ypos; k < ypos+3; k++) {
                        if(sudoku[j][k]==i){
                            flag=1;
                            break;
                        }
                    }
                    if(flag==1)
                        break;
                    }
                    if(flag==0){
                        int c=0;
                        int x = 0,y = 0;
                        for (int j = xpos; j < xpos+3; j++) {
                            for (int k = ypos; k < ypos+3; k++) {
                                //check validity for each number if not filled
                                if(sudoku[j][k]!=0)
                                    continue;
                                else{
                                    if(checkValidity(sudoku,j,k,i)){
                                        if(!posSolution[j][k].posSol.contains(i))
                                            posSolution[j][k].posSol.add(i);
                                        //c++;
                                        x=j;
                                        y=k;
                                    }
                                }
                            }
                        }
//                        if(c==1){
//                            sudoku[x][y]=i;
//                            for (int j = 0; j < 9; j++) {
//                                for (int k = 0; k < 9; k++) {
//                                    System.out.print(sudoku[j][k]+" ");
//                                }
//                                System.out.println("");
//                            }
//                            System.out.println("");
//                            int u=0;
//                            for (int j = 0; j < 9; j++) {
//                                for (int k = 0; k < 9; k++) {
//                                    tf[u++].setText(""+sudoku[j][k]);
//                                }
//                            }
//                        }
                    }
                    if(xpos==6&&ypos==6){
                        xpos=0;
                        ypos=0;
                        cflag++;
                    }

                    else if(ypos==6){
                        ypos=0;
                        xpos+=3;
                    }
                    else{
                        ypos+=3;
                    }
                }while(cflag<9);
        }
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if(sudoku[i][j]==0){
                    System.out.print("For pos "+i+", "+j+": ");
                    for (int k = 0; k < posSolution[i][j].posSol.size(); k++) {
                        System.out.print(posSolution[i][j].posSol.get(k)+" ");
                    }
                    System.out.println("");
                }
            }
        }
            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 9; j++) {
                    if(sudoku[i][j]==0){
                        if(posSolution[i][j].posSol.size()==1){
                            sudoku[i][j]=posSolution[i][j].posSol.get(0);
                            for (int k = 0; k < 9; k++) {
                                for (int l = 0; l < 9; l++) {
                                    System.out.print(sudoku[k][l]+" ");
                                }
                                System.out.println("");
                            }
                            System.out.println("");
                            int u=0;
                            for (int k = 0; k < 9; k++) {
                                for (int l = 0; l < 9; l++) {
                                    tf[u++].setText(""+sudoku[k][l]);
                                }
                            }
                            count++;
                        }
                    }
                }
            }
        }while(count!=0);
        int f=0;
        for (int i = 0; i < 81; i++) {
            if(tf[i].getText().equals("0")){
                //call next function
                System.out.println("Final state:");
                for (int j = 0; j < 9; j++) {
            for (int k = 0; k < 9; k++) {
                if(sudoku[j][k]==0){
                    System.out.print("For pos "+j+", "+k+": ");
                    for (int l = 0; l < posSolution[j][k].posSol.size(); l++) {
                        System.out.print(posSolution[j][k].posSol.get(l)+" ");
                    }
                    System.out.println("");
                }
            }
        }
                f=1;
                break;
            }
        }
        if(f==0){
            comFrame.setVisible(true);
        }
    }
    
    public boolean checkValidity(int[][] sudoku, int x,int y,int num){
        for (int i = 0; i < 9; i++) {
            if(sudoku[x][i]==num)
                return false;
            if(sudoku[i][y]==num)
                return false;
        }
        return true;
    }
    
    @Override
    public void actionPerformed(ActionEvent arg0) {
        if(arg0.getActionCommand()=="Import File"){
            JFileChooser j = new JFileChooser("f:"); 
            int r = j.showOpenDialog(null); 
            File fi = new File(j.getSelectedFile().getAbsolutePath());
            String line = "";
            String cvsSplitBy = ",";
            BufferedReader br = null;
            int pos=0;
            try {
                br = new BufferedReader(new FileReader(fi));
                while ((line = br.readLine()) != null) {
                    String[] nums=line.split(",");
                    for (int i = 0; i < nums.length; i++) {
                        if(i==0){
                            if(nums[i].length()==2){
                                tf[pos++].setText(""+nums[i].charAt(1));
                            }
                            else
                                tf[pos++].setText(nums[i]);
                        }
                        else{
                            tf[pos++].setText(nums[i]);
                        }
                        
                    }
                }

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (br != null) {
                    try {
                        br.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        if(arg0.getActionCommand()=="Submit"){
            int[][] sudokuNums=new int[9][9];
            int c=0;
            int[] n=new int[81];
            for (int i = 0; i < 81; i++) {
//                if(tf[i].getText().equals("0"))
//                    n[i]=0;
//                else
                if(i==0)
                    n[i]=Integer.parseInt(""+tf[i].getText());
                else
                    n[i]=Integer.parseInt(tf[i].getText());
            }
            
            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 9; j++) {
                    sudokuNums[i][j]=n[c++];
                }
            }
            fillSudoku(sudokuNums);
        }
    }
    
    public static void main(String[] args) {
        new Sudoku();
    }
}