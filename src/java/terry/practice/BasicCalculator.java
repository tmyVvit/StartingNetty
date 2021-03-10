package terry.practice;



import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class BasicCalculator {

    public static void main(String[] args) {
        BasicCalculator basicCalculator = new BasicCalculator();
        System.out.println(basicCalculator.calculate("(1+(4+5+2)-3)+(6+8)"));
        System.out.println(basicCalculator.calculate("- (3 + (4 + 5))"));
    }

    public static void print(List<String> list) {
        for (String str : list) {
            System.out.print(str + " ");
        }
        System.out.println();
    }

    /**
     * 输入的s是一个中缀表达式
     * 可以将s转换成一个后缀表达式，然后使用栈计算结果
     * @param s 一个中缀表达式
     * @return 运算结果
     */
    public int calculate(String s) {
        List<String> list = convert(s);
        print(list);
        Stack<Integer> stack = new Stack<>();
        for (String op : list) {
            if (isOperator(op)) {
                int b = stack.pop();
                int a = stack.pop();
                int res = operate(a, b, op);
                stack.push(res);
            } else {
                stack.push(Integer.parseInt(op));
            }
        }
        return stack.pop();
    }

    /**
     *
     * @param a 第一个操作数
     * @param b 第二个操作数
     * @param op 运算符 + - * /
     * @return 返回运算结果
     */

    private int operate(int a, int b, String op) {
        if ("+".equals(op)) {
            return a + b;
        } else if ("-".equals(op)) {
            return a - b;
        } else if ("*".equals(op)) {
            return a * b;
        } else if ("/".equals(op)) {
            return a / b;
        }
        return 0;
    }


    // 将一个中缀表达式转换成后缀表达式
    // 首先初始化两个栈 stack1, stack2
    // 然后从左到右遍历 s，
    // 1。 如果遇到数字，则入栈stack1
    // 2。 如果遇到( 则入栈 stack2
    // 3。 如果遇到 ）则出栈stack2 并将结果入栈 stack1 知道 stack2 栈顶是 （ ，然后将 （ 出栈
    // 4。 如果遇到 运算符：
    //          如果stack2 为空或者栈顶为 （ 则入栈 stack2
    //          否则比较 运算符与stack2栈顶的运算符的优先级
    //              如果优先级 > 栈顶 ：则压入stack2
    //              如果优先级 <= 栈顶： 则将stack2栈顶运算符出栈 将其入栈 stack1， 然后重复步骤4
    // 知道字符串s遍历结束或者遇到 = ，则将stack2全部运算符出栈，并压入stack1
    public List<String> convert(String s) {
        List<String> list = convertToList(s);
        Stack<String> stack1 = new Stack<>();
        Stack<String> stack2 = new Stack<>();

        for (String op : list) {
            if (" ".equals(op) || "".equals(op)) continue;
            if ("(".equals(op)) {
                stack2.push(op);
            } else if (")".equals(op)) {
                String pop;
                while (!stack2.isEmpty() && !(pop = stack2.pop()).equals("(")) {
                    stack1.push(pop);
                }
            } else if (isOperator(op)) {
                while (true) {
                    if (stack2.isEmpty() || "(".equals(stack2.peek()) || calculateOperatorPriority(op, stack2.peek()) == 1) {
                        stack2.push(op);
                        break;
                    } else {
                        stack1.push(stack2.pop());
                    }
                }

            } else {
                stack1.push(op);
            }
        }
        while (!stack1.isEmpty()) {
            stack2.push(stack1.pop());
        }
        List<String> result = new ArrayList<>();
        while (!stack2.isEmpty()) {
            result.add(stack2.pop());
        }
        return result;
    }

    private List<String> convertToList(String s) {
        if (s.charAt(0) == '-') s = "0" + s;
        char[] arr = s.replaceAll(" ", "").toCharArray();
        List<String> list = new ArrayList<>();
        StringBuilder number = new StringBuilder();
        for (char ch : arr) {
            if (isNumber(ch)) {
                number.append(ch);
            } else {
                list.add(number.toString());
                list.add(String.valueOf(ch));
                number = new StringBuilder();

            }
        }
        list.add(number.toString());
        return list;
    }

    private int calculateOperatorPriority(String op1, String op2) {
        if ("*".equals(op1) || "/".equals(op1)) {
            if ("*".equals(op2) || "/".equals(op2)) return 0;
            return 1;
        }
        if ("*".equals(op2) || "/".equals(op2)) return -1;
        return 0;
    }

    private boolean isOperator(String op) {
        return "+".equals(op) || "-".equals(op) || "*".equals(op) || "/".equals(op);
    }

    private boolean isNumber(char ch) {
        return ch >= '0' && ch <= '9';
    }
}
