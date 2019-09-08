
import java.awt.BorderLayout;
import java.awt.Color;
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
    JPanel sudokuPanel=new JPanel();
    //JPanel panel[][]=new JPanel[3][3];
    JTextField tf[]=new JTextField[81];
    JButton submitButton=new JButton("Submit");
    JButton readButton=new JButton("Import File");
    Data[][] solArray=new Data[9][9];
    public Sudoku(){
        frame.setLayout(new GridLayout(10,9));
        //sudokuPanel.setLayout(new GridLayout(9,9));
        int c=0;
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                tf[c]=new JTextField();
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
            System.out.println("THIS FUCKING TEST");
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
                        tf[pos++].setText(nums[i]);
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
                    n[i]=Integer.parseInt(""+tf[i].getText().charAt(1));
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