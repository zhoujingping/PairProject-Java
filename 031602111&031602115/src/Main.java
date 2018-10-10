import java.io.*;

public class Main {
    public static void main(String[] args) throws IOException {
        String type = new String();
        File   fileIn = null;
        String fileOutAddress = new String();
        int  countNum = args.length;
        int  outNum = 10;
        int  arrayNum = 0;
        if(countNum > 0){
            for (int i = 0;i < countNum/2 ; i=i+2){
                if(args[i].equals("-i")){
                    fileIn  = new File(args[i+1]);
                }else if(args[i].equals("-w")){
                    if (type.equals("0")) {
                        type = "weight0";
                    }else if (type.equals("1")){
                        type = "weight0";
                    }
                }else if(args[i].equals("-m")){
                    arrayNum  =  Integer.parseInt(args[i+1]);

                }else if(args[i].equals("-n")){
                    outNum  =  Integer.parseInt(args[i+1]);

                }else if(args[i].equals("-o")){
                    fileOutAddress  =  args[i+1];
                }
            }

        }else{
            fileIn  = new File("./cvpr/result.txt");
            fileOutAddress  =  "./src/result.txt";
            type = "weight0";
//            type = "weight1";
            System.out.println("无自定义命令，使用默认参数:" + type);
        }

        //初始化工具类
        Count count = new Count(fileIn,fileOutAddress);
        switch (type){
            case "weight0":
                Count.getResult("weight0",arrayNum,outNum);
                break;

            case "weight1":
                Count.getResult("weight1",arrayNum,outNum);
                break;
            default:
                System.out.println("sorry,match fail");
                break;
        }
    }
}