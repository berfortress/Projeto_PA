/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webcrawler;

import java.util.Objects;

/**
 *
 * @author berna
 */
public class Hyperlinks {
    private static int id = 0;
    private final double distance;
    private String linkName;

    public Hyperlinks(String linkName) {
        this.id ++;
        this.linkName = linkName;
        this.distance = 0;
    }

    public String getLinkName() {
        return linkName;
    }

    public void setLinkName(String linkName) {
        this.linkName = linkName;
    }

    public double getDistance() {
        return distance;
    }

    public static int getId() {
        return id;
    }

    @Override
    public String toString() {
        return "ID:" + id;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Hyperlinks other = (Hyperlinks) obj;
        if (this.id != other.getId()) {
            return false;
        }
        if (this.distance != other.distance) {
            return false;
        }
        return true;
    }
}
