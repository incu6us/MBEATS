/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.response;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author vpryimak
 */
public class GetInfoXmlResponse {

    public List<String> infoXml(List<String[]> res, String audioPath, String audioUrl, String audioExt) {
        File audioFile = null;
        String[] respArrStart = new String[3];
        String[] respArrMiddle = new String[10];
        List<String> list = new ArrayList<>();

        String response = null;

        respArrStart[0] = "<response>"
                + "<id>" + res.get(0)[0] + "</id>"
                + "<values>";

        list.add(respArrStart[0]);

        for (int i = 0; i < res.size(); i++) {
            audioFile = new File(audioPath.replaceAll("/$", "") + "/" + res.get(i)[8] + "." + audioExt);
            respArrMiddle[0] = "<value>";
            respArrMiddle[1] = "<calldate>" + res.get(i)[1] + "</calldate>";
            respArrMiddle[2] = "<src>" + res.get(i)[2] + "</src>";
            respArrMiddle[3] = "<dst>" + res.get(i)[3] + "</dst>";
            respArrMiddle[4] = "<lastapp>" + res.get(i)[4] + "</lastapp>";
            respArrMiddle[5] = "<duration>" + res.get(i)[5] + "</duration>";
            respArrMiddle[6] = "<billsec>" + res.get(i)[6] + "</billsec>";
            respArrMiddle[7] = "<disposition>" + res.get(i)[7] + "</disposition>";
            if (audioFile.exists()) {
                respArrMiddle[8] = "<audio>" + audioUrl.replaceAll("/$", "") + "/" + res.get(i)[8] + "." + audioExt + "</audio>";
            } else {
                respArrMiddle[8] = "<audio>null</audio>";
            }
            respArrMiddle[9] = "</value>";

            list.add(respArrMiddle[0]);
            list.add(respArrMiddle[1]);
            list.add(respArrMiddle[2]);
            list.add(respArrMiddle[3]);
            list.add(respArrMiddle[4]);
            list.add(respArrMiddle[5]);
            list.add(respArrMiddle[6]);
            list.add(respArrMiddle[7]);
            list.add(respArrMiddle[8]);
            list.add(respArrMiddle[9]);
        }

        respArrStart[2] = "</values></response>";
        list.add(respArrStart[2]);

        return list;
    }

    public List<String> infoJson(List<String[]> res, String audioPath, String audioUrl, String audioExt) {
        File audioFile = null;
        String[] respArrStart = new String[2];
        String[] respArrMiddle = new String[10];
        List<String> list = new ArrayList<>();

        String response = null;

        respArrStart[0] = "{"
                + "\"response\": {"
                + "\"id\":\"" + res.get(0)[0] + "\","
                + "\"values\": {";

        list.add(respArrStart[0]);

        for (int i = 0; i < res.size(); i++) {
            audioFile = new File(audioPath.replaceAll("/$", "") + "/" + res.get(i)[8] + "." + audioExt);
            respArrMiddle[0] = "\"value-"+(i+1)+"\": {";
            respArrMiddle[1] = "\"calldate\":\"" + res.get(i)[1] + "\",";
            respArrMiddle[2] = "\"src\":\"" + res.get(i)[2] + "\",";
            respArrMiddle[3] = "\"dst\":\"" + res.get(i)[3] + "\",";
            respArrMiddle[4] = "\"lastapp\":\"" + res.get(i)[4] + "\",";
            respArrMiddle[5] = "\"duration\":\"" + res.get(i)[5] + "\",";
            respArrMiddle[6] = "\"billsec\":\"" + res.get(i)[6] + "\",";
            respArrMiddle[7] = "\"disposition\":\"" + res.get(i)[7] + "\",";
            if (audioFile.exists()) {
                respArrMiddle[8] = "\"audio\":\"" + audioUrl.replaceAll("/$", "") + "/" + res.get(i)[8] + "." + audioExt + "\"";
            } else {
                respArrMiddle[8] = "\"audio\":\"null\"";
            }
            if(i+1 == res.size()){
                respArrMiddle[9] = "}";
            }else{
                respArrMiddle[9] = "},";
            }
            
            list.add(respArrMiddle[0]);
            list.add(respArrMiddle[1]);
            list.add(respArrMiddle[2]);
            list.add(respArrMiddle[3]);
            list.add(respArrMiddle[4]);
            list.add(respArrMiddle[5]);
            list.add(respArrMiddle[6]);
            list.add(respArrMiddle[7]);
            list.add(respArrMiddle[8]);
            list.add(respArrMiddle[9]);
        }

        respArrStart[1] = "}"
                + "}"
                + "}";

        list.add(respArrStart[1]);

        return list;
    }

}
