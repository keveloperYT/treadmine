package com.keveloper.treadmill;

import net.minecraftforge.client.event.InputUpdateEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.time.Instant;

import java.net.*;
import java.io.*;

public class EventHandler {
    double prev_x = 0.0;
    double prev_z = 0.0;
    double prev_t = 0.0;
    int queue_size = 3;
    double[] rolling_avg = {0.0, 0.0, 0.0};
    int queue_pos = 0;
    int prev_avg = 0;

    Socket socket = null;
    DataOutputStream out = null;

    @SubscribeEvent
    public void playerEvent(InputUpdateEvent event) {
        if (event.getPlayer() != null) {
            int current_state = event.getPlayer().onGround ? 1 : 0;
            double curr_x = event.getPlayer().getPositionVector().x;
            double curr_z = event.getPlayer().getPositionVector().z;
            double curr_t = Instant.now().toEpochMilli();

            if (prev_t == 0.0) {
                try {
                    // Put host ip and port here.
                    socket = new Socket("192.168.1.420", 8003);
                    out = new DataOutputStream(socket.getOutputStream());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (current_state == 1) {

                if (prev_t != 0.0) {
                    double vel_x = (curr_x - prev_x) / ((curr_t - prev_t) / 1000.0);
                    double vel_z = (curr_z - prev_z) / ((curr_t - prev_t) / 1000.0);
                    double vel = Math.pow(Math.pow(vel_x, 2) + Math.pow(vel_z, 2), 0.5);
                    if (vel > 15.0) {
                        vel = 15.0;
                    }
                    else if (0.0 < vel && vel <= 5.4) {
                        vel *= 0.07*Math.pow(vel-5.4, 2)+1;
                    }
                    else if (5.4 < vel && vel <= 6.4) {
                        vel *= 1.3;
                    }
                    else if (6.4 < vel) {
                        vel *= 1.35;
                    }
                    rolling_avg[queue_pos] = vel;
                    queue_pos += 1;
                    queue_pos %= queue_size;

                    double sum = 0;
                    for (int ii = 0; ii < queue_size; ii++) {
                        sum += rolling_avg[ii];
                    }
                    sum /= queue_size;

                    if ((int) sum != prev_avg){
                        prev_avg = (int) (sum*100/15.0);
                        //System.out.println("duty factor: %" + prev_avg);
                        //System.out.println("vel: %" + sum);

                        try
                        {
                            out.writeByte(prev_avg);
                        }
                        catch(IOException i)
                        {
                            System.out.println(i);
                        }
                    }
                }
            }
            prev_x = curr_x;
            prev_z = curr_z;
            prev_t = curr_t;
        }
    }

}
