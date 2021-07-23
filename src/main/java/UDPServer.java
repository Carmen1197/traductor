/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author milu
 */

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketAddress;
import java.util.HashMap;
import java.util.Map;


public class UDPServer {


    public static void main(String[] args) throws Exception {
        new UDPServer().start();
    }

    // Definir el número de puerto udp
    int PORT = 30000;
    // Cada datagrama es de hasta 4K
    int DATA_LEN = 4096;
    // Matriz de bytes para recibir datos de la red
    byte[] buff_in = new byte[DATA_LEN];
    // Cree un objeto DatagramPacket listo para aceptar datos con la matriz de bytes especificada
     DatagramPacket packet_in = new DatagramPacket(buff_in, buff_in.length);

    // Definir un objeto DatagramPacket para enviar
    DatagramPacket packet_out;

    public void start() throws Exception {
        try(DatagramSocket socket = new DatagramSocket(PORT)){
            String key = null;  // Palabras inglesas ingresadas por el cliente
            String value=null;  // Contenido chino traducido por el servidor
            SocketAddress address=null; // Obtener el objeto del cliente y escribir datos a la otra parte a través del objeto, de lo contrario no hay destino
            byte[] reviceData=null; // Datos devueltos al cliente
            System.out.println("El servidor inglés-español y Español-Inglés ha comenzado ...");
            while (true){
                // Lee los datos en el socket y luego encapsula los datos en packet_in
                socket.receive(packet_in);
                // Obtener los datos ingresados ​​por el cliente
                buff_in=packet_in.getData();
                // Para convertir la matriz de bytes en una cadena, debe eliminar los espacios finales
                key=new String(buff_in,0,buff_in.length).trim();

                // Obtenga el valor de acuerdo con la clave del mapa
      
                if (key!=null && "Cat".equals(key)){
                    value="gato";
                }
                
                if(key!=null && "Gato".equals(key)){
                 value= "Cat";
                }
                 
                if (key!=null && "Dog".equals(key)){
                    value="perro";   
                }
                
                if(key!=null&& "Perro".equals(key)) {
                    value= "Dog";
                }
                
               
                address=packet_in.getSocketAddress();
                // verifica las condiciones de salida
                if("down".equals(key)){
                    System.out.println("La entrada del cliente es:"+key);
                    reviceData="El servidor está inactivo, por favor intente nuevamente".getBytes();
                    packet_out=new DatagramPacket(reviceData,reviceData.length,address);
                    socket.send(packet_out);
                    break;
                }else {
                    System.out.println("La entrada del cliente es:"+key+", el resultado traducido es"+value);
                    // Construye los datos enviados por el servidor al cliente
                    reviceData=("La palabra que ingresó traducida es:"+value).getBytes();
                    // Construye packet_out para enviar datos
                    packet_out=new DatagramPacket(reviceData,reviceData.length,address);
                    socket.send(packet_out);
                    value = null;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("===== Servidor apagado =====");
        }
    }
//
    /* Defina el conjunto de mapas para almacenar las palabras que deben traducirse.
    private static Map<String, String> maps = new HashMap<>();
    // Agregar datos al mapa
    static {
        maps.put("dog", "perro");
        maps.put("cat", "Gato");
        maps.put("fish", "pez");
        maps.put("bird", "pájaro");
        maps.put("pig", "cerdo");
    }*/
}