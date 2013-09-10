


import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

public class Sexpression {
    protected static Object apply(Object function, Object x, Map aList, Map dList) {
        Vector output = new Vector();
        Vector CAR = new Vector();
        CAR.add("CAR");
        Vector CDR = new Vector();
        CDR.add("CDR");
        Vector CONS = new Vector();
        CONS.add("CONS");
        Vector ATOM = new Vector();
        ATOM.add("ATOM");
        Vector EQ = new Vector();
        EQ.add("EQ");
        Vector NULL = new Vector();
        NULL.add("NULL");
        Vector INT = new Vector();
        INT.add("INT");
        Vector PLUS = new Vector();
        PLUS.add("PLUS");
        Vector MINUS = new Vector();
        MINUS.add("MINUS");
        Vector TIMES = new Vector();
        TIMES.add("TIMES");
        Vector QUOTIENT = new Vector();
        QUOTIENT.add("QUOTIENT");
        Vector REMAINDER = new Vector();
        REMAINDER.add("REMAINDER");
        Vector LESS = new Vector();
        LESS.add("LESS");
        Vector GREATER = new Vector();
        GREATER.add("GREATER");
        if (!atom(function).equalsIgnoreCase("T")) {
            System.out.println("Error: Apply " + function);
            System.exit(-1);
            return null;
        }
        if (eq(function, CAR).equalsIgnoreCase("T")) {
            return car(car(x));
        } else if (eq(function, CDR).equalsIgnoreCase("T")) {
            return cdr(car(x));
        } else if (eq(function, CONS).equalsIgnoreCase("T")) {
            if (x.getClass().getName().equalsIgnoreCase("java.util.Vector")) {
                if (length(x) != 2) {
                    System.out.println("Error: Mismatch in number of parameters");
                    System.exit(-1);
                }
                return cons(car(x), car(cdr(x)));
            }

        } else if (eq(function, ATOM).equalsIgnoreCase("T")) {
            if (x.getClass().getName().equalsIgnoreCase("java.util.Vector")) {
                if (length(x) != 1) {
                    System.out.println("Error: Mismatch in number of parameters");
                    System.exit(-1);
                }
                output.removeAllElements();
                output.add(atom(car(x)));
                return output;
            }

        } else if (eq(function, EQ).equalsIgnoreCase("T")) {
            if (x.getClass().getName().equalsIgnoreCase("java.util.Vector")) {
                if (length(x) != 2) {
                    System.out.println("Error: Mismatch in number of parameters");
                    System.exit(-1);
                }
                output.removeAllElements();
                output.add(eq(car(x), car(cdr(x))));
                return output;
            }

        } else if (eq(function, NULL).equalsIgnoreCase("T")) {
            if (x.getClass().getName().equalsIgnoreCase("java.util.Vector")) {
                if (length(x) != 1) {
                    System.out.println("Error: Mismatch in number of parameters");
                    System.exit(-1);
                }
                output.removeAllElements();
                output.add(NULL(car(x)));
                return output;
            }

        } else if (eq(function, INT).equalsIgnoreCase("T")) {
            if (x.getClass().getName().equalsIgnoreCase("java.util.Vector")) {
                if (length(x) != 1) {
                    System.out.println("Error: Mismatch in number of parameters");
                    System.exit(-1);
                }
                output.removeAllElements();
                output.add(isInt(car(x)));
                return output;
            }

        } else if (eq(function, PLUS).equalsIgnoreCase("T")) {
            if (x.getClass().getName().equalsIgnoreCase("java.util.Vector")) {
                if (length(x) != 2) {
                    System.out.println("Error: Mismatch in number of parameters");
                    System.exit(-1);
                }
                output.removeAllElements();
                output.add(plus(car(x), car(cdr(x))));
                return output;
            }

        } else if (eq(function, MINUS).equalsIgnoreCase("T")) {
            if (x.getClass().getName().equalsIgnoreCase("java.util.Vector")) {
                if (length(x) != 2) {
                    System.out.println("Error: Mismatch in number of parameters");
                    System.exit(-1);
                }
                output.removeAllElements();
                output.add(minus(car(x), car(cdr(x))));
                return output;
            }

        } else if (eq(function, TIMES).equalsIgnoreCase("T")) {
            if (x.getClass().getName().equalsIgnoreCase("java.util.Vector")) {
                if (length(x) != 2) {
                    System.out.println("Error: Mismatch in number of parameters");
                    System.exit(-1);
                }
                output.removeAllElements();
                output.add(times(car(x), car(cdr(x))));
                return output;
            }

        } else if (eq(function, QUOTIENT).equalsIgnoreCase("T")) {
            if (x.getClass().getName().equalsIgnoreCase("java.util.Vector")) {
                if (length(x) != 2) {
                    System.out.println("Error: Mismatch in number of parameters");
                    System.exit(-1);
                }
                output.removeAllElements();
                output.add(quotient(car(x), car(cdr(x))));
                return output;
            }

        } else if (eq(function, REMAINDER).equalsIgnoreCase("T")) {
            if (x.getClass().getName().equalsIgnoreCase("java.util.Vector")) {
                if (length(x)!= 2) {
                    System.out.println("Error: Mismatch in number of parameters");
                    System.exit(-1);
                }
                output.removeAllElements();
                output.add(remainder(car(x), car(cdr(x))));
                return output;
            }

        } else if (eq(function, LESS).equalsIgnoreCase("T")) {
            if (x.getClass().getName().equalsIgnoreCase("java.util.Vector")) {
                if (length(x)!= 2) {
                    System.out.println("Error: Mismatch in number of parameters");
                    System.exit(-1);
                }
                output.removeAllElements();
                output.add(less(car(x), car(cdr(x))));
                return output;
            }

        } else if (eq(function, GREATER).equalsIgnoreCase("T")) {
            if (x.getClass().getName().equalsIgnoreCase("java.util.Vector")) {
                if (length(x) != 2) {
                    System.out.println("Error: Mismatch in number of parameters");
                    System.exit(-1);
                }
                output.removeAllElements();
                output.add(greater(car(x), car(cdr(x))));
                return output;
            }
        } else {
            if (function.getClass().getName().equalsIgnoreCase("java.util.Vector")) {
                if (!dList.containsKey(((Vector) function).elementAt(0))) {
                    System.out.println("Error: Function " + ((Vector) function).elementAt(0).toString() + " unbound");
                    System.exit(-1);
                }
            }

            Vector pList = new Vector();
            Vector functionBody = new Vector();
            if (function.getClass().getName().equalsIgnoreCase("java.util.Vector")) {
                pList = (Vector)car(dList.get(((Vector) function).elementAt(0)));
                functionBody = (Vector)cdr(dList.get(((Vector) function).elementAt(0)));
            }

            if (x.getClass().getName().equalsIgnoreCase("java.util.Vector")) {
                if (length(pList) != length(x)) {
                    System.out.println("Error: Parameter mismatch");
                    System.exit(-1);
                }
            }

            Map aListNew = new HashMap();
            if (length(pList) == 0) {
                aListNew.putAll(aList);
            } else {
                aListNew = addPairs(pList, x, aList);
            }
            return eval(functionBody, aListNew, dList);
        }
        return null;
    }

