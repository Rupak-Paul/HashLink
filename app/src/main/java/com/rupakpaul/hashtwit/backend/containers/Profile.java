package com.rupakpaul.hashtwit.backend.containers;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

public class Profile {
    private byte[] profilePicture;
    private String name;
    private String about;
    private String dob;
    private String location;

    public Profile(byte[] profilePicture, String name, String about, String dob, String location) {
        if(profilePicture == null) this.profilePicture = new byte[0];
        else this.profilePicture = profilePicture.clone();

        if(name == null) this.name = "";
        else this.name = name;

        if(about == null) this.about = "";
        else this.about = about;

        if(dob == null) this.dob = "";
        else this.dob = dob;

        if(location == null) this.location = "";
        else this.location = location;
    }

    public Profile(byte[] dataStream) throws Exception {
        ByteBuffer buffer = ByteBuffer.wrap(dataStream);

        int profilePictureLength = buffer.getInt();
        this.profilePicture = new byte[profilePictureLength];
        buffer.get(this.profilePicture);

        int nameLength = buffer.getInt();
        byte[] nameBytes = new byte[nameLength];
        buffer.get(nameBytes);
        this.name = new String(nameBytes, StandardCharsets.UTF_8);

        int aboutLength = buffer.getInt();
        byte[] aboutBytes = new byte[aboutLength];
        buffer.get(aboutBytes);
        this.about = new String(aboutBytes, StandardCharsets.UTF_8);

        int dobLength = buffer.getInt();
        byte[] dobBytes = new byte[dobLength];
        buffer.get(dobBytes);
        this.dob = new String(dobBytes, StandardCharsets.UTF_8);

        int locationLength = buffer.getInt();
        byte[] locationBytes = new byte[locationLength];
        buffer.get(locationBytes);
        this.location = new String(locationBytes, StandardCharsets.UTF_8);
    }

    public byte[] serialize() {
        int size = Integer.BYTES + profilePicture.length +
                Integer.BYTES + name.getBytes(StandardCharsets.UTF_8).length +
                Integer.BYTES + about.getBytes(StandardCharsets.UTF_8).length +
                Integer.BYTES + dob.getBytes(StandardCharsets.UTF_8).length +
                Integer.BYTES + location.getBytes(StandardCharsets.UTF_8).length;

        ByteBuffer buffer = ByteBuffer.allocate(size);

        buffer.putInt(profilePicture.length);
        buffer.put(profilePicture);

        byte[] nameBytes = name.getBytes(StandardCharsets.UTF_8);
        buffer.putInt(nameBytes.length);
        buffer.put(nameBytes);

        byte[] aboutBytes = about.getBytes(StandardCharsets.UTF_8);
        buffer.putInt(aboutBytes.length);
        buffer.put(aboutBytes);

        byte[] dobBytes = dob.getBytes(StandardCharsets.UTF_8);
        buffer.putInt(dobBytes.length);
        buffer.put(dobBytes);

        byte[] locationBytes = location.getBytes(StandardCharsets.UTF_8);
        buffer.putInt(locationBytes.length);
        buffer.put(locationBytes);

        return buffer.array();
    }

    public byte[] getProfilePicture() {
        return profilePicture.clone();
    }

    public String getName() {
        return name;
    }

    public String getAbout() {
        return about;
    }

    public String getDob() {
        return dob;
    }

    public String getLocation() {
        return location;
    }
}
