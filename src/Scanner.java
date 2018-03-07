/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Ahmed
 */
import java.io.*;

public class Scanner {

    private String readPath;
    private String writePath;
    private String fulltxt;

    private enum states {
        start, innum, inid, inassign, incomment, other, done
    };
    private states state;

    Scanner(String read, String write) {
        state = states.start;
        readPath = new String(read);
        writePath = new String(write);
    }

    public void readfile() throws FileNotFoundException, IOException {

        String line = null;
        try {
            FileReader fileReader = new FileReader(readPath);

            BufferedReader bufferedReader = new BufferedReader(fileReader);
            fulltxt = new String("");
            while ((line = bufferedReader.readLine()) != null) {
                fulltxt += line;
            }

            /*System.out.println(fulltxt);*/
            bufferedReader.close();
        } catch (FileNotFoundException ex) {
            System.out.println(
                    "Unable to open file '"
                    + readPath + "'");
        } catch (IOException ex) {
            System.out.println("Error reading file '"
                    + readPath + "'");
        }

    }

    public void clearOutFile() throws IOException {

        FileWriter fileWriter = new FileWriter(writePath);
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
        bufferedWriter.write("");
        bufferedWriter.close();
    }

    public void writefile(String i, String s) throws IOException {

        try {
            FileWriter fileWriter = new FileWriter(writePath, true);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

            String w = "" + i + " :" + s;
            bufferedWriter.append(w);
            bufferedWriter.newLine();
            bufferedWriter.close();

        } catch (IOException ex) {

            System.out.println(
                    "Unable to open file '"
                    + writePath + "'");
        }

    }

    public void scan() throws IOException {
        int i = 0;
        while (i < fulltxt.length()) {

            String temp = "";

            /**
             * ************start state*******************
             */
            if (state == states.start) 
                switch (fulltxt.charAt(i)) {

                    case '{':
                        state = states.incomment;
                        break;

                    case '+':
                    case '-':
                    case '*':
                    case '/':
                    case '=':
                    case '<':
                    case '(':
                    case ')':
                    case ';':
                        state = states.other;
                        break;

                    case '0':
                    case '1':
                    case '2':
                    case '3':
                    case '4':
                    case '5':
                    case '6':
                    case '7':
                    case '8':
                    case '9':
                        state = states.innum;
                        break;

                    case ':':
                        state = states.inassign;
                        break;

                    default:
                        state = states.inid;
                        break;

                }
            
            switch (state) {
                /**
                 * ********************number*********************
                 */
                case innum:
                    //i++;
                    temp = "";
                    while (numbercheck(fulltxt.charAt(i))) {
                        temp += fulltxt.charAt(i);
                        i++;
                    }
                    //if(fulltxt[i]!=' ')
                    i--;
                    writefile(temp, "number");
                    state = states.start;

                    break;

                /**
                 * ******************identifier**************************
                 */
                case inid:
                    //	i++;
                    temp = "";
                    while (charcheck(fulltxt.charAt(i))) {
                        temp += fulltxt.charAt(i);
                        i++;
                    }
                    //if (fulltxt[i] == ' ')
                    if (temp != "") {
                        i--;
                    }

                    if ((temp.equals("if")) ||(temp.equals("then")) || (temp.equals("else")) || (temp.equals("end"))
                            || (temp.equals("repeat")) || (temp.equals("until")) || (temp.equals("read")) || (temp.equals("write"))) 
                            {
                      writefile(temp, "reserved word");
                        
                    } 
                    else if (temp != "") {
                        writefile(temp, "identefier");
                    }
                    state = states.start;

                    break;

                /**
                 * ************************assign*************************
                 */
                case inassign:
                    //i++;
                    if (fulltxt.charAt(i + 1) == '=') {
                        writefile(":=", "assingment");
                    }
                    i++;
                    state = states.start;
                    break;

                /**
                 * ******************comment*********************
                 */
                case incomment:
                    i++;
                    while (fulltxt.charAt(i) != '}') {
                        i++;
                        //cout << " incom	";
                    }
                    //i++;
                    //cout << endl;
                    state = states.start;
                    break;

                /////////////other/////////////////////////////
                case other:
                    //i++;
                    temp = "";
                    temp += fulltxt.charAt(i);
                    writefile(temp, "special symbol");// symboletype(fulltxt[i]));

                    state = states.start;
                    break;

                /**
                 * ****************************************************
                 */
                default:
                    break;
            }

            i++;
        }

    }

    boolean numbercheck(char c) {
        switch (c) {
            case '0':
            case '1':
            case '2':
            case '3':
            case '4':
            case '5':
            case '6':
            case '7':
            case '8':
            case '9':
                return true;
            default:
                return false;
        }
    }

    boolean charcheck(char c) {
        switch (c) {

            case '0':
            case '1':
            case '2':
            case '3':
            case '4':
            case '5':
            case '6':
            case '7':
            case '8':
            case '9':
                return false;

            case '+':
            case '-':
            case '*':
            case '/':
            case '=':
            case '<':
            case '(':
            case ')':
            case ';':
            case ' ':
            case ':':
            case '{':
            case '}':
                return false;

            default:
                return true;

        }
    }
    
    public void gettoken()
    {
        
    }
}