    protected static Object eval(Object sexp, Map aList, Map dList) {
        Vector TRUE = new Vector();
        TRUE.add("T");
        Vector FALSE = new Vector();
        FALSE.add("NIL");
        Vector QUOTE = new Vector();
        QUOTE.add("QUOTE");
        Vector COND = new Vector();
        COND.add("COND");
        Vector DEFUN = new Vector();
        DEFUN.add("DEFUN");
        String name;
        Vector pList = new Vector();
        Vector fnBody = new Vector();
        Vector temp = new Vector();
        Vector output = new Vector();
        if (atom(sexp).equalsIgnoreCase("T")) {
        	Object v = new Object();
        	v = ((Vector) sexp).elementAt(0);
        	if (isInt(sexp)) {
                return sexp;
            } else if (eq(sexp, TRUE).equalsIgnoreCase("T")) {
                return TRUE;
            } else if (eq(sexp, FALSE).equalsIgnoreCase("T")) {
                return FALSE;
            } else if (aList.containsKey(((Vector) sexp).elementAt(0))) {
                return aList.get(((Vector) sexp).elementAt(0));
            } else {
                System.out.println(sexp + ":unbound");
            }
        } else if ((atom(car(sexp))).equals("T")) {
            if (eq(car(sexp), QUOTE).equalsIgnoreCase("T")) {
                return car(cdr(sexp));
            } else if (eq(car(sexp), COND).equalsIgnoreCase("T")) {
                return evcon(cdr(sexp), aList, dList);
            } else if (eq(car(sexp), DEFUN).equalsIgnoreCase("T")) {
                temp = (Vector) car(cdr(sexp));
                name = temp.elementAt(0).toString();
                pList = (Vector) car(cdr(cdr(sexp)));
                fnBody = (Vector) car(cdr(cdr(cdr(sexp))));
                dList.put(name, cons(pList, fnBody));
                return name;
            } else {
                return apply(car(sexp), evlis(cdr(sexp), aList, dList), aList, dList);
            }
        }
        System.out.println("Error: Invalid s-exp: " + sexp);
        System.exit(-1);
        return null;
    }

