/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package api;

/**
 *
 * @author marc.vila.gomez
 */
public class VideoAPI {
    
    public VideoAPI(){
        try {
            int i = 3;
            int j = 4;
            int result = add(i, j);
            System.out.println("Result = " + result);
        } catch (Exception ex) {
            System.out.println("Exception: " + ex);
        }
    }
    
    private static int add(int i, int j) {
        org.me.server.ServerApplication_Service service = new org.me.server.ServerApplication_Service();
        org.me.server.ServerApplication port = service.getServerApplicationPort();
        return port.add(i, j);
    }
}
