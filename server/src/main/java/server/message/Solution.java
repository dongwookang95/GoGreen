package server.message;

import java.util.HashMap;
import java.util.Map;

public class Solution {


    int ip;

    int mac;

    Map<Integer, Integer> arptable;

    // constructor
    public Solution(int mac, int ip) {
        this.ip = ip;
        this.mac = mac;
        arptable = new HashMap<>();
        arptable.put(ip,mac);
    }

    // This function returns a spoofed ARP packet:
    //  The argument passed to this function is the IP address that you want to impersonate.
    public String spoofArp(int spoofIP) {
        String macString = Integer.toHexString(mac);
        String ipString = Integer.toHexString(spoofIP);
        String newStructure = "02" + macString + ipString + "000000" + ipString;
        return newStructure;
    }

    // Receive a message and provide the response. This function returns either a packet, or a status code.
    public String receiveArp(String message) {
        String opcode = message.substring(0,2);
        int opcodeInt = Integer.parseInt(opcode);
        String sMAC = message.substring(2,8);
        int sMACint = Integer.parseInt(sMAC, 16);
        String sIP = message.substring(8,12);
        int sIPint = Integer.parseInt(sIP, 16);
        String tMAC = message.substring(12, 18);
        int tMACint = Integer.parseInt(tMAC, 16);
        String tIP = message.substring(18, 22);
        int tIPint = Integer.parseInt(tIP, 16);
        String ignoreMessage = "IGNORE";
        String okMessage = "OK";
        String attackMessage = "ATTACK";
        String result = "";

        // 01ccccccbbbb000000aaaa
        if(opcode.equals("01")){

            if(sIPint != tIPint && checkContain(tIPint) == true){
                return "02" + Integer.toHexString(arptable.get(tIPint)) + tIP + sMAC + sIP;
            }
            if(sIP != tIP && checkContain(tIPint) == false){
                return ignoreMessage;
            }

            if(sIP.equals(tIP) && checkContain(tIPint) == true){
                return ignoreMessage;
            }
        }


        if(opcode.equals("02")) {

            if(tMACint != 0){
                return ignoreMessage;
            }
            // 02ddddddbbbb000000bbbb
            if(sIPint == tIPint && checkContain(tIPint) == true){
                if(arptable.get(tIPint) != sMACint){
                    result = attackMessage;
                }else{
                    arptable.put(sIPint, sMACint);
                    result =ignoreMessage;
                }
            } else{
                arptable.put(sIPint, sMACint);
                result = okMessage;
            }
        }


        return result;


    }

    public boolean checkContain(int a){
        if(arptable.containsKey(a) == true){
            return true;
        }else{
            return false;
        }
    }


}


