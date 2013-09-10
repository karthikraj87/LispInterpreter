

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.Vector;



public class Interpreter extends Sexpression{

    public String inputString;
    public static Map aList;
    public static Map dList;


    public static String stringNormalize(String inputString) {
        inputString = inputString.toUpperCase();//transform all characters to uppercase
        inputString = removeDelim(inputString);//Remove all delimeters such as blank spaces, tab, newline etc
        //inputString = inputString.replaceAll("\\+", "");// numbers are positive by default if not preceded by '-'
        inputString = inputString.replace(" . ", "."); // remove extra whitespaces besides the dot
        inputString = inputString.replace(". ", ".");
        inputString = inputString.replace(" .", ".");
        inputString = inputString.replace(" )", ")");// remove extra whitespaces besides the parentheses
        inputString = inputString.replace("( ", "(");
        inputString = inputString.replace("()", "NIL");//() => NIL
        return inputString.trim();
    }

    public static String removeDelim(String inputString) {
        StringTokenizer statement = new StringTokenizer(inputString);
        String temp = "";
        while (statement.hasMoreElements()) {
            temp = temp + " " + statement.nextElement();
        }
        return temp;
    }

    public static Vector getNextToken(String normalizedString) {
        Vector nextToken = new Vector();
        int numPar;
        String tokens = new String();
        tokens = normalizedString.trim();
        if (tokens.startsWith("(")) {
            numPar = 0;
            for (int i = 0; i < tokens.length(); i++) {
                if (tokens.charAt(i) == '(') {
                    numPar++;
                } else if (tokens.charAt(i) == ')') {
                    numPar--;
                }
                if (numPar == 0) {
                    nextToken.add(0, tokens.substring(0, i + 1));
                    nextToken.add(1, tokens.substring(i + 1));
                    nextToken.add(2, "sexp");
                    return nextToken;
                }
            }
            if (numPar > 0) {
                nextToken.add(0, "");
                nextToken.add(1, tokens);
                nextToken.add(2, "error");
                return nextToken;
            }
        }
        if (tokens.startsWith(".")) {
            nextToken.add(0, ".");
            nextToken.add(1, tokens.substring(1));
            nextToken.add(2, "dot");
            return nextToken;
        }
        for (int j = 0; j < tokens.length(); j++) {
            if (tokens.charAt(j) == ' ' || tokens.charAt(j) == '.' || tokens.charAt(j) == '(') {
                nextToken.add(0, tokens.substring(0, j));
                nextToken.add(1, tokens.substring(j));
                nextToken.add(2, "atom");
                return nextToken;
            }
        }
        nextToken.add(0, tokens);
        nextToken.add(1, "");
        nextToken.add(2, "atom");
        return nextToken;
    }

    public static boolean checkParentheses(String inputString) {
        int numPar = 0;
        for (int i = 0; i < inputString.length(); i++) {
            if (inputString.charAt(i) == '(') {
                numPar++;
            } else if (inputString.charAt(i) == ')') {
                numPar--;
            }
            if (numPar < 0) {
                return false;
            }
        }
        if (numPar != 0) {
            return false;
        }
        return true;
    }

    public static String removeParentheses(String inputString) {
        inputString = inputString.trim();
        if (inputString.startsWith("(") && inputString.endsWith(")")) {
            inputString = inputString.substring(1, inputString.length() - 1);
        }
        return inputString;
    }

    
    public static String sexpToString(Object sexp, boolean flag) {
        String result;
        if (!sexp.getClass().getName().equalsIgnoreCase("java.util.Vector")) {
            if (flag) {
                return ")";
            } else {
                return sexp.toString();
            }
        }
        if (((Vector) sexp).size() == 1) {
            return ((Vector) sexp).elementAt(0).toString();
        }
        Object left = new Object();
        Object right = new Object();
        left = ((Vector) sexp).elementAt(0);
        right =((Vector) sexp).elementAt(1);

        if (isList( sexp)) {
            if (flag) {
                result = sexpToString(left, false) + " " + sexpToString(right, true);
            } else {
                result = "(" + sexpToString(left, false) + " " + sexpToString(right, true);
            }
        } else {
            result = "(" + sexpToString(left, false) + " . " + sexpToString(right, false) + ")";
        }
        result = result.replace(" )", ")");
        return result;
    }

    

