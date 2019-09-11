package server.message;

import java.util.HashMap;
import java.util.Map;

public class abc {
    int ip;
    int mac;
    Map<Integer, Integer> arptable;

    // constructor
    public abc(int mac, int ip) {
        this.ip = ip;
        this.mac = mac;
        this.arptable = new HashMap<>();
    }

    // This function returns a spoofed ARP packet:
    //  The argument passed to this function is the IP address that you want to impersonate.
    public String spoofArp(int spoofIP) {
        String strMac = toHex(mac);
        String strIp = toHex(spoofIP);
        return "02" + strMac + strIp + "000000" + strIp;
    }

    // Receive a message and provide the response. This function returns either a packet, or a status code.
    public String receiveArp(String message) {
        int opCode = getPart(message, 0, 2);
        int sendMac = getPart(message, 2, 8);
        int sendIp = getPart(message, 8, 12);
        int targMac = getPart(message, 12, 18);
        int targIp = getPart(message, 18, 22);

        if(targIp == ip){
            return "02" + toHex(mac) + toHex(ip) + toHex(sendMac) + toHex(sendIp);
        }

        if(opCode == 1 ||(targMac != 0 && targMac != mac) ){
            return "IGNORE";
        }

        if(targMac == mac && targIp != sendIp){
            return "ATTACK";
        }

        int retrievedMac = -1;
        if(arptable.get(sendIp) != null){
            retrievedMac = arptable.get(sendIp);
        }

        if(retrievedMac > -1 && retrievedMac != sendMac){
            return "ATTACK";
        }

        if(arptable.get(sendIp) != null){
            return "IGNORE";
        }

        arptable.put(sendIp, sendMac);

        return "OK";
    }

    private int getPart(String message, int from, int to) {
        String part = message.substring(from, to);
        return Integer.valueOf(part, 16);
    }

    private String toHex(int input){
        return Integer.toHexString(input);
    }


}
