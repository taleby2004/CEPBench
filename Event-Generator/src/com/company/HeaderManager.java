package com.company;

import java.util.BitSet;

/**
 * Created by mohammadreza on 7/2/2016.
 */
public class HeaderManager {


    public byte version ;
    public byte timeStamp;
    public byte serializerType;
    public byte serializerVersion;
    public byte isExtended;
    public byte orderType;
    public byte extensionSize;
    public String Extension;

    public int header_lenght ;
    public HeaderManager(){
        version = 0b01111110;
        timeStamp = 0b01000001;
        serializerType = 0b1000001;
        serializerVersion = 0b1000001;
        isExtended = 0b01000010;
        orderType = 0b01000001;


    }

    public void setVersion(byte version) {
        this.version = version;
    }

    public void setTimeStamp(byte timeStamp) {
        this.timeStamp = timeStamp;
    }

    public void setSerializerType(byte serializerType) {
        this.serializerType = serializerType;
    }

    public void setSerializerVersion(byte serializerVersion) {
        this.serializerVersion = serializerVersion;
    }

    public void setIsExtended(byte isExtended) {
        this.isExtended = isExtended;
    }

    public void setOrderType(byte orderType) {
        this.orderType = orderType;
    }

    public void setExtensionSize(byte extensionSize) {
        this.extensionSize = extensionSize;
    }

    public void setExtension(String extension) {
        Extension = extension;
    }
    public byte[] generateHeader(){
        byte[] headerbits = new byte[]{version,timeStamp,serializerType,serializerVersion,isExtended,orderType};
        BitSet fbs = BitSet.valueOf(headerbits);
        BitSet hbs = new BitSet();
        for(int i = 0; i<4; i++){
            if(fbs.get(i))
            hbs.set(i);

        }

        for (int i = 4; i <30 ; i++){
            if(fbs.get(i+4))
                hbs.set(i);
        }
        for (int i = 30; i <32 ; i++) {
            if(fbs.get(i+4+6))
                hbs.set(i);
        }

        byte[] header = hbs.toByteArray();
        header_lenght = header.length ;
        return header;
    }

    public void readHeader(byte[] headerBytes){
        BitSet bs = BitSet.valueOf(headerBytes);
        byte[] normalBytes = bs.get(4,28).toByteArray();
        version = bs.get(0,4).toByteArray()[0];
        timeStamp = normalBytes[0];
        serializerType = normalBytes[1];
        serializerVersion = normalBytes[2];
        isExtended = bs.get(28,30).toByteArray()[0];
        orderType = bs.get(30,32).toByteArray()[0];

    }
}
