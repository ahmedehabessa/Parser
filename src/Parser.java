
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Ahmed
 */
public class Parser {
    
    private static int i=0;
    private LinkedList <String> tokens;
    private LinkedList <TreeNode> tree=new LinkedList<TreeNode>();
    private LinkedList <String> syntax;
    private String token;
    private String syntaxx;
    private String readPath;
    private String writePath;
   
    
    Parser(String readPath ,String writePath )
    {
        token=new String();
        tokens = new LinkedList<String>();
        syntax = new LinkedList<String>();
        this.readPath = new String(readPath);
        this.writePath = new String(writePath);

    }
    
    public void readFile()
    {
        
        String line = null;
        try {
            FileReader fileReader = new FileReader(readPath);

            BufferedReader bufferedReader = new BufferedReader(fileReader);
            //fulltxt = new String("");
            while ((line = bufferedReader.readLine()) != null) {
                String [] s = line.split(" :");
            if(s[1].equals("reserved word") || s[1].equals("special symbol") || s[1].equals("assingment"))
                {
                    tokens.addLast(s[0]);
                }
                else 
                {
                    tokens.addLast(s[1]);
                }
                syntax.addLast(s[0]);
            }
              bufferedReader.close();
//              System.out.println(tokens);
//              System.out.println(syntax);
              
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

    
    public void writeFile(String s)
    {
       try {
            FileWriter fileWriter = new FileWriter(writePath, true);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            
//                l.addFirst(s);
            String w = "" + i + " :" + s;
            bufferedWriter.append(s);
            bufferedWriter.newLine();
            bufferedWriter.close();
            
         //   System.out.println(s);
            
        } catch (IOException ex) {

            System.out.println(
                    "Unable to open file '"
                    + writePath + "'");
        }

        
    }
    
    
    
    void match(String expectedToken)
    {
        
        if(token.equals(expectedToken))
        {
            getToken();
        }
        else
        {
       
          System.out.println("cant match tokennnnnnnnnnnnnnnn");
            
        }
        
    }
    void getToken ()
    {
        if(i<tokens.size())
        {token=tokens.get(i);
        if(i>0)
        syntaxx=syntax.get(i-1);
        i++;
        }
    }
    
    TreeNode Program()
    {  
        TreeNode n = new TreeNode();
        getToken();
        n = stmt_sequence();
        writeFile("Program found");
        
        return n;
    }
    
    TreeNode  stmt_sequence()
    {
        TreeNode n = new TreeNode();
        LinkedList<TreeNode> stmtChild = new LinkedList<TreeNode>();
        
        stmtChild.addLast(stmt());
        while (token.equals(";"))
           
        {
            getToken();
            stmtChild.addLast(stmt());
        }
        writeFile("statment sequence found");
        Iterator i = stmtChild.iterator();
        while(i.hasNext())
        {
            n.setChild((TreeNode) i.next());
        }
        return n;
    }
    
    TreeNode stmt()
    {
       TreeNode n  = new TreeNode();  // can we equal initialized node with anoter one ???
//        getToken();
        switch (token)
        {
            case "if":
                n= if_stmt();
                break;
            case "repeat":
                n= repeat_stmt();
                break;
            case "identefier":
                n= assign_stmt();
                break;
            case "read":
                n = read_stmt();
                break;
            case "write":
                n = write_stmt();
                break;

        }
        
        writeFile ("statment found");
        return n;
    }
    TreeNode if_stmt()
    {   
        match("if");
        
        TreeNode n = new TreeNode();
        TreeNode testChild = new TreeNode();
        TreeNode thenChild = new TreeNode();
        TreeNode elseChild = new TreeNode();
        testChild = exp();
        n.setName("if");
        match("then");
        
        thenChild = stmt_sequence();
        
        n.setChild(testChild);
        n.setChild(thenChild);
        
        if (token.equals("else")) {
            match("else");
            elseChild = stmt();
            n.setChild(elseChild);
        }

        match("end");
        writeFile("if statment found");
        return n;
    }
    
    TreeNode repeat_stmt()
    {   
        TreeNode n = new TreeNode();
        TreeNode bodyChild = new TreeNode();
        TreeNode testChild = new TreeNode();
        match ("repeat");
        n.setName("repeat");
        bodyChild = stmt_sequence();
        
        match("until");
        
        testChild = exp();
        
        writeFile("repeat statment found");
        n.setChild(bodyChild);
        n.setChild(testChild);
        return n;
    }
        
    TreeNode assign_stmt()
    {
        TreeNode n = new TreeNode();
        TreeNode expChild = new TreeNode();
        match("identefier");
        n.setName("assign \n" + syntaxx);
        match(":=");
        expChild = exp();
        writeFile("assign statment found");
        n.setChild(expChild);
        return n;
    }
    
    
    TreeNode read_stmt()
    {
        TreeNode n = new TreeNode();
        match("read");
        match("identefier");
        
        n.setName("read \n id = " + syntaxx);
        writeFile("read statment found");
        return n;
    }
    
    TreeNode write_stmt()
    {   TreeNode n = new TreeNode();
        TreeNode expChild = new TreeNode();
        match("write");
        n.setName("write");
        expChild = exp();
        writeFile ("write statment found");
        n.setChild(expChild);
        return n;
    }
    TreeNode exp()
    {
        TreeNode n = new TreeNode();
        LinkedList <TreeNode> simpexpChild= new LinkedList<TreeNode>(); 
        
        simpexpChild .addLast(simp_exp());
        
//        getToken();
        boolean flg = false;
        switch(token)
        {
            case "<":
                comparison_op();
                match("<"); 
                simpexpChild.addLast(simp_exp());
                n.setName("comp \n (<)");
                break;

            case "=":
                comparison_op();
                match("=");
               simpexpChild.addLast(simp_exp());
               n.setName("comp \n (=)");
                break;
        }
        writeFile ("expression found");
        Iterator i =  simpexpChild.iterator();
        while (i.hasNext())
        {
            n.setChild((TreeNode) i.next());
        }
        return n;
    }
    
    TreeNode simp_exp()
    {    
        TreeNode n = new TreeNode();
        LinkedList<TreeNode> termChild = new LinkedList<TreeNode>();
        termChild.addLast(term());
//        getToken();
        while ( add_op() )//token.equals("+" )|| token.equals("-"))
        {
            if(token.equals("+"))
            {
                match("+");
                n.setName(" op \n (+)");
            }
            
            else
            {
                match("-");
                n.setName("op \n (-)");
            }   
            
            termChild.addLast(term());
            
        }
        writeFile ("simple expression found");
        Iterator i = termChild.iterator();
        while(i.hasNext())
        {
            n.setChild((TreeNode) i.next());
        }
        return n;
    }
    TreeNode term()
    {
        TreeNode n = new TreeNode();
        LinkedList<TreeNode> factorChild = new LinkedList<TreeNode>();
        
       factorChild .addLast(factor());
//        getToken();
        while ( mul_op() )//token.equals("*") || token.equals("/"))
        {
            if(token.equals("*"))
            {
                match("*");
                n.setName("op \n (*)");
            }
            else 
            {
               match("/");
               n.setName("op \n /");
            }
            factorChild.addLast(factor());
        }
        writeFile ("term found");
        Iterator i = factorChild.iterator();
        while(i.hasNext())
        {
            n.setChild((TreeNode) i.next());
        }
        return n;

    }
    TreeNode factor ()
    {
        TreeNode n = new TreeNode();
//        TreeNode expChild = new TreeNode();
//        TreeNode number = new TreeNode("number");
//        TreeNode id = new TreeNode("id");
////        getToken();
        switch (token)
        {
            case"(":   
                match("(");
               n = exp();
                match(")");
//                n.setChild(expChild);
                
                break;
               
            case "number":
                match("number");
                n.setName("const \n "+ syntaxx);
                n.setTerminal();
//                n.setChild(number);
                break;
            default:
                match("identefier");
                n.setName("id \n" + syntaxx);
                n.setTerminal();
//                n.setChild(id);
                break;
        }
        
        writeFile ("factor found");
        return n;
    }
    void comparison_op()
    {
        switch(token)
        {
            case "<":
                writeFile("comaprsion operator found");
                break;
                
            case "=":
                writeFile("coparison operator found");
                break;
         }
    }
    boolean add_op()
    {
            if(token.equals("+"))
            {
                writeFile("add operator found");
                return true;
            }
            else if(token.equals("-")) 
            {
                writeFile("add operator found");
                return true;
            }
            else 
                return false;
    }
    boolean mul_op()
    {
         if(token.equals("*"))
            {
                writeFile("mul operator found");
                return true;
            }
         if(token.equals("/"))
            {
                writeFile("mul operator found");
                return true;
            }
         else
             return false;
         
    }
    
        
}