    protected static Object evcon(Object binaryExp, Map aList, Map dList) {
    	Vector TRUE = new Vector();
    	TRUE.add("T");
        if ((NULL(binaryExp)).equals("T")) {
            System.out.println("Error: Invalid/empty binary expression for COND");
            System.exit(-1);
            return null;
        } else if ((eval(car(car(binaryExp)), aList, dList)).equals(TRUE)) {
            return eval(car(cdr(car(binaryExp))), aList, dList);
        } else {
            return evcon(cdr(binaryExp), aList, dList);
        }
    }

    protected static Object evlis(Object list, Map aList, Map dList) {
        Vector output = new Vector();
        Object dummy1 = new Object();
        dummy1 = NULL(list);
        if (NULL(list).equals("T")) {
            return "NIL";
        } else {
            return cons(eval(car(list), aList, dList), evlis(cdr(list), aList, dList));
        }
    }

    protected static Map addPairs(Object pList, Object argument, Map aList) {
        Map alist = new HashMap();
        alist.putAll(aList);
        Object value = new Object();
        String key;
        Vector NIL = new Vector();
        NIL.add("NIL");
        while (true) {
            key = ((Vector) car(pList)).elementAt(0).toString();
            value = ((Vector) car(argument));
            alist.put(key, value);
            if ((cdr(pList).equals(NIL))) {
                break;
            } else {
                pList = (Vector) cdr(pList);
                argument = (Vector) cdr(argument);
            }
        }
        return alist;
    }



    protected static int length(Object sexp) {
        int length = 0;
        Object sexpr = new Object();
        if (sexp.getClass().getName().equalsIgnoreCase("java.util.Vector")) {
            sexpr = ((Vector)sexp).clone();
            while (sexpr.getClass().getName().equalsIgnoreCase("java.util.Vector") && ((Vector) sexp).size() > 1) {
                sexpr = ((Vector)sexpr).elementAt(1);
                length++;
            }
        }
        return length;
    }

   


    /*Lisp Primitive functions*/

    protected static Object car(Object sexp) {
        Vector sexpr = new Vector();
        if (atom(sexp).equals("T")) {
            System.out.println("Error: car cannot be applied on an atomic expression" + sexpr);
            System.exit(-1);
        }
        if (sexp.getClass().getName().equals("java.util.Vector")) {
            sexpr = (Vector) sexp;
            Vector carElem = new Vector();
            Object n2 = new Object();
            n2 = sexpr.elementAt(0);
            String classname = ((Vector)sexp).elementAt(0).getClass().getName();
            if (!(((Vector)sexp).elementAt(0).getClass().getName().equalsIgnoreCase("java.util.Vector"))) {
                carElem.add(((Vector)sexp).elementAt(0));
                return carElem;
            }
            return ((Vector)sexp).elementAt(0);
        }
        return null;
    }