    public static Vector convertStrToSexp(String expression) {
        String local = expression;
        Vector tokens = new Vector();
        Vector output = new Vector();
        boolean flag = false;
        int numDots;
        while (local.length() != 0) {
            Vector token1 = new Vector();
            Vector nextToken = new Vector();
            nextToken = getNextToken(local);
            Object token = new Object();
            token = nextToken.elementAt(0);
            String remaining = (String) nextToken.elementAt(1);
            String type = (String) nextToken.elementAt(2);
            if (type.equalsIgnoreCase("sexp"))//Recursively convert s-exp into a s-exp binary tree
            {
                token = removeParentheses(token.toString());
                token = convertStrToSexp(token.toString());
            }
            local = remaining;
            tokens.add(token);
        }
        if (tokens.elementAt(0).toString().equals(".")) {
            System.out.println("Error: s-exp cannot begin with a '.'");
            System.exit(-1);
        }
        numDots = 0;
        for (int i = 0; i < tokens.size(); i++)//Compute the number of dots
        {
            if (tokens.elementAt(i).toString().equals(".")) {
                numDots++;
            }
        }
        if (numDots > 1) {
            System.out.println("Error: Invalid dot notation");
            System.exit(-1);
        }
        if (tokens.size() == 1) {
            output.add(tokens.elementAt(0));
            output.add("NIL");
            return output;
        }
        if (tokens.elementAt(1).toString().equals(".")) {
            return dotToTree(tokens);
        }
        if (numDots > 0) {
            System.out.println("Error: Invalid list notation " + expression);
            System.exit(-1);
        }

        return listToTree(tokens);
    }

     public static Vector listToTree(Vector tokens) {
        Vector output = new Vector();
        if (tokens.size() == 1) {
            output.add(tokens.elementAt(0));
            output.add("NIL");
            return output;
        }
        output.add(tokens.elementAt(0));
        tokens.removeElementAt(0);
        output.add(listToTree(tokens));
        return output;
    }

    public static Vector dotToTree(Vector tokens) {
        tokens.removeElementAt(1);
        Vector tree = new Vector();
        if (tokens.size() != 2) {
            System.out.println("Error: Invalid dot notation, Dot notation should contain only two elements");
            System.exit(-1);
        }
        tree.add(tokens.elementAt(0));
        tree.add(tokens.elementAt(1));
        return tree;
    }
        public static boolean isList(Object sexp) {
        Object sexpr = new Object();
        if (!sexp.getClass().getName().equalsIgnoreCase("java.util.Vector")) {
            return false;
        }
        if (((Vector)sexp).size() == 1) {
            return false;
        }
        if(sexp.getClass().getName().equalsIgnoreCase("java.util.Vector")){
            sexpr = ((Vector)sexp).clone();
            while (sexpr.getClass().getName().equalsIgnoreCase("java.util.Vector") && ((Vector)sexpr).size() > 1) {
                sexpr = ((Vector)sexpr).elementAt(1);
            }
            if (sexpr.equals("NIL")) {
                return true;
            }
        }
        return false;
    }
    
    public static void main(String[] args) throws Exception {
        
        try {
            Interpreter interpreterObj = new Interpreter();
            Sexpression lispExpression = new Sexpression();
            aList = new HashMap();
            dList = new HashMap();
            InputStreamReader isr = new InputStreamReader(System.in);
            BufferedReader br = new BufferedReader(isr);
            System.out.println("Enter the name of the input file:");
            String fileName = br.readLine();
            BufferedReader in = new BufferedReader(new FileReader(fileName));
            File inputFile = new File(fileName);
            char[] buf = new char[1024];
            int numRead = 0;
            StringBuilder fileData = new StringBuilder((int) inputFile.length());
            while ((numRead = in.read(buf)) != -1) {
                fileData.append(buf, 0, numRead);
            }
            in.close();
            interpreterObj.inputString = fileData.toString();
            System.out.println("****************************************************************************");
            String normalizedString = new String();
            normalizedString = interpreterObj.stringNormalize(interpreterObj.inputString);
            Vector tokenVector = new Vector();
            String token = new String();
            String remain_string = new String();
            String type = new String();
            while (normalizedString.length() > 0) {
                tokenVector = interpreterObj.getNextToken(normalizedString);
                token = (String) tokenVector.elementAt(0).toString();
                remain_string = (String) tokenVector.elementAt(1).toString();
                type = (String) tokenVector.elementAt(2).toString();
                System.out.println(token);
                if (!(interpreterObj.checkParentheses(token))) {
                    System.out.println("Parantheses mismatch");
                    break;
                }
                if (type.equalsIgnoreCase("sexp")) {
                    Vector sexp = new Vector();
                    sexp = convertStrToSexp(removeParentheses(token));
                    Object evalop = new Object();
                    evalop = lispExpression.eval(sexp, aList, dList);
                    String op = sexpToString(evalop, false);
                    System.out.println(op);
                } else if (type.equalsIgnoreCase("atom")) {
                    Vector temp = new Vector();
                    temp.add(token);
                    if (lispExpression.isInt(temp) || token.equalsIgnoreCase("T") || token.equalsIgnoreCase("NIL")) {
                        System.out.println("Input sexp: " + token);
                        System.out.println("Output sexp: " + token);
                    }
                } else {
                    System.out.println("Error: Invalid sexp " + remain_string);
                    break;
                }
                normalizedString = remain_string;
            }
            
        } catch (IOException e) {
            System.out.println("Input file not found or could not be read");
        } catch (NumberFormatException e) {
            System.out.println("Invalid Number or operation not supported "+e.getMessage());
        } catch (Exception e) {
            System.out.println("Error executing command "+e.getMessage());
        } finally{
            System.out.println("***********************LISP INTERPRETER ENDS ******************************");
        }
    }
}
