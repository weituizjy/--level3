import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Calculator {
    public static void main(String[] args) {
        Scanner sc=new Scanner(System.in);
        String str=null;
        String re="[0-9\\.+-/*()=]+";
        System.out.print("请输入算式：");
        str=sc.next();
        if(!str.matches(re)){
            System.out.print("输入算式不合法");
        }
        Calculator calculator=new Calculator();
        double results=calculator.calculator(str);
        System.out.print("="+results);
    }
    public double calculator(String expression){
        String exp="";
        //判断是否()存在
        if(expression.contains("(")&&expression.contains(")")){
            int a=expression.indexOf("(");//前括号的标记
            if(a>0){
                exp=expression.substring(0,a);//取前括号前的字符
            }
            expression=expression.substring(a+1);//取前括号后的字符
            int brackets=0;//括号里括号的数量
            for(int i=0;i<expression.length();i++){
                if("(".equals(expression.charAt(i)+"")){
                    brackets++;
                } else if(")".equals(expression.charAt(i)+"")&&brackets==0){
                        double num=calculator(expression.substring(0,i));
                        exp+=num;
                        if(i+1<expression.length()){
                            expression=expression.substring(i+1);
                            a=expression.indexOf("(");
                            if(a!=-1){
                                if(a>0){
                                    exp+=expression.substring(0,a);
                                }
                                expression=expression.substring(a+1);
                                i=-1;
                            }else{
                                exp+=expression;
                                break;
                            }
                        }
                }else if(")".equals(expression.charAt(i)+"")){
                        brackets--;
                }
            }
            //如果不存在括号
        }else {
            exp=expression;
        }
        //将字符串中数字和运算符拆分到集合
        List<String> list=new ArrayList<>();
        String x="";
        for(int i=0;i<exp.length();i++){
            x=exp.charAt(i)+"";
            if("-".equals(x)||"+".equals(x)||"*".equals(x)||"/".equals(x)){
                if("-".equals(x)&&list.size()==0&&i==0){
                    list.add("0");//如果负号在第一个，在集合中添加一个0
                }else if(i!=0){
                    list.add(exp.substring(0,i));//将运算符前的数字截取并加入数组
                }
                list.add(x);//将运算符加入数组
                exp=exp.substring(i+1);
                i=-1;//使得新的exp从0开始取x
            }else if(i+1>=exp.length()){
                list.add(exp);
                break;
            }
        }
        //计算
        double result=0.0;
        for(int i=0;i<2;i++){
            for(int j=0;j<list.size();j++){
                if(i==0){
                    if("*".equals(list.get(j))){
                        //乘法
                        result=Double.parseDouble(list.get(j-1))*Double.parseDouble(list.get(j+1));
                    }else if("/".equals(list.get(j))){
                        //除法
                        if("0".equals(list.get(j+1))){
                            System.out.print("除数为0，计算错误：");
                        }else{
                            result = Double.parseDouble(list.get(j - 1)) / Double.parseDouble(list.get(j + 1));
                        }
                    }
                    if("*".equals(list.get(j))||"/".equals(list.get(j))){
                        //移除已计算的，并把结果加入集合中
                        list.remove(j+1);
                        list.remove(j);
                        list.remove(j-1);
                        list.add(j-1,result+"");
                        j--;
                    }
                }else{
                    //加法
                    if("+".equals(list.get(j))){
                        result=Double.parseDouble(list.get(j-1))+Double.parseDouble(list.get(j+1));
                    //减法
                    }else if("-".equals(list.get(j))){
                        result=Double.parseDouble(list.get(j-1))-Double.parseDouble(list.get(j+1));
                    }
                    if("+".equals(list.get(j))||"-".equals(list.get(j))){
                        list.remove(j+1);
                        list.remove(j);
                        list.remove(j-1);
                        list.add(j-1,result+"");
                        j--;
                    }
                }
            }
        }
        result=(double)Math.round(result*1000)/1000;//三位小数
        return result;
    }
}
