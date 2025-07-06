package me.General.DataManegement;

import net.minestom.server.coordinate.Pos;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.UUID;

public class PlayerData implements Serializable {
    public UUID uuid;
    public String username;
    public Double x;
    public Double y;
    public Double z;
    public String[] permissions;

    //public Pos position;

    public PlayerData(UUID uuid, String username, Double x, Double y, Double z, String[] permissions) {
        this.uuid = uuid;
        this.username = username;
        this.x = x;
        this.y = y;
        this.z = z;
        this.permissions = permissions;
    }
}