    protected static Object cdr(Object sexp) {
        Vector localSexp = new Vector();
        Vector cdrElem = new Vector();
        Vector sexpr = new Vector();
        if (atom(sexp).equals("T")) {
            System.out.println("Error: cdr cannot be applied on an atomic expression" + sexpr.toString());
            System.exit(-1);
        }
        if (sexp.getClass().getName().equals("java.util.Vector")) {
            localSexp = (Vector) sexp;

            sexpr = (Vector) localSexp.clone();
            Object n = new Object();
            n = sexpr.elementAt(1);
            String className = sexpr.elementAt(1).getClass().getName();
            if (!sexpr.elementAt(1).getClass().getName().equalsIgnoreCase("java.util.Vector")) {
                cdrElem.add(sexpr.elementAt(1));
                return cdrElem;
            }
            return sexpr.elementAt(1);

        }
        return null;

    }

    protected static String atom(Object sexp) {
        if (sexp.getClass().getName().equals("java.util.Vector")) {
            Vector sexpr = new Vector();
            sexpr = (Vector) sexp;
            if (sexpr.size() == 1) {
                return "T";
            }
            return "NIL";
        }
        return "T";
    }

    protected static Object cons(Object sexp1, Object sexp2) {
        Vector sexp = new Vector();
        sexp.add(sexp1);
        sexp.add(sexp2);
        return (Vector)sexp;
    }

    protected static String eq(Object sexp1, Object sexp2) {
        if (!((atom(sexp1)).equalsIgnoreCase("T") && (atom(sexp2)).equalsIgnoreCase("T"))) {
            System.out.println("EQ cannot be applied on non-atoms");
            System.exit(-1);
        }
        if (sexp1.equals(sexp2)) {
            return "T";
        }
        return "NIL";
    }

    protected static String NULL(Object sexp) {
        Vector NIL = new Vector();
        NIL.add("NIL");
        if ((atom(sexp)).equalsIgnoreCase("T") && (eq(sexp, NIL)).equalsIgnoreCase("T")) {
            return "T";
        }
        return "NIL";
    }

    protected static boolean isInt(Object sexp) {
        Vector localSexp = new Vector();
        Vector sexpr = new Vector();
        if (sexp.getClass().getName().equalsIgnoreCase("java.util.Vector")) {
            localSexp = (Vector) sexp;
            sexpr = (Vector) localSexp.clone();
            String firstString = sexpr.elementAt(0).toString();
            char firstChar = firstString.charAt(0);
            String temp;
            if (firstChar == '-') {
                temp = firstString.substring(1);
            } else if (firstChar == '+') {
                temp = firstString.substring(1);
            } else {
                temp = firstString;
            }
            if (temp.getClass().getName().equalsIgnoreCase("java.util.Vector")) {
                return false;
            }
            return isInteger(temp);
        }
        return isInteger(sexp.toString());
    }

