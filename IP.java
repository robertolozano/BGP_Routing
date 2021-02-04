public class IP {
    int ip_1;
    int ip_2;
    int ip_3;
    int ip_4;

    String bit32;

    public IP(int ip1, int ip2, int ip3, int ip4, String bits){
        this.ip_1 = ip1;
        this.ip_2 = ip2;
        this.ip_3 = ip3;
        this.ip_4 = ip4;
        this.bit32 = bits;
    }

    public void display(){
        System.out.println(ip_1 +"."+ ip_2 +"."+ ip_3 +"."+ ip_4);
    }

    public void output(){
        System.out.print(ip_1 +"."+ ip_2 +"."+ ip_3 +"."+ ip_4);
    }
}