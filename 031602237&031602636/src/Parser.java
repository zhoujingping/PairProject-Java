public class Parser {


    private String i = "test.txt";
    private String o = "result.txt";
    private int w = 0;
    private int m = 1;
    private int n = 10;

    public Parser() {

    }

    public Parser(String args[]) {

        if (args.length % 2 == 0) {

            for (int j = 0; j < args.length; j += 2) {
                String type = args[j];
                String value = args[j + 1];

                switch (type) {
                    case "-i":
                        i = value;
                        break;
                    case "-o":
                        o = value;
                        break;
                    case "-w":
                        w = Integer.valueOf(value);
                        break;
                    case "-m":
                        m = Integer.valueOf(value);
                        break;
                    case "-n":
                        n = Integer.valueOf(value);
                        break;
                }

            }

        }

    }


    public String getI() {
        return i;
    }

    public String getO() {
        return o;
    }

    public int getW() {
        return w;
    }

    public int getM() {
        return m;
    }

    public int getN() {
        return n;
    }
}