    protected static boolean isInteger(String input) {
        try {
            Integer.parseInt(input.toString());
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    protected static String plus(Object sexp1, Object sexp2) {
        if (!(atom(sexp1).equalsIgnoreCase("T")) && (atom(sexp2).equalsIgnoreCase("T"))) {
            System.out.println("Error: Plus cannot be applied to non atoms");
            System.exit(-1);
        }
        if (!(isInt(sexp1) && isInt(sexp2))) {
            System.out.println("Error: Plus cannot be applied to non integers");
            System.exit(-1);
        }
        Integer result = new Integer(0);
        result = Integer.parseInt(((Vector) sexp1).elementAt(0).toString().replace("+", "")) + Integer.parseInt(((Vector) sexp2).elementAt(0).toString().replace("+",""));
        return result.toString();
    }

    protected static String minus(Object sexp1, Object sexp2) {
        if (!(atom(sexp1).equalsIgnoreCase("T")) && (atom(sexp2).equalsIgnoreCase("T"))) {
            System.out.println("Error: Minus cannot be applied to non atoms");
            System.exit(-1);
        }
        if (!(isInt(sexp1) && isInt(sexp2))) {
            System.out.println("Error: Minus cannot be applied to non integers");
            System.exit(-1);
        }
        Integer result = new Integer(0);
        result = Integer.parseInt(((Vector) sexp1).elementAt(0).toString().replace("+", "")) - Integer.parseInt(((Vector) sexp2).elementAt(0).toString().replace("+",""));
        return result.toString();
    }

    protected static String times(Object sexp1, Object sexp2) {
        if (!(atom(sexp1).equalsIgnoreCase("T")) && (atom(sexp2).equalsIgnoreCase("T"))) {
            System.out.println("Error: Times cannot be applied to non atoms");
            System.exit(-1);
        }
        if (!(isInt(sexp1) && isInt(sexp2))) {
            System.out.println("Error: Times cannot be applied to non integers");
            System.exit(-1);
        }
        Integer result = new Integer(0);
        result = Integer.parseInt(((Vector) sexp1).elementAt(0).toString().replace("+", "")) * Integer.parseInt(((Vector) sexp2).elementAt(0).toString().replace("+",""));
        return result.toString();
    }

    protected static String quotient(Object sexp1, Object sexp2) {
        if (!(atom(sexp1).equalsIgnoreCase("T")) && (atom(sexp2).equalsIgnoreCase("T"))) {
            System.out.println("Error: Quotient cannot be applied to non atoms");
            System.exit(-1);
        }
        if (!(isInt(sexp1) && isInt(sexp2))) {
            System.out.println("Error: Quotient cannot be applied to non integers");
            System.exit(-1);
        }
        Integer result = new Integer(0);
        result = Integer.parseInt(((Vector) sexp1).elementAt(0).toString().replace("+", "")) / Integer.parseInt(((Vector) sexp2).elementAt(0).toString().replace("+",""));
        return result.toString();
    }

    protected static String remainder(Object sexp1, Object sexp2) {
        if (!(atom(sexp1).equalsIgnoreCase("T")) && (atom(sexp2).equalsIgnoreCase("T"))) {
            System.out.println("Error: Minus cannot be applied to non atoms");
            System.exit(-1);
        }
        if (!(isInt(sexp1) && isInt(sexp2))) {
            System.out.println("Error: Minus cannot be applied to non integers");
            System.exit(-1);
        }
        Integer result = new Integer(0);
        result = Integer.parseInt(((Vector) sexp1).elementAt(0).toString().replace("+", "")) % Integer.parseInt(((Vector) sexp2).elementAt(0).toString().replace("+",""));
        return result.toString();
    }

    protected static String less(Object sexp1, Object sexp2) {
        if (!(atom(sexp1).equalsIgnoreCase("T")) && (atom(sexp2).equalsIgnoreCase("T"))) {
            System.out.println("Error: Minus cannot be applied to non atoms");
            System.exit(-1);
        }
        if (!(isInt(sexp1) && isInt(sexp2))) {
            System.out.println("Error: Minus cannot be applied to non integers");
            System.exit(-1);
        }
        int input1;
        int input2;
        input1 = Integer.parseInt(((Vector) sexp1).elementAt(0).toString().replace("+", ""));
        input2 = Integer.parseInt(((Vector) sexp2).elementAt(0).toString().replace("+", ""));
        if (input1 < input2) {
            return "T";
        }
        return "NIL";
    }

    protected static String greater(Object sexp1, Object sexp2) {
        if (!(atom(sexp1).equalsIgnoreCase("T")) && (atom(sexp2).equalsIgnoreCase("T"))) {
            System.out.println("Error: Minus cannot be applied to non atoms");
            System.exit(-1);
        }
        if (!(isInt(sexp1) && isInt(sexp2))) {
            System.out.println("Error: Minus cannot be applied to non integers");
            System.exit(-1);
        }
        int input1;
        int input2;
        input1 = Integer.parseInt(((Vector) sexp1).elementAt(0).toString().replace("+", ""));
        input2 = Integer.parseInt(((Vector) sexp2).elementAt(0).toString().replace("+", ""));
        if (input1 > input2) {
            return "T";
        }
        return "NIL";
    }


}
