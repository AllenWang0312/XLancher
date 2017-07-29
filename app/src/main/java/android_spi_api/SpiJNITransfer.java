package android_spi_api;

/**
 * Created by wpc on 2017/7/28.
 */

public class SpiJNITransfer{


    public static final String path="/dev/spidev2.1";

    public static final  byte[] test_bytes={
            (byte) 0xFF,  (byte) 0xFF,  (byte) 0xFF,  (byte) 0xFF,  (byte) 0xFF,  (byte) 0xFF,
                0x40, 0x00, 0x00, 0x00, 0x00,  (byte) 0x95,
            (byte) 0xFF,  (byte) 0xFF,  (byte) 0xFF,  (byte) 0xFF,  (byte) 0xFF,  (byte) 0xFF,
            (byte)   0xFF,  (byte) 0xFF,  (byte) 0xFF, (byte)  0xFF, (byte)  0xFF,  (byte) 0xFF,
            (byte)  0xFF,  (byte) 0xFF, (byte)  0xFF,  (byte) 0xFF,  (byte) 0xFF,  (byte) 0xFF,
            (byte) 0xDE,  (byte) 0xAD,  (byte) 0xBE,  (byte) 0xEF,  (byte) 0xBA,  (byte) 0xAD,
            (byte) 0xF0, 0x0D,
    };

    static {
        System.loadLibrary("spi_transfer");
    }

    public native void test();//
    public native int open(String path);//if return <0 can't open device
//    private native int setSpiMode();//if(ret==-1) can't set spi mode
//    private native int getSpiMode();//if(ret==-1) can't get spi mode
//    private native int setBitsPerWord(int bits);//if(ret==-1) can't set bits per word
//    private native int getBitsPerWord();
//    private native int setMaxSpeed(int hz);
//    private native int getMaxSpeed();
    public native byte[]transfer(int index,byte[] input);//

    public native void close(int index);

}
